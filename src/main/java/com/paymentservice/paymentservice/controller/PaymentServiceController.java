package com.paymentservice.paymentservice.controller;

import com.paymentservice.paymentservice.service.RoutingRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-service")
@Api(tags = "payment-service", description = "Payment Service Controller")
public class PaymentServiceController {

    @Autowired
    RoutingRulesService routingRulesService;

    @ApiOperation(tags = "payment-service", value = "This service gets routing rules from the database.")
    @GetMapping("/getRoutingRules")
    public String getRoutingRules(@PathVariable String ruleIdentifier) {
        return routingRulesService.getRoutingRules(ruleIdentifier);
    }
}
