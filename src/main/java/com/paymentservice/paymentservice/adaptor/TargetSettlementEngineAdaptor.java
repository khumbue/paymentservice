package com.paymentservice.paymentservice.adaptor;

import com.paymentservice.paymentservice.dto.Payment;
import com.paymentservice.paymentservice.util.GenericMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

import static com.paymentservice.paymentservice.util.ApplicationConstants.OUTGOING_MT195_ACKNOWLEDGEMENT;
import static com.paymentservice.paymentservice.util.ApplicationConstants.PAYMENT_SERVICE_INVALID_MESSAGES;

/**
 * Mock implementation of the service to Send Payment to Settlement Engine. Class has two methods:
 * settlementWithNoAcknowledgement() which handles messages with no acknowledgement and
 * settlementWithAcknowledgment() which handles messages with an acknowledgement.
 */

@Component
public class TargetSettlementEngineAdaptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetSettlementEngineAdaptor.class);

    @Autowired
    JmsTemplate jmsTemplate;

    public void settlementWithNoAcknowledgement(Payment payment) {
        LOGGER.info("Target Settlement Engine Adaptor with None for: {}.", payment.getOrderingCustomer());
        LOGGER.info("Process complete");
    }

    public void settlementWithAcknowledgment(Payment payment) {
        LOGGER.info("Target Settlement Engine Adaptor with AckNak");
        GenericMarshaller<Payment> genericMarshaller = new GenericMarshaller<>();

        try {
            jmsTemplate.convertAndSend(OUTGOING_MT195_ACKNOWLEDGEMENT, genericMarshaller.marshall(payment, Payment.class));
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage());
            jmsTemplate.convertAndSend(PAYMENT_SERVICE_INVALID_MESSAGES, e.getMessage());
        }
    }
}
