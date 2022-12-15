package ru.neoflex.mappers;

import ru.neoflex.models.Application;
import ru.neoflex.models.Client;
import ru.neoflex.models.Passport;
import ru.neoflex.openapi.model.FinishRegistrationRequestDTO;
import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.ScoringDataDTO;

public class SimpleSourceDestinationMapperImpl implements SimpleSourceDestinationMapper{

    @Override
    public Client mappingClient(LoanApplicationRequestDTO source) {
        if ( source == null ) {
            return null;
        }
        Client client = new Client();
        client.setEmail(source.getEmail());
        client.setFirstName(source.getFirstName());
        client.setLastName(source.getLastName());
        client.setMiddleName(source.getMiddleName());
        client.setBirthDate(source.getBirthdate());
        return client;
    }

    @Override
    public Passport mappingPassport(LoanApplicationRequestDTO source) {
        if ( source == null ) {
            return null;
        }
        Passport passport = new Passport();
        passport.setNumber(source.getPassportNumber());
        passport.setSeries(source.getPassportSeries());
        return passport;
    }

    @Override
    public ScoringDataDTO mappringScoringData(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Application application) {
        if ( finishRegistrationRequestDTO == null || application ==null) {
            return null;
        }
        ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
        scoringDataDTO.setAmount(application.getCredit().getAmount());
        scoringDataDTO.setAccount(finishRegistrationRequestDTO.getAccount());
        scoringDataDTO.setFirstName(application.getClient().getFirstName());
        scoringDataDTO.setLastName(application.getClient().getLastName());
        scoringDataDTO.setMiddleName(application.getClient().getMiddleName());
        scoringDataDTO.setBirthdate(application.getClient().getBirthDate());
        scoringDataDTO.setDependentAmount(finishRegistrationRequestDTO.getDependentAmount());
        scoringDataDTO.setEmployment(finishRegistrationRequestDTO.getEmployment());
       // scoringDataDTO.setGender(finishRegistrationRequestDTO.getGender());
        scoringDataDTO.setIsInsuranceEnabled(application.getCredit().getInsuranceEnable());

        return null;
    }
}
