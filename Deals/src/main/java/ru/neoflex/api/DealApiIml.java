package ru.neoflex.api;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.neoflex.clientApi.SimpleClient;
import ru.neoflex.mappers.SimpleSourceDestinationMapper;
import ru.neoflex.models.*;
import ru.neoflex.openapi.api.DealApi;
import ru.neoflex.openapi.model.*;
import ru.neoflex.services.DealServiceImpl;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DealApiIml implements DealApi {
    @Autowired
    private DealServiceImpl dealService;
    @Autowired
    private SimpleClient simpleClient;
    private SimpleSourceDestinationMapper mapper
            = Mappers.getMapper(SimpleSourceDestinationMapper.class);
    @Override
    public ResponseEntity<List<LoanOfferDTO>> dealApplicationPost(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        Client client = mapper.mappingClient(loanApplicationRequestDTO);
        Passport passport = mapper.mappingPassport(loanApplicationRequestDTO);
        client.setPassport(passport);
        Application application = new Application();
        application.setClient(client);
        dealService.addClient(client);
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatus("HZ");
        statusHistory.setTime(LocalDate.now());
        statusHistory.setChange_type(Change_type.AUTOMATIC);
        application.setStatusHistory(statusHistory);
        dealService.addApplication(application);
        List<LoanOfferDTO> offerDTOList = simpleClient.getLoanOfferDTOList(loanApplicationRequestDTO);
        ResponseEntity<List<LoanOfferDTO>> listResponseEntity = ResponseEntity.ok().body(offerDTOList);
        return listResponseEntity;
    }

    @Override
    public ResponseEntity<Void> dealCalculateApplicationIdPut(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
         Application application =dealService.findApplicationByID(applicationId.intValue());
         ScoringDataDTO scoringDataDTO = new ScoringDataDTO();

        List<CreditDTO> offerDTOList = simpleClient.getCreditDTOList(scoringDataDTO);
        return null;
    }

    @Override
    public ResponseEntity<Void> dealOfferPut(LoanOfferDTO loanOfferDTO) {
        return null;
    }
}
