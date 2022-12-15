package ru.neoflex.clientApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.neoflex.openapi.model.CreditDTO;
import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.LoanOfferDTO;
import ru.neoflex.openapi.model.ScoringDataDTO;

import java.util.List;
@FeignClient(name = "test", url = "${Conveyor.url}")
public interface SimpleClient {

    @RequestMapping(method = RequestMethod.POST, value = "/conveyor/offers", consumes = "application/json")
    List<LoanOfferDTO> getLoanOfferDTOList(LoanApplicationRequestDTO loanApplicationRequestDTO);
    @RequestMapping(method = RequestMethod.POST, value = "/conveyor/calculation", consumes = "application/json")
    List<CreditDTO> getCreditDTOList(ScoringDataDTO scoringDataDTO);
}
