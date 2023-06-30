package com.amit.accounts.controller;

import com.amit.accounts.config.AccountsServiceConfig;
import com.amit.accounts.model.*;
import com.amit.accounts.repository.AccountsRepository;
import com.amit.accounts.service.client.CardsFeignClient;
import com.amit.accounts.service.client.LoansFeignClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsServiceConfig accountsConfig;

    @Autowired
    private CardsFeignClient cardsFeignClient;

    @Autowired
    private LoansFeignClient loansFeignClient;

    private Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @PostMapping("/accounts")
    public Accounts getAccountDetails(@RequestBody Customer customer){
        Accounts accounts = accountsRepository.getAccountByCustomerId(customer.getCustomerId());
        if(accounts != null)
            return accounts;
        return null;
    }

    @GetMapping("/accounts/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountsConfig.getMsg(), accountsConfig.getBuildVersion(),
                accountsConfig.getMailDetails(), accountsConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

    @PostMapping("/customerDetails")
//    @CircuitBreaker(name="detailsForCustomerSupportApp",fallbackMethod ="getCustomerDetailsFallBack" )
    @Retry(name="retryForCustomerDetails",fallbackMethod = "getCustomerDetailsFallBack")
    public CustomerDetails getCustomerDetails(@RequestHeader("ambank-correlation-id") String correlationid, @RequestBody Customer customer){

        logger.info("getCustomerDetails method started");
        Accounts accounts = accountsRepository.getAccountByCustomerId(customer.getCustomerId());

        List<Cards> cards = cardsFeignClient.getCardsDetails(correlationid,customer);
        List<Loans> loans = loansFeignClient.getLoansDetails(correlationid,customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setCards(cards);
        customerDetails.setLoans(loans);
        logger.info("getCustomerDetails method end");
        return customerDetails;
    }

    private CustomerDetails getCustomerDetailsFallBack(@RequestHeader("ambank-correlation-id") String correlationid,Customer customer,Throwable t){

        Accounts accounts = accountsRepository.getAccountByCustomerId(customer.getCustomerId());

        List<Loans> loans = loansFeignClient.getLoansDetails(correlationid,customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);

        return customerDetails;
    }

    @GetMapping("/sayHello")
    @RateLimiter(name = "sayHello",fallbackMethod = "sayHelloFallBack")
    public String sayHello(){
        return "Hi, welcome to ambank ";
    }

    private String sayHelloFallBack(Throwable t){
        return "Hello welcome from sayHelloFallBack";
    }
}
