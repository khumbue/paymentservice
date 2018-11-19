package com.paymentservice.paymentservice.component;

import com.paymentservice.paymentservice.dto.Payment;
import com.paymentservice.paymentservice.util.GenericUnmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

import static com.paymentservice.paymentservice.util.ApplicationConstants.PAYMENT_SERVICE_INVALID_MESSAGES;

@Component
public class MT195AcknowledgementSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(MT195AcknowledgementSender.class);

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMT195Acknowledgement(String paymentXmlMessage) {

        GenericUnmarshaller<Payment> genericUnmarshaller = new GenericUnmarshaller<>();
        try {
            Payment payment = genericUnmarshaller.unmarshall(paymentXmlMessage, Payment.class);
            LOGGER.info("Send MT195 Acknowledgement for: " + payment.getOrderingCustomer());
            LOGGER.info("Process complete");
        } catch (JAXBException e) {
            jmsTemplate.convertAndSend(PAYMENT_SERVICE_INVALID_MESSAGES, e.getMessage() + "\n" + paymentXmlMessage);
        }
    }
}
