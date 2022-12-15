package ru.neoflex.mappers;

import org.mapstruct.Mapper;
import ru.neoflex.models.Application;
import ru.neoflex.models.Client;
import ru.neoflex.models.Passport;
import ru.neoflex.openapi.model.FinishRegistrationRequestDTO;
import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.ScoringDataDTO;

@Mapper
public interface SimpleSourceDestinationMapper {
  //  Application mappingApplication(LoanApplicationRequestDTO source);
    Client mappingClient(LoanApplicationRequestDTO source);
    Passport mappingPassport(LoanApplicationRequestDTO source);
    ScoringDataDTO mappringScoringData(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Application application);
}
