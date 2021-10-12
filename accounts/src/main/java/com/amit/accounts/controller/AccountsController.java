package com.amit.accounts.controller;

import com.amit.accounts.config.AccountsServiceConfig;
import com.amit.accounts.model.Accounts;
import com.amit.accounts.model.Customer;
import com.amit.accounts.model.Properties;
import com.amit.accounts.repository.AccountsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsServiceConfig accountsConfig;

    @PostMapping("/accounts")
    public Accounts getAccountDetails(@RequestBody Customer customer){
        System.out.println("customer id.."+customer.getCustomerId());
        Accounts accounts = accountsRepository.getAccountByCustomerId(customer.getCustomerId());
        System.out.println("account..."+ accounts.toString());
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
}
