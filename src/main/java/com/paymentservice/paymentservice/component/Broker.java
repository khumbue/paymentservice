package com.paymentservice.paymentservice.component;

import com.paymentservice.paymentservice.dto.PaymentMessageStatus;
import com.paymentservice.paymentservice.dto.ValidatedPayment;
import com.paymentservice.paymentservice.service.PaymentService;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class Broker {

    @Autowired
    PaymentService paymentService;

    @JmsListener(destination = "incoming.validated.payment.messages")
    @SendTo("incoming.retrieve.payment.message.status")
    public PaymentMessageStatus getMessage(final ValidatedPayment validatedPayment) {
        return paymentService.receiveValidatedPaymentMessage(validatedPayment);
    }


    @JmsListener(destination = "incoming.retrieve.payment.message.status")
    public String retrievePaymentStatusMessage(final PaymentMessageStatus paymentMessageStatus) {
        paymentService.retrievePaymentMessageStatus(paymentMessageStatus);
        return "";
    }
}
