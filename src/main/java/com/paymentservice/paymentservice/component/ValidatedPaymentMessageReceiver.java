package com.paymentservice.paymentservice.component;

import com.paymentservice.paymentservice.dto.Payment;
import com.paymentservice.paymentservice.exception.PaymentServiceException;
import com.paymentservice.paymentservice.util.GenericMarshaller;
import com.paymentservice.paymentservice.util.Mapper;
import com.paymentservice.paymentservice.util.SwiftMessageConverter;
import com.prowidesoftware.swift.model.SwiftMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

import static com.paymentservice.paymentservice.util.ApplicationConstants.INCOMING_RETRIEVE_PAYMENT_MESSAGE_STATUS;
import static com.paymentservice.paymentservice.util.ApplicationConstants.PAYMENT_SERVICE_INVALID_MESSAGES;

/**
 * Class handles the initial SWIFT MT101 message passed to the microservice. It converts the message to an internal
 * Payment xml message understood by the application.
 */

@Component
public class ValidatedPaymentMessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatedPaymentMessageReceiver.class);

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private SwiftMessageConverter swiftMessageConverter;

    public void receiveValidatedPaymentMessage(String validatedPaymentSwiftMessage) {
        LOGGER.info("Running Receive Validated Payment Message");
        GenericMarshaller<Payment> genericMarshaller = new GenericMarshaller<>();
        try {
            SwiftMessage swiftMessage = swiftMessageConverter.getSwiftMessageObject(validatedPaymentSwiftMessage);
            Payment payment = Mapper.mapSwiftMessageToPayment(swiftMessage);
            LOGGER.info("Handling Payment Message for orderingCustomer: {}, for amount: {}.", payment.getOrderingCustomer(), payment.getTransactionAmount());

            String paymentXml = genericMarshaller.marshall(payment, Payment.class);
            LOGGER.info("Generated internal xml message is:");
            LOGGER.info(paymentXml);
            jmsTemplate.convertAndSend(INCOMING_RETRIEVE_PAYMENT_MESSAGE_STATUS, paymentXml);
        } catch (JAXBException | PaymentServiceException e) {
            jmsTemplate.convertAndSend(PAYMENT_SERVICE_INVALID_MESSAGES, e.getMessage() + "\n" + validatedPaymentSwiftMessage);
        }
    }
}
