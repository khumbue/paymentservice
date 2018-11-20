package com.paymentservice.paymentservice.component;

import com.paymentservice.paymentservice.adaptor.TargetSettlementEngineAdaptor;
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
public class PaymentSettlementEngineSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(MT195AcknowledgementSender.class);

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    TargetSettlementEngineAdaptor targetSettlementEngineAdaptor;

    public void sendPaymentToSettlementEngine(String paymentXmlMessage) {
    LOGGER.info("Running Send Payment to Settlement Engine");

        GenericUnmarshaller<Payment> genericUnmarshaller = new GenericUnmarshaller<>();
        try {
            Payment payment = genericUnmarshaller.unmarshall(paymentXmlMessage, Payment.class);

            if ("None".equalsIgnoreCase(payment.getTargetEngine())) {
                LOGGER.info("Rules engine returned None");
                targetSettlementEngineAdaptor.settlementWithNoAcknowledgement(payment);
            } else if ("Watchamakalit".equalsIgnoreCase(payment.getTargetEngine())) {
                LOGGER.info("Rules engine returned Watchamakalit");
                targetSettlementEngineAdaptor.settlementWithAcknowledgment(payment);
            }
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage());
            jmsTemplate.convertAndSend(PAYMENT_SERVICE_INVALID_MESSAGES, e.getMessage() + "\n" + paymentXmlMessage);
        }
    }
}
