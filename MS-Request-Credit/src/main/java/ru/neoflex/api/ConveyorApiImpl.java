package ru.neoflex.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ConveyorApiImpl.class);
    @Autowired
    ConveyorService conveyorService;

    public ResponseEntity<List<LoanOfferDTO>> restReceiveRequestConveyorOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        logger.info(loanApplicationRequestDTO.toString());
        ResponseEntity responseEntity = ResponseEntity.ok().body(conveyorService.preScoring(loanApplicationRequestDTO));
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<CreditDTO>> restReceiveRequestConveyoralculation(ScoringDataDTO scoringDataDTO) {
        logger.info(scoringDataDTO.toString());
        ResponseEntity responseEntity = ResponseEntity.ok().body(conveyorService.scoring(scoringDataDTO));
        return responseEntity;
    }
}
