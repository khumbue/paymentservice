package com.paymentservice.paymentservice.repository;

import com.paymentservice.paymentservice.PaymentserviceApplication;
import com.paymentservice.paymentservice.model.RoutingRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentserviceApplication.class)
public class RoutingRuleRepositoryTest {

    @Autowired
    private RoutingRuleRepository routingRuleRepository;

    @Test
    public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        RoutingRule routingRule = generateRoutingRule();
        routingRule = routingRuleRepository.save(routingRule);

        RoutingRule foundRoutingRule = routingRuleRepository.findById(routingRule.getId()).get();

        assertNotNull(foundRoutingRule);
        assertEquals(routingRule.getSettlementEngine(), foundRoutingRule.getSettlementEngine());
    }

    private RoutingRule generateRoutingRule() {
        RoutingRule routingRule = new RoutingRule();
        routingRule.setId(1003L);
        routingRule.setSettlementEngine("dummySettlementEngine");
        return routingRule;
    }
}