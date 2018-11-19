package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.model.RoutingRule;
import com.paymentservice.paymentservice.repository.RoutingRuleRepository;
import com.paymentservice.paymentservice.util.RandomBooleanGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoutingRulesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoutingRulesService.class);

    @Autowired
    RoutingRuleRepository routingRuleRepository;

    public String getRoutingRules(String ruleIdentifier) {
        LOGGER.info("Getting rule for identifier: " + ruleIdentifier);
        RoutingRule routingRule = routingRuleRepository.findById(Long.parseLong(ruleIdentifier)).get();
        return routingRule.getSettlementEngine();
    }
}
