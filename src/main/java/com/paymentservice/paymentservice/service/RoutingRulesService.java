package com.paymentservice.paymentservice.service;

import org.springframework.stereotype.Service;

@Service
public class RoutingRulesService {
    public String getRoutingRules(String ruleIdentifier) {
        System.out.println("Getting rules for rule identifier: " + ruleIdentifier);
        return "Test Rule!";
    }
}
