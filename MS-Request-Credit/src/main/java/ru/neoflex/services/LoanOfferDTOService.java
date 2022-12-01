package ru.neoflex.services;

import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.LoanOfferDTO;

import java.util.List;

public interface LoanOfferDTOService {
    public List<LoanOfferDTO> getListLoanOfferDTO(LoanApplicationRequestDTO loanApplicationRequestDTO);
    public LoanOfferDTO getLoanOffer(LoanApplicationRequestDTO loanApplicationRequestDTO, Boolean isInsuranceEnabled, Boolean isSalaryClient);
}
