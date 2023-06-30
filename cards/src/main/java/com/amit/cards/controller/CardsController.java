package com.amit.cards.controller;

import com.amit.cards.config.CardsServiceConfig;
import com.amit.cards.model.Cards;
import com.amit.cards.model.Customer;
import com.amit.cards.model.Properties;
import com.amit.cards.repository.CardsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CardsServiceConfig cardsServiceConfig;

    private Logger logger = LoggerFactory.getLogger(CardsController.class);

    @PostMapping("/cards")
    public List<Cards> getCardsDetails(@RequestHeader("ambank-correlation-id") String correlationid, @RequestBody Customer customer){
        logger.info("getCardsDetails method started");
        List<Cards> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
        logger.info("getCardsDetails method started");
        if(cards != null){
            return cards;
        }
        return null;
    }

    @GetMapping("/cards/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(cardsServiceConfig.getMsg(), cardsServiceConfig.getBuildVersion(),
                cardsServiceConfig.getMailDetails(), cardsServiceConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }
}
