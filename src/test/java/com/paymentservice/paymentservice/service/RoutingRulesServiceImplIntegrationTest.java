package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.PaymentserviceApplication;
import com.paymentservice.paymentservice.model.RoutingRule;
import com.paymentservice.paymentservice.repository.RoutingRuleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class RoutingRulesServiceImplIntegrationTest {

    @TestConfiguration
    static class RoutingRulesServiceImplTestContextConfiguration {

        @Bean
        public RoutingRulesService routingRulesService() {
            return new RoutingRulesService();
        }
    }

    @Autowired
    RoutingRulesService routingRulesService;

    @MockBean
    private RoutingRuleRepository routingRuleRepository;

    @Before
    public void setUp() {
        RoutingRule routingRule = generateRoutingRule();
        Optional<RoutingRule> routingRuleOptional = Optional.of(routingRule);

        Mockito.when(routingRuleRepository.findById(1003L))
                .thenReturn(routingRuleOptional);
    }

    @Test
    public void whenValidId_thenRoutingRuleShouldBeFound() {
        String routingId = "1003";
        String rule = routingRulesService.getRoutingRules(routingId);

        assertEquals(rule, "dummySettlementEngine");
    }

    private RoutingRule generateRoutingRule() {
        RoutingRule routingRule = new RoutingRule();
        routingRule.setId(1003L);
        routingRule.setSettlementEngine("dummySettlementEngine");
        return routingRule;
    }
}