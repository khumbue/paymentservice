package com.paymentservice.paymentservice.util;

import com.paymentservice.paymentservice.dto.Payment;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class GenericMarshallerTest {

    @Test
    public void marshall() throws Exception {
        GenericMarshaller<Payment> paymentGenericMarshaller = new GenericMarshaller<>();
        String xmlString = paymentGenericMarshaller.marshall(generateSamplePayment(), Payment.class);
        assertTrue(xmlString.contains("<beneficiaryCustomer>Bruce Wayne</beneficiaryCustomer>"));
        assertTrue(xmlString.contains("<beneficiaryAccountNumber>7162964</beneficiaryAccountNumber>"));
        assertTrue(xmlString.contains("<transactionAmount>200000.00</transactionAmount>"));
    }

    public Payment generateSamplePayment() {
        Payment payment = new Payment();
        payment.setTransactionAmount(new BigDecimal("200000.00"));
        payment.setOrderingCustomer("Clark Kent");
        payment.setOrderingCustomerAccountNumber("95166505");
        payment.setBeneficiaryCustomer("Bruce Wayne");
        payment.setBeneficiaryAccountNumber("7162964");
        return payment;
    }
}