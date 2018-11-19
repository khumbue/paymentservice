package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.dto.PaymentMessageStatus;
import org.springframework.stereotype.Service;

@Service
public class AcknowledgementSenderService {

    public void sendMT195Acknowledgement(PaymentMessageStatus paymentMessageStatus) {
        System.out.println("Send MT195 Acknowledgement for: " + paymentMessageStatus.getFromAccountName());
        System.out.println("Process complete");
    }
}
