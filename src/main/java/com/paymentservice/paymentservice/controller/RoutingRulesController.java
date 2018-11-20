package com.paymentservice.paymentservice.controller;

import com.paymentservice.paymentservice.service.RoutingRulesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routing-rules-service")
@Api(tags = "routing-rules-service", description = "Routing Rules Service Controller")
public class RoutingRulesController {

    @Autowired
    RoutingRulesService routingRulesService;

    @ApiOperation(tags = "routing-rules-service", value = "This service gets routing rules from the database.")
    @GetMapping("/getRoutingRules")
    public ResponseEntity<String> getRoutingRules(@RequestParam String ruleIdentifier) {
        return ResponseEntity.ok(routingRulesService.getRoutingRules(ruleIdentifier));
    }
}
