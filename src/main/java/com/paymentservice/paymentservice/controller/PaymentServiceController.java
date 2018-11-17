package com.paymentservice.paymentservice.controller;

import com.paymentservice.paymentservice.service.RoutingRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paymentservice")
public class PaymentServiceController {

    @Autowired
    RoutingRulesService routingRulesService;

    @GetMapping("/getRoutingRules")
    public String getRoutingRules() {
        return routingRulesService.getRoutingRules();
    }
}
