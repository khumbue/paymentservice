package com.paymentservice.paymentservice.util;

import com.paymentservice.paymentservice.dto.Payment;
import com.prowidesoftware.swift.model.SwiftMessage;

public class Mapper {
    public static Payment mapSwiftMessageToPayment(SwiftMessage swiftMessage) {
        Payment payment = new Payment();
        payment.setSenderReference(swiftMessage.getBlock4().getTagValue(""));
        /*payment.setBeneficiaryAccountNumber();
        payment.setBeneficiaryCustomer();
        payment.setCurrency();
        payment.setDetailsOfCharges();
        payment.setOrderingCustomer();
        payment.setOrderingCustomerAccountNumber();
        payment.setRequestedExecutionDate();
        payment.setSendingInstitution();
        payment.setTransactionAmount();
        payment.setTransactionReference();*/
        payment.setStatus(generateRandomStatus());
        return payment;
    }

    //Assumption is the payment status might not be received in the initial SWIFT MT101 message hence we're generating a random status
    private static String generateRandomStatus() {
        return RandomBooleanGenerator.generateRandomStatus() ? "Rejected" : "Not Rejected";
    }

}
