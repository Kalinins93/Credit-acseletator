package ru.neoflex.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.neoflex.openapi.api.ConveyorApi;
import ru.neoflex.openapi.model.CreditDTO;
import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.LoanOfferDTO;
import ru.neoflex.openapi.model.ScoringDataDTO;
import ru.neoflex.services.ConveyorService;

import java.util.List;

@Controller
public class ConveyorApiImpl implements ConveyorApi {
    @Autowired
    ConveyorService conveyorService;

    public ResponseEntity<List<LoanOfferDTO>> restReceiveRequestConveyorOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        ResponseEntity<List<LoanOfferDTO>> responseEntity = ResponseEntity.ok().body(conveyorService.preScoring(loanApplicationRequestDTO));
        return responseEntity;
    }

    @Override
    public ResponseEntity<CreditDTO> restReceiveRequestConveyoralculation(ScoringDataDTO scoringDataDTO) {
        ResponseEntity<CreditDTO>  responseEntity = ResponseEntity.ok().body(conveyorService.scoring(scoringDataDTO));
        return responseEntity;
    }
}
