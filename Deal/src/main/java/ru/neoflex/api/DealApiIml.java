package ru.neoflex.api;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.neoflex.clientApi.FeingConveyor;
import ru.neoflex.models.Application;
import ru.neoflex.models.Client;
import ru.neoflex.models.Passport;
import ru.neoflex.openapi.api.DealApi;
import ru.neoflex.openapi.model.FinishRegistrationRequestDTO;
import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.LoanOfferDTO;
import ru.neoflex.services.DealServiceImpl;

import java.util.List;
@Controller
public class DealApiIml implements DealApi{
    @Autowired
    private DealServiceImpl dealService;
    @Autowired
    private FeingConveyor feingConveyor;
    @Override
    public ResponseEntity<List<LoanOfferDTO>> dealApplicationPost(LoanApplicationRequestDTO loanApplicationRequestDTO)  {
        Client client = new Client();
        client.setBirthDate(loanApplicationRequestDTO.getBirthdate());
        client.setFirstName(loanApplicationRequestDTO.getFirstName());
        client.setLastName(loanApplicationRequestDTO.getLastName());
        client.setMiddleName(loanApplicationRequestDTO.getMiddleName());
        client.setEmail(loanApplicationRequestDTO.getEmail());
        Passport passport = new Passport();
        passport.setNumber(loanApplicationRequestDTO.getPassportNumber());
        passport.setSeries(loanApplicationRequestDTO.getPassportSeries());
        client.setPassport(passport);
        Application application = new Application();
        application.setClient(client);
        dealService.addPassport(passport);
        dealService.addClient(client);
        dealService.addApplication(application);
        feingConveyor.offers(loanApplicationRequestDTO);
        ResponseEntity<List<LoanOfferDTO>> listResponseEntity = ResponseEntity.ok().body(null);
        return listResponseEntity;
    }

    @Override
    public ResponseEntity<Void> dealCalculateApplicationIdPut(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> dealOfferPut(LoanOfferDTO loanOfferDTO) {
        return null;
    }
}
