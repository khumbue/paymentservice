package com.paymentservice.paymentservice.component;

import com.paymentservice.paymentservice.dto.PaymentMessageStatus;
import com.paymentservice.paymentservice.dto.ValidatedPayment;
import com.paymentservice.paymentservice.service.PaymentService;
import com.paymentservice.paymentservice.util.GenericUnmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

import static com.paymentservice.paymentservice.util.ApplicationConstants.INCOMING_RETRIEVE_PAYMENT_MESSAGE_STATUS;
import static com.paymentservice.paymentservice.util.ApplicationConstants.INCOMING_VALIDATED_PAYMENT_MESSAGES;
import static com.paymentservice.paymentservice.util.ApplicationConstants.PAYMENT_SERVICE_INVALID_MESSAGES;

@Component
public class Broker {

    @Autowired
    PaymentService paymentService;

    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = INCOMING_VALIDATED_PAYMENT_MESSAGES)
    public void getMessage(final String validatedPaymentXml) {
        System.out.print(validatedPaymentXml);
        GenericUnmarshaller<ValidatedPayment> genericUnmarshaller = new GenericUnmarshaller<>();

        try {
            ValidatedPayment validatedPayment = (ValidatedPayment) genericUnmarshaller.unmarshall(validatedPaymentXml, ValidatedPayment.class);
            String paymentMessageStatusXml = paymentService.receiveValidatedPaymentMessage(validatedPayment);
            jmsTemplate.convertAndSend(INCOMING_RETRIEVE_PAYMENT_MESSAGE_STATUS, paymentMessageStatusXml);
        } catch (JAXBException e) {
            //TODO: Send error cause to someone who monitors or handles the error queue.
            jmsTemplate.convertAndSend(PAYMENT_SERVICE_INVALID_MESSAGES, validatedPaymentXml);
        }
    }

    @JmsListener(destination = INCOMING_RETRIEVE_PAYMENT_MESSAGE_STATUS)
    public void retrievePaymentStatusMessage(final String paymentMessageStatusXml) {
        System.out.print("*****");
        GenericUnmarshaller<PaymentMessageStatus> genericUnmarshaller = new GenericUnmarshaller<>();
        try {
            PaymentMessageStatus paymentMessageStatus = (PaymentMessageStatus) genericUnmarshaller.unmarshall(paymentMessageStatusXml, PaymentMessageStatus.class);
            if ("Rejected".equalsIgnoreCase(paymentMessageStatus.getStatus())) {
                System.out.print("Status is Rejected");
            } else {
                System.out.print("Status is Not Rejected");
            }
        } catch (JAXBException e) {
            //TODO: Send error cause to someone who monitors or handles the error queue.
            jmsTemplate.convertAndSend(PAYMENT_SERVICE_INVALID_MESSAGES, paymentMessageStatusXml);
        }

    }

    @SendTo(PAYMENT_SERVICE_INVALID_MESSAGES)
    public String handleInvalidMessages(final String invalidMessage) {
        return invalidMessage;
    }
}
