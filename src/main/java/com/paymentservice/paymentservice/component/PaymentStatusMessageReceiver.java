package com.paymentservice.paymentservice.component;

import com.paymentservice.paymentservice.adaptor.TargetSettlementEngineAdaptor;
import com.paymentservice.paymentservice.dto.Payment;
import com.paymentservice.paymentservice.util.GenericMarshaller;
import com.paymentservice.paymentservice.util.GenericUnmarshaller;
import com.paymentservice.paymentservice.util.RandomBooleanGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;

import static com.paymentservice.paymentservice.util.ApplicationConstants.OUTGOING_MT195_ACKNOWLEDGEMENT;
import static com.paymentservice.paymentservice.util.ApplicationConstants.OUTGOING_SEND_PAYMENT_SETTLEMENT_ENGINE;
import static com.paymentservice.paymentservice.util.ApplicationConstants.PAYMENT_SERVICE_INVALID_MESSAGES;

@Component
public class PaymentStatusMessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentStatusMessageReceiver.class);

    @Value("${routing.rules.service.url}")
    private String routingRulesServiceUrl;

    @Autowired
    JmsTemplate jmsTemplate;

    public void retrievePaymentMessageStatus(String paymentXmlMessage) {
        LOGGER.info("Running Retrieve Payment Message Status");
        GenericUnmarshaller<Payment> genericUnmarshaller = new GenericUnmarshaller<>();

        try {
            Payment payment = genericUnmarshaller.unmarshall(paymentXmlMessage, Payment.class);
            if ("Rejected".equalsIgnoreCase(payment.getStatus())) {
                LOGGER.info("Status is Rejected");
            } else {
                LOGGER.info("Status is Not Rejected");
                determineDestination(payment);
            }
            jmsTemplate.convertAndSend(OUTGOING_MT195_ACKNOWLEDGEMENT, paymentXmlMessage);
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage());
            jmsTemplate.convertAndSend(PAYMENT_SERVICE_INVALID_MESSAGES, e.getMessage() + "\n" + paymentXmlMessage);
        }
    }

    public void determineDestination(Payment payment) {
        GenericMarshaller<Payment> paymentGenericMarshaller = new GenericMarshaller<>();

        String routingRuleIdentifier = generateRandomRuleId();
        LOGGER.info("Determine Destination using routingRuleIdentifier {}.", routingRuleIdentifier);

        String targetEngine = retrieveTargetEngineFromRoutingService(routingRuleIdentifier);
        payment.setTargetEngine(targetEngine);
        try {
            String paymentXmlMessage = paymentGenericMarshaller.marshall(payment, Payment.class);
            jmsTemplate.convertAndSend(OUTGOING_SEND_PAYMENT_SETTLEMENT_ENGINE, paymentXmlMessage);
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage());
        }
    }

    //Calling the routing rules service to retrieve the rule given a specific identifier.
    private String retrieveTargetEngineFromRoutingService(String routingRuleIdentifier) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> searchPayload = restTemplate.getForEntity(routingRulesServiceUrl + routingRuleIdentifier, String.class);
        return searchPayload.getBody();
    }

    private String generateRandomRuleId() {
        return RandomBooleanGenerator.generateRandomStatus() ? "10001" : "10002";
    }
}
