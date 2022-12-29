package ru.neoflex.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.clientapi.Client;
import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.LoanOfferDTO;

import java.util.List;
@RestController
public class ApplicationApi implements ru.neoflex.openapi.api.ApplicationApi {
    @Autowired
    Client client;
    @Override
    public ResponseEntity<List<LoanOfferDTO>> restReceiveApplication(LoanApplicationRequestDTO loanApplicationRequestDTO) {
       ResponseEntity<List<LoanOfferDTO>> responseEntity =  ResponseEntity.ok().body(client.getLoanOfferDTOList(loanApplicationRequestDTO));
        return responseEntity;
    }

    @Override
    public ResponseEntity<Void> restReceiveRequestConveyorOffers(LoanOfferDTO loanOfferDTO) {
        client.putLoanOfferDTO(loanOfferDTO);
        return ResponseEntity.ok().body(null);
    }
}
