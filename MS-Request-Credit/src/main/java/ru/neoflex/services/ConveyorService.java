package ru.neoflex.services;

import ru.neoflex.openapi.model.CreditDTO;
import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.LoanOfferDTO;
import ru.neoflex.openapi.model.ScoringDataDTO;

import java.util.List;

public interface ConveyorService {
    public List<LoanOfferDTO> preScoring(LoanApplicationRequestDTO loanApplicationRequestDTO);
    public List<CreditDTO> scoring(ScoringDataDTO scoringDataDTO);
}
