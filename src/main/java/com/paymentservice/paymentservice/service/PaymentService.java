package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.adaptor.TargetSettlementEngineAdaptor;
import com.paymentservice.paymentservice.dto.PaymentMessageStatus;
import com.paymentservice.paymentservice.dto.ValidatedPayment;
import com.paymentservice.paymentservice.util.GenericMarshaller;
import com.paymentservice.paymentservice.util.RandomBooleanGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    @Value("${routing.rules.service.url}")
    private String routingRulesServiceUrl;

    @Autowired
    TargetSettlementEngineAdaptor targetSettlementEngineAdaptor;

    public String receiveValidatedPaymentMessage(ValidatedPayment validatedPayment) {
        GenericMarshaller<PaymentMessageStatus> genericMarshaller = new GenericMarshaller<>();
        PaymentMessageStatus paymentMessageStatus = new PaymentMessageStatus();
        paymentMessageStatus.setAmount(validatedPayment.getAmount());
        paymentMessageStatus.setStatus(generateRandomStatus());
        paymentMessageStatus.setFromAccountName(validatedPayment.getFromAccountName());
        String paymentMessageStatusXml = genericMarshaller.marshall(paymentMessageStatus, PaymentMessageStatus.class);
        System.out.println("Completed processing validated payment message for payer: " + validatedPayment.getFromAccountName());
        return paymentMessageStatusXml;
    }

    public void retrievePaymentMessageStatus(PaymentMessageStatus paymentMessageStatus) {
        System.out.println("Completed processing validated payment message for payer: " + paymentMessageStatus.getFromAccountName());
    }

    //Assumption is the payment status might not be received in the initial SWIFT MT101 message hence we're generating a random status
    private String generateRandomStatus() {
        return RandomBooleanGenerator.generateRandomStatus() ? "Rejected" : "Not Rejected";
    }

    public void determineDestination(PaymentMessageStatus paymentMessageStatus) {
        System.out.println("Determine Destination");
        String routingRuleIdentifier = "dummyRuleIdentifier";
        String targetEngine = retrieveTargetEngineFromRoutingService(routingRuleIdentifier);

        if ("None".equalsIgnoreCase(targetEngine)) {
            System.out.println("Rules engine returned None");
            targetSettlementEngineAdaptor.settlementWithNoAcknowledgement(paymentMessageStatus);
        } else if ("Watchamakalit".equalsIgnoreCase(targetEngine)) {
            System.out.println("Rules engine returned Watchamakalit");
            targetSettlementEngineAdaptor.settlementWithAcknowledgment(paymentMessageStatus);
        }
    }

    private String retrieveTargetEngineFromRoutingService(String routingRuleIdentifier) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> searchPayload = restTemplate.getForEntity(routingRulesServiceUrl, String.class, routingRuleIdentifier);
        return searchPayload.getBody();
    }
}
