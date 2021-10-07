package com.amit.accounts.controller;

import com.amit.accounts.model.Accounts;
import com.amit.accounts.model.Customer;
import com.amit.accounts.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @PostMapping("/accounts")
    public Accounts getAccountDetails(@RequestBody Customer customer){
        System.out.println("customer id.."+customer.getCustomerId());
        Accounts accounts = accountsRepository.getAccountByCustomerId(customer.getCustomerId());
        System.out.println("account..."+ accounts.toString());
        if(accounts != null)
            return accounts;
        return null;
    }
}
