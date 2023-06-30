package com.amit.loans.controller;

import com.amit.loans.config.LoansServiceConfig;
import com.amit.loans.model.Customer;
import com.amit.loans.model.Loans;
import com.amit.loans.model.Properties;
import com.amit.loans.repository.LoansRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoansController {
    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private LoansServiceConfig loansServiceConfig;

    private Logger logger = LoggerFactory.getLogger(LoansController.class);

    @PostMapping("/loans")
    public List<Loans> getLoansDetail(@RequestHeader("ambank-correlation-id") String correlationid,@RequestBody Customer customer){
        logger.info("getLoansDetail method started");
        List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        logger.info("getLoansDetail method end");
        if(loans != null){
            return loans;
        }
        return null;
    }

    @GetMapping("/loans/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(loansServiceConfig.getMsg(), loansServiceConfig.getBuildVersion(),
                loansServiceConfig.getMailDetails(), loansServiceConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }
}
