package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.dto.PaymentMessageStatus;
import com.paymentservice.paymentservice.dto.ValidatedPayment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public PaymentMessageStatus receiveValidatedPaymentMessage(ValidatedPayment validatedPayment) {
        PaymentMessageStatus paymentMessageStatus = new PaymentMessageStatus();
        System.out.println("Completed processing validated payment message for payer: " + validatedPayment.getFromAccountName());
        paymentMessageStatus.setAmount(validatedPayment.getAmount());
        paymentMessageStatus.setFromAccountName(validatedPayment.getFromAccountName());
        return paymentMessageStatus;
    }

    public void retrievePaymentMessageStatus(PaymentMessageStatus paymentMessageStatus) {
        System.out.println("Completed processing validated payment message for payer: " + paymentMessageStatus.getFromAccountName());
    }
}
