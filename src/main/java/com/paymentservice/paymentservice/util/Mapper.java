package com.paymentservice.paymentservice.util;

import com.paymentservice.paymentservice.dto.Payment;
import com.paymentservice.paymentservice.exception.PaymentServiceException;
import com.prowidesoftware.swift.model.SwiftMessage;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mapper {

    public static Payment mapSwiftMessageToPayment(SwiftMessage swiftMessage) throws PaymentServiceException {
        Payment payment = new Payment();
        payment.setSenderReference(swiftMessage.getBlock4().getTagValue("20"));
        payment.setCurrency(StringUtils.substring(swiftMessage.getBlock4().getTagValue("32B"), 0, 3));
        payment.setDetailsOfCharges(swiftMessage.getBlock4().getTagValue("71A"));
        payment.setRequestedExecutionDate(DateUtil.parseDate(swiftMessage.getBlock4().getTagValue("30")));
        payment.setSendingInstitution(swiftMessage.getBlock4().getTagValue("51A "));
        payment.setTransactionReference(swiftMessage.getBlock4().getTagValue("21"));
        payment.setStatus(generateRandomStatus());

        String amountStr = StringUtils.substring(swiftMessage.getBlock4().getTagValue("32B"), 3);
        payment.setTransactionAmount(new BigDecimal(amountStr.replace(',', '.')));

        String orderingCustomerStr = swiftMessage.getBlock4().getTagValue("50H");
        payment.setOrderingCustomer(SwiftMessageUtil.extractAccountName(orderingCustomerStr));
        payment.setOrderingCustomerAccountNumber(SwiftMessageUtil.extractAccountNumber(orderingCustomerStr));

        String beneficiaryStr = swiftMessage.getBlock4().getTagValue("59");
        payment.setBeneficiaryCustomer(SwiftMessageUtil.extractAccountName(beneficiaryStr));
        payment.setBeneficiaryAccountNumber(SwiftMessageUtil.extractAccountNumber(beneficiaryStr));
        return payment;
    }

    //Assumption is the payment status might not be received in the initial SWIFT MT101 message hence we're generating a random status
    private static String generateRandomStatus() {
        return RandomBooleanGenerator.generateRandomStatus() ? "Rejected" : "Not Rejected";
    }
}