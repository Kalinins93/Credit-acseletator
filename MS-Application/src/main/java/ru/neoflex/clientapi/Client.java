package ru.neoflex.clientapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.neoflex.openapi.model.LoanApplicationRequestDTO;
import ru.neoflex.openapi.model.LoanOfferDTO;

import java.util.List;

@FeignClient(name = "dealClient", url = "${client.url}")
public interface Client {

    @RequestMapping(method = RequestMethod.POST, value = "/deal/offers", consumes = "application/json")
    List<LoanOfferDTO> getLoanOfferDTOList(LoanApplicationRequestDTO loanApplicationRequestDTO);
    @RequestMapping(method = RequestMethod.POST, value = "/deal/calculation", consumes = "application/json")
    void putLoanOfferDTO(LoanOfferDTO loanOfferDTO);
}

