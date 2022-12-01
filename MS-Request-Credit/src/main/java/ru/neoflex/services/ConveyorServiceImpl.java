package ru.neoflex.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.neoflex.openapi.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConveyorServiceImpl implements ConveyorService {
    @Value("${Amount}")
    private BigDecimal amount;
    @Value("${Term}")
    private Integer term;
    @Value("${general-rate}")
    private BigDecimal generalRate;
    @Autowired
    private LoanOfferDTOServiceImpl loanOfferDTOService;
    @Override
    public List<LoanOfferDTO> preScoring(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        if (loanApplicationRequestDTO.getLastName().isBlank() || loanApplicationRequestDTO.getFirstName().length() < 2 || loanApplicationRequestDTO.getFirstName().length() > 30)
            throw new RuntimeException();
        if (loanApplicationRequestDTO.getMiddleName().isBlank() || loanApplicationRequestDTO.getMiddleName().length() < 2 || loanApplicationRequestDTO.getMiddleName().length() > 30)
            throw new RuntimeException();
        if (!loanApplicationRequestDTO.getLastName().isEmpty() && loanApplicationRequestDTO.getLastName().length() < 2 || loanApplicationRequestDTO.getLastName().length() > 30)
            throw new RuntimeException();
        if (loanApplicationRequestDTO.getAmount().compareTo(amount) < 0) throw new RuntimeException();
        if (term > loanApplicationRequestDTO.getTerm()) throw new RuntimeException();
        if (Period.between(LocalDate.of(1900 + loanApplicationRequestDTO.getBirthdate().getYear(), loanApplicationRequestDTO.getBirthdate().getMonth(), loanApplicationRequestDTO.getBirthdate().getDay()), LocalDate.now()).getYears() < 18)
            throw new RuntimeException();
        if (loanApplicationRequestDTO.getPassportNumber().length() == 6 && loanApplicationRequestDTO.getPassportSeries().length() == 4) {
            int temp = Integer.parseInt(loanApplicationRequestDTO.getPassportSeries());
            temp = Integer.parseInt(loanApplicationRequestDTO.getPassportNumber());
            temp = 0;
        } else {
            throw new RuntimeException();
        }
        if (!loanApplicationRequestDTO.getEmail().matches("[\\w\\.]{2,50}@[\\w\\.]{2,20}"))
            throw new RuntimeException();
        return loanOfferDTOService.getListLoanOfferDTO(loanApplicationRequestDTO);
    }

    @Override
    public List<CreditDTO> scoring(ScoringDataDTO scoringDataDTO) {
        List<CreditDTO> creditDTOList = new ArrayList<CreditDTO>();
        CreditDTO creditDTO = new CreditDTO();

        int yearsOld = Period.between(LocalDate.of(1900 + scoringDataDTO.getBirthdate().getYear(), scoringDataDTO.getBirthdate().getMonth(), scoringDataDTO.getBirthdate().getDay()), LocalDate.now()).getYears();
        if(yearsOld > 60 || yearsOld < 20) throw new RuntimeException();
        if(EmploymentDTO.EmploymentStatusEnum.WORKLESS.equals(scoringDataDTO.getEmployment().getEmploymentStatus())) throw new RuntimeException();
        if(scoringDataDTO.getEmployment().getWorkExperienceTotal()<12)throw  new RuntimeException();
        if(scoringDataDTO.getEmployment().getWorkExperienceCurrent()<3) throw  new RuntimeException();
        if(scoringDataDTO.getEmployment().getSalary().multiply(new BigDecimal(20)).compareTo(scoringDataDTO.getAmount())<0) throw new RuntimeException();
        creditDTO.setRate(
                EmploymentDTO.EmploymentStatusEnum.SELF_EMPLOYED.equals(scoringDataDTO.getEmployment().getEmploymentStatus())?
                 generalRate.add(new BigDecimal(1)) :
                    EmploymentDTO.EmploymentStatusEnum.BUSINESSMAN.equals(scoringDataDTO.getEmployment().getEmploymentStatus())?
                        generalRate.add(new BigDecimal(3)):
                            EmploymentDTO.EmploymentStatusEnum.WORKING.equals(scoringDataDTO.getEmployment().getEmploymentStatus())?
                                EmploymentDTO.PositionEnum.TOP_MANAGER.equals(scoringDataDTO.getEmployment().getPosition())?
                                        generalRate.add(BigDecimal.valueOf(-4)):
                                        EmploymentDTO.PositionEnum.MIDDLE_MANAGER.equals(scoringDataDTO.getEmployment().getPosition())?
                                                generalRate.add(BigDecimal.valueOf(-2)):
                                                generalRate:
                                    generalRate
        );
        creditDTO.setRate(
                scoringDataDTO.getMaritalStatus().equals(ScoringDataDTO.MaritalStatusEnum.MARRIED)?
                        creditDTO.getRate().add(BigDecimal.valueOf(-3)):
                        scoringDataDTO.getMaritalStatus().equals(ScoringDataDTO.MaritalStatusEnum.DIVORCED)?
                        creditDTO.getRate().add(BigDecimal.ONE):
                                creditDTO.getRate()
        );
        creditDTO.setRate(
                scoringDataDTO.getDependentAmount()>1?
                        creditDTO.getRate().add(BigDecimal.ONE):
                        creditDTO.getRate()
        );
        Integer year = Period.between(LocalDate.of(scoringDataDTO.getBirthdate().getYear(),scoringDataDTO.getBirthdate().getMonth(),scoringDataDTO.getBirthdate().getDay()),LocalDate.now()).getYears();
        creditDTO.setRate(
                scoringDataDTO.getGender().equals(
                        ScoringDataDTO.GenderEnum.FEMALE)&& year>=35 && year<=60?
                        creditDTO.getRate().add(BigDecimal.valueOf(-3))
                        :creditDTO.getRate()
        );
        creditDTO.setRate(
                scoringDataDTO.getGender().equals(
                        ScoringDataDTO.GenderEnum.MALE)&& year>=35 && year<=55?
                        creditDTO.getRate().add(BigDecimal.valueOf(-3))
                        :creditDTO.getRate()
        );
        creditDTO.setRate(scoringDataDTO.getGender().equals(
                ScoringDataDTO.GenderEnum.NOT_BINARY)?creditDTO.getRate().add(BigDecimal.valueOf(3)):creditDTO.getRate());
        creditDTO.setTerm(scoringDataDTO.getTerm());
        creditDTO.setPsk(scoringDataDTO.getAmount().multiply(BigDecimal.ONE.add(creditDTO.getRate().divide(BigDecimal.valueOf(100)))));
        creditDTO.setIsInsuranceEnabled(scoringDataDTO.getIsInsuranceEnabled());
        creditDTO.setIsSalaryClient(scoringDataDTO.getIsSalaryClient());
        creditDTO.setMonthlyPayment(creditDTO.getPsk().divide(BigDecimal.valueOf(creditDTO.getTerm()),2,BigDecimal.ROUND_CEILING));
        creditDTO.setAmount(scoringDataDTO.getAmount());
        List<PaymentScheduleElement> paymentScheduleElementList = new ArrayList<PaymentScheduleElement>();
        for (int i =1;  i<=creditDTO.getTerm(); i++){
            if(i==1){
            PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement();
            paymentScheduleElement.setTotalPayment(creditDTO.getMonthlyPayment());
            paymentScheduleElement.setNumber(i);
            paymentScheduleElement.setDate(Date.from(Instant.now()));
            paymentScheduleElement.setInterestPayment(creditDTO.getAmount().multiply(creditDTO.getRate().divide(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(12),2, RoundingMode.CEILING));
            paymentScheduleElement.setDebtPayment(creditDTO.getMonthlyPayment().add(paymentScheduleElement.getInterestPayment().negate()));
            paymentScheduleElement.setRemainingDebt(creditDTO.getAmount().add(paymentScheduleElement.getInterestPayment()).add(paymentScheduleElement.getDebtPayment().negate()));
            paymentScheduleElementList.add(paymentScheduleElement);
            }
            else {
                PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement();
                paymentScheduleElement.setTotalPayment(creditDTO.getMonthlyPayment());
                paymentScheduleElement.setNumber(i);
                LocalDate date1 = LocalDate.of(1900+paymentScheduleElementList.get(i-2).getDate().getYear(),paymentScheduleElementList.get(i-2).getDate().getMonth(),paymentScheduleElementList.get(i-2).getDate().getDay());
                date1.plusMonths(1);
                paymentScheduleElement.setDate(Date.valueOf(date1));
                paymentScheduleElement.setInterestPayment(paymentScheduleElementList.get(i-2).getRemainingDebt().multiply(creditDTO.getRate().divide(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(12),2, RoundingMode.CEILING));
                paymentScheduleElement.setDebtPayment(creditDTO.getMonthlyPayment().add(paymentScheduleElement.getInterestPayment().negate()));
                paymentScheduleElement.setRemainingDebt(paymentScheduleElementList.get(i-2).getRemainingDebt().add(paymentScheduleElement.getInterestPayment()).add(paymentScheduleElement.getDebtPayment().negate()));
                paymentScheduleElementList.add(paymentScheduleElement);

            }
        }
        creditDTO.setPaymentSchedule(paymentScheduleElementList);
        creditDTOList.add(creditDTO);
        return creditDTOList;
    }

}
