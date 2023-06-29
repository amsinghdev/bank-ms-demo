package com.amit.accounts.service.client;

import com.amit.accounts.model.Cards;
import com.amit.accounts.model.Customer;
import com.amit.accounts.model.Loans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("loans")
public interface LoansFeignClient {
    @RequestMapping(method = RequestMethod.POST,value="loans",consumes = "application/json")
    List<Loans> getLoansDetails(@RequestHeader("ambank-correlation-id") String correlationid,@RequestBody Customer customer);
}
