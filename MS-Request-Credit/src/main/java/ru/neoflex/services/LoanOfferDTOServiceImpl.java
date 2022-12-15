package ru.neoflex.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.neoflex.match.CreditInformationService;
import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.LoanOfferDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class LoanOfferDTOServiceImpl implements LoanOfferDTOService {
    @Value("${general-rate}")
    private BigDecimal generalRate;
    @Value("${increasing-without-insurance}")
    private BigDecimal increasingWithoutInsurance;
    @Value("${increasing-not-salary-client}")
    private BigDecimal increasingIsNotSalaryClient;
    @Value("${sum-insurance}")
    private BigDecimal sumInsurance;
    @Autowired
    private CreditInformationService creditInformationService;
    @Override
    public List<LoanOfferDTO> getListLoanOfferDTO(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> offerDTOList= new ArrayList<LoanOfferDTO>();
        LoanOfferDTO loanOfferDTOWithOutInsuranceAndIsNotSalary = this.getLoanOffer(loanApplicationRequestDTO, false, false);
        LoanOfferDTO loanOfferDTOInsuranceAndIsNotSalary = this.getLoanOffer(loanApplicationRequestDTO, true, false);
        LoanOfferDTO loanOfferDTOWithOutInsuranceAndIsSalary = this.getLoanOffer(loanApplicationRequestDTO, false, true);
        LoanOfferDTO loanOfferDTOWithInsuranceAndIsSalary = this.getLoanOffer(loanApplicationRequestDTO, true, true);
        offerDTOList.add(loanOfferDTOWithOutInsuranceAndIsNotSalary);
        offerDTOList.add(loanOfferDTOInsuranceAndIsNotSalary);
        offerDTOList.add(loanOfferDTOWithOutInsuranceAndIsSalary);
        offerDTOList.add(loanOfferDTOWithInsuranceAndIsSalary);
        AtomicInteger i = new AtomicInteger(0);
        offerDTOList.sort(Comparator.comparing(loanOfferDTO -> loanOfferDTO.getTotalAmount()));
        Collections.reverse(offerDTOList);
        offerDTOList.forEach((LoanOfferDTO loanOfferDTO) -> loanOfferDTO.setId(i.incrementAndGet()));
        return offerDTOList;
    }

    @Override
    public LoanOfferDTO getLoanOffer(LoanApplicationRequestDTO loanApplicationRequestDTO, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        LoanOfferDTO anyBodyLoanOfferDTO = new LoanOfferDTO();
        anyBodyLoanOfferDTO.setApplicationId(loanApplicationRequestDTO.hashCode());
        anyBodyLoanOfferDTO.setIsSalaryClient(isSalaryClient);
        anyBodyLoanOfferDTO.setIsInsuranceEnabled(isInsuranceEnabled);
        anyBodyLoanOfferDTO.setTerm(loanApplicationRequestDTO.getTerm());
        anyBodyLoanOfferDTO.setRequestedAmount(loanApplicationRequestDTO.getAmount());
        anyBodyLoanOfferDTO.setRate(
                anyBodyLoanOfferDTO.getIsInsuranceEnabled() && anyBodyLoanOfferDTO.getIsSalaryClient() ?
                        generalRate :
                        !anyBodyLoanOfferDTO.getIsInsuranceEnabled() && anyBodyLoanOfferDTO.getIsSalaryClient() ?
                                generalRate.add(increasingWithoutInsurance) :
                                anyBodyLoanOfferDTO.getIsInsuranceEnabled() && !anyBodyLoanOfferDTO.getIsSalaryClient() ?
                                        generalRate.add(increasingIsNotSalaryClient) :
                                        generalRate.add(increasingIsNotSalaryClient).add(increasingWithoutInsurance)
        );
        BigDecimal sumInsuranceAll = sumInsurance.multiply(new BigDecimal(loanApplicationRequestDTO.getTerm()).divide(new BigDecimal(12), 2, RoundingMode.CEILING));
        anyBodyLoanOfferDTO.setTotalAmount(
                anyBodyLoanOfferDTO.getIsInsuranceEnabled()?
                        creditInformationService.GetPSk(anyBodyLoanOfferDTO.getRate(),anyBodyLoanOfferDTO.getRequestedAmount(),anyBodyLoanOfferDTO.getTerm()).add(sumInsuranceAll):
                        creditInformationService.GetPSk(anyBodyLoanOfferDTO.getRate(),anyBodyLoanOfferDTO.getRequestedAmount(),anyBodyLoanOfferDTO.getTerm()));
        anyBodyLoanOfferDTO.setMonthlyPayment(creditInformationService.getMonthPaymant(anyBodyLoanOfferDTO.getRate(),anyBodyLoanOfferDTO.getRequestedAmount(), anyBodyLoanOfferDTO.getTerm()));
        return anyBodyLoanOfferDTO;
    }
}
