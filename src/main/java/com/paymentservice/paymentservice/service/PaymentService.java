package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.component.MT195AcknowledgementSender;
import com.paymentservice.paymentservice.component.PaymentSettlementEngineSender;
import com.paymentservice.paymentservice.component.PaymentStatusMessageReceiver;
import com.paymentservice.paymentservice.component.ValidatedPaymentMessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class is used as a Facade of the internal flow. Provides methods that
 */
@Service
public class PaymentService {

    @Autowired
    ValidatedPaymentMessageReceiver validatedPaymentMessageReceiver;

    @Autowired
    PaymentStatusMessageReceiver paymentStatusMessageReceiver;

    @Autowired
    MT195AcknowledgementSender mt195AcknowledgementSender;

    @Autowired
    PaymentSettlementEngineSender paymentSettlementEngineSender;

    public void receiveValidatedPaymentMessage(String validatedPaymentSwiftMessage) {
        validatedPaymentMessageReceiver.receiveValidatedPaymentMessage(validatedPaymentSwiftMessage);
    }

    public void retrievePaymentMessageStatus(String paymentXmlMessage) {
        paymentStatusMessageReceiver.retrievePaymentMessageStatus(paymentXmlMessage);
    }

    public void sendMT195Acknowledgement(String paymentXmlMessage) {
        mt195AcknowledgementSender.sendMT195Acknowledgement(paymentXmlMessage);
    }

    public void sendPaymentToSettlementEngine(String paymentMessageXml) {
        paymentSettlementEngineSender.sendPaymentToSettlementEngine(paymentMessageXml);
    }
}
