package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.util.RandomBooleanGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class RoutingRulesService {

    @Autowired
    JmsTemplate jmsTemplate;

    public String getRoutingRules(String ruleIdentifier) {
        System.out.println("Getting rules for rule identifier: " + ruleIdentifier);
        return generateRandomRule();
    }

    private String generateRandomRule() {
        return RandomBooleanGenerator.generateRandomStatus() ? "None" : "Watchamakalit";
    }
}
