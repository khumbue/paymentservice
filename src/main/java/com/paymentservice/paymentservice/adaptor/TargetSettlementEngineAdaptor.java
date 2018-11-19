package com.paymentservice.paymentservice.adaptor;

import com.paymentservice.paymentservice.dto.PaymentMessageStatus;
import com.paymentservice.paymentservice.util.GenericMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.paymentservice.paymentservice.util.ApplicationConstants.OUTGOING_MT195_ACKNOWLEDGEMENT;

@Component
public class TargetSettlementEngineAdaptor {

    @Autowired
    JmsTemplate jmsTemplate;

    public void settlementWithNoAcknowledgement(PaymentMessageStatus paymentMessageStatus) {
        System.out.println("Target Settlement Engine Adaptor with None for: " + paymentMessageStatus.getFromAccountName());
        System.out.println("Process complete");
    }

    public void settlementWithAcknowledgment(PaymentMessageStatus paymentMessageStatus) {
        System.out.println("Target Settlement Engine Adaptor with AckNak");
        GenericMarshaller<PaymentMessageStatus> genericMarshaller = new GenericMarshaller<>();
        jmsTemplate.convertAndSend(OUTGOING_MT195_ACKNOWLEDGEMENT, genericMarshaller.marshall(paymentMessageStatus, PaymentMessageStatus.class));
    }
}
