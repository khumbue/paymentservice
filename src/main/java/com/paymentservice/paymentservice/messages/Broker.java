package com.paymentservice.paymentservice.messages;

import com.paymentservice.paymentservice.component.MT195AcknowledgementSender;
import com.paymentservice.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import static com.paymentservice.paymentservice.util.ApplicationConstants.*;

@Component
public class Broker {

    @Autowired
    PaymentService paymentService;

    @Autowired
    MT195AcknowledgementSender MT195AcknowledgementSender;

    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = INCOMING_VALIDATED_PAYMENT_MESSAGES)
    public void getMessage(String validatedPaymentSwiftMessage) {
        paymentService.receiveValidatedPaymentMessage(validatedPaymentSwiftMessage);
    }

    @JmsListener(destination = INCOMING_RETRIEVE_PAYMENT_MESSAGE_STATUS)
    public void retrievePaymentStatusMessage(String paymentMessageXml) {
        paymentService.retrievePaymentMessageStatus(paymentMessageXml);
    }

    @JmsListener(destination = OUTGOING_MT195_ACKNOWLEDGEMENT)
    public void sendMT195Acknowledgement(String paymentMessageXml) {
        paymentService.sendMT195Acknowledgement(paymentMessageXml);
    }

    @JmsListener(destination = OUTGOING_SEND_PAYMENT_SETTLEMENT_ENGINE)
    public void sendPaymentToSettlementEngine(String paymentMessageXml) {
        paymentService.sendPaymentToSettlementEngine(paymentMessageXml);
    }

    @SendTo(PAYMENT_SERVICE_INVALID_MESSAGES)
    public String handleInvalidMessages(final String invalidMessage) {
        return invalidMessage;
    }
}
