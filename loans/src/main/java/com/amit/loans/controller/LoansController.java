package com.amit.loans.controller;

import com.amit.loans.model.Customer;
import com.amit.loans.model.Loans;
import com.amit.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {
    @Autowired
    private LoansRepository loansRepository;

    @PostMapping("/loans")
    public List<Loans> getLoansDetail(@RequestBody Customer customer){
        List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        if(loans != null){
            return loans;
        }
        return null;
    }
}
