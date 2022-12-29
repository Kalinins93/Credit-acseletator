package ru.neoflex.api;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.LoanOfferDTO;
import java.util.List;
@Controller
public class ApplicationApi implements ru.neoflex.openapi.api.ApplicationApi {
  //  @Autowired
  //  ConveyorServiceImpl conveyorService;
    @Override
    public ResponseEntity<List<LoanOfferDTO>> restReceiveApplication(LoanApplicationRequestDTO loanApplicationRequestDTO) {
     //   ResponseEntity<List<LoanOfferDTO>> responseEntity = ResponseEntity.ok().body(conveyorService.preScoring(loanApplicationRequestDTO));
        return null;
    }

    @Override
    public ResponseEntity<Void> restReceiveRequestConveyorOffers(LoanOfferDTO loanOfferDTO) {
        return null;
    }
}
