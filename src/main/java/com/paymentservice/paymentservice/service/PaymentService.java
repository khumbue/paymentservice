package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.dto.PaymentMessageStatus;
import com.paymentservice.paymentservice.dto.ValidatedPayment;
import com.paymentservice.paymentservice.util.GenericMarshaller;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

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
    public String generateRandomStatus() {
        boolean bool = Math.random() < 0.5;
        return bool ? "Rejected" : "Not Rejected";
    }
}
