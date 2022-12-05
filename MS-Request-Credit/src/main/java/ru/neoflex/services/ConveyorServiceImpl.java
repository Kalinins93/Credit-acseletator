package ru.neoflex.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.neoflex.openapi.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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

    @Override    public List<LoanOfferDTO> preScoring(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        if (loanApplicationRequestDTO.getLastName().isBlank() || loanApplicationRequestDTO.getFirstName().length() < 2 || loanApplicationRequestDTO.getFirstName().length() > 30)
            throw new RuntimeException();
        if (loanApplicationRequestDTO.getMiddleName().isBlank() || loanApplicationRequestDTO.getMiddleName().length() < 2 || loanApplicationRequestDTO.getMiddleName().length() > 30)
            throw new RuntimeException();
        if (!loanApplicationRequestDTO.getLastName().isEmpty() && loanApplicationRequestDTO.getLastName().length() < 2 || loanApplicationRequestDTO.getLastName().length() > 30)
            throw new RuntimeException();
        if (loanApplicationRequestDTO.getAmount().compareTo(amount) < 0) throw new RuntimeException();
        if (term > loanApplicationRequestDTO.getTerm()) throw new RuntimeException();
        if (Period.between(loanApplicationRequestDTO.getBirthdate(), LocalDate.now()).getYears() < 18)
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
    public CreditDTO scoring(ScoringDataDTO scoringDataDTO) {
        CreditDTO creditDTO = new CreditDTO();

        int yearsOld = Period.between(scoringDataDTO.getBirthdate(), LocalDate.now()).getYears();
        if (yearsOld > 60 || yearsOld < 20) throw new RuntimeException();
        if (EmploymentDTO.EmploymentStatusEnum.WORKLESS.equals(scoringDataDTO.getEmployment().getEmploymentStatus()))
            throw new RuntimeException();
        if (scoringDataDTO.getEmployment().getWorkExperienceTotal() < 12) throw new RuntimeException();
        if (scoringDataDTO.getEmployment().getWorkExperienceCurrent() < 3) throw new RuntimeException();
        if (scoringDataDTO.getEmployment().getSalary().multiply(new BigDecimal(20)).compareTo(scoringDataDTO.getAmount()) < 0)
            throw new RuntimeException();
        creditDTO.setRate(
                EmploymentDTO.EmploymentStatusEnum.SELF_EMPLOYED.equals(scoringDataDTO.getEmployment().getEmploymentStatus()) ?
                        generalRate.add(new BigDecimal(1)) :
                        EmploymentDTO.EmploymentStatusEnum.BUSINESSMAN.equals(scoringDataDTO.getEmployment().getEmploymentStatus()) ?
                                generalRate.add(new BigDecimal(3)) :
                                EmploymentDTO.EmploymentStatusEnum.WORKING.equals(scoringDataDTO.getEmployment().getEmploymentStatus()) ?
                                        EmploymentDTO.PositionEnum.TOP_MANAGER.equals(scoringDataDTO.getEmployment().getPosition()) ?
                                                generalRate.add(BigDecimal.valueOf(-4)) :
                                                EmploymentDTO.PositionEnum.MIDDLE_MANAGER.equals(scoringDataDTO.getEmployment().getPosition()) ?
                                                        generalRate.add(BigDecimal.valueOf(-2)) :
                                                        generalRate :
                                        generalRate
        );
        creditDTO.setRate(
                scoringDataDTO.getMaritalStatus().equals(ScoringDataDTO.MaritalStatusEnum.MARRIED) ?
                        creditDTO.getRate().add(BigDecimal.valueOf(-3)) :
                        scoringDataDTO.getMaritalStatus().equals(ScoringDataDTO.MaritalStatusEnum.DIVORCED) ?
                                creditDTO.getRate().add(BigDecimal.ONE) :
                                creditDTO.getRate()
        );
        creditDTO.setRate(
                scoringDataDTO.getDependentAmount() > 1 ?
                        creditDTO.getRate().add(BigDecimal.ONE) :
                        creditDTO.getRate()
        );
        Integer year = Period.between(scoringDataDTO.getBirthdate(), LocalDate.now()).getYears();
        creditDTO.setRate(
                scoringDataDTO.getGender().equals(
                        ScoringDataDTO.GenderEnum.FEMALE) && year >= 35 && year <= 60 ?
                        creditDTO.getRate().add(BigDecimal.valueOf(-3))
                        : creditDTO.getRate()
        );
        creditDTO.setRate(
                scoringDataDTO.getGender().equals(
                        ScoringDataDTO.GenderEnum.MALE) && year >= 35 && year <= 55 ?
                        creditDTO.getRate().add(BigDecimal.valueOf(-3))
                        : creditDTO.getRate()
        );
        creditDTO.setRate(scoringDataDTO.getGender().equals(
                ScoringDataDTO.GenderEnum.NOT_BINARY) ? creditDTO.getRate().add(BigDecimal.valueOf(3)) : creditDTO.getRate());
        creditDTO.setTerm(scoringDataDTO.getTerm());
        creditDTO.setIsInsuranceEnabled(scoringDataDTO.getIsInsuranceEnabled());
        creditDTO.setIsSalaryClient(scoringDataDTO.getIsSalaryClient());
        double mounthRate = (creditDTO.getRate().doubleValue() / 12) / 100;
        double koffeciantAutet = (mounthRate * Math.pow((1 + mounthRate), creditDTO.getTerm())) / (Math.pow((1 + mounthRate), creditDTO.getTerm()) - 1);
        double procent=0;
        creditDTO.setMonthlyPayment(scoringDataDTO.getAmount().multiply(BigDecimal.valueOf(koffeciantAutet)));
        creditDTO.setAmount(scoringDataDTO.getAmount());
        List<PaymentScheduleElement> paymentScheduleElementList = new ArrayList<PaymentScheduleElement>();
        Calendar dateForPayment = Calendar.getInstance();
        TimeZone tz = dateForPayment .getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        for (int i = 0; i < creditDTO.getTerm(); i++) {
            if (i == 0) {
                PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement();
                paymentScheduleElement.setTotalPayment(creditDTO.getMonthlyPayment());
                paymentScheduleElement.setNumber(i + 1);
                paymentScheduleElement.setDate(LocalDate.now());
                paymentScheduleElement.setInterestPayment(creditDTO.getAmount().multiply(BigDecimal.valueOf(mounthRate)));
                procent= paymentScheduleElement.getInterestPayment().doubleValue();
                paymentScheduleElement.setDebtPayment(creditDTO.getMonthlyPayment().add(paymentScheduleElement.getInterestPayment().negate()));
                paymentScheduleElement.setRemainingDebt(creditDTO.getAmount().add(paymentScheduleElement.getDebtPayment().negate()));
                paymentScheduleElementList.add(paymentScheduleElement);

            } else {
                int n = i - 1;
                PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement();
                paymentScheduleElement.setNumber(i + 1);
                dateForPayment.add(Calendar.MONTH, 1);
                paymentScheduleElement.setDate(LocalDate.ofInstant(dateForPayment.toInstant(),zid));
                paymentScheduleElement.setInterestPayment(paymentScheduleElementList.get(n).getRemainingDebt().multiply(BigDecimal.valueOf(mounthRate)));
                procent= procent+ paymentScheduleElement.getInterestPayment().doubleValue();
                paymentScheduleElement.setDebtPayment(creditDTO.getMonthlyPayment().add(paymentScheduleElement.getInterestPayment().negate()));
                paymentScheduleElement.setRemainingDebt(paymentScheduleElementList.get(n).getRemainingDebt().add(paymentScheduleElement.getDebtPayment().negate()));
                paymentScheduleElement.setTotalPayment(creditDTO.getMonthlyPayment());
                if ((i == creditDTO.getTerm() - 1)) {
                    paymentScheduleElement.setRemainingDebt(BigDecimal.ZERO);
                } else {
                    paymentScheduleElement.setRemainingDebt(paymentScheduleElementList.get(n).getRemainingDebt().add(paymentScheduleElement.getDebtPayment().negate()));
                }
                paymentScheduleElementList.add(paymentScheduleElement);
            }
        }
        creditDTO.setPaymentSchedule(paymentScheduleElementList);
        creditDTO.setPsk(creditDTO.getAmount().add(BigDecimal.valueOf(procent)));
        return creditDTO;
    }

}
