package com.paymentservice.paymentservice.util;

import com.paymentservice.paymentservice.dto.ValidatedPayment;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class GenericMarshallerTest {

    @Test
    public void marshall() {
        GenericMarshaller<ValidatedPayment> validatedPaymentGenericMarshaller = new GenericMarshaller<>();
        String xmlString = validatedPaymentGenericMarshaller.marshall(generateSampleValidatedPayment(), ValidatedPayment.class);
        assertTrue(xmlString.contains("<toAccountName>Bruce Wayne</toAccountName>"));
        assertTrue(xmlString.contains("<toAccountNumber>7162964</toAccountNumber>"));
        assertTrue(xmlString.contains("<amount>200000.00</amount>"));
    }

    public ValidatedPayment generateSampleValidatedPayment() {
        ValidatedPayment validatedPayment = new ValidatedPayment();
        validatedPayment.setAmount(new BigDecimal("200000.00"));
        validatedPayment.setFromAccountName("Clark Kent");
        validatedPayment.setFromAccountNumber("95166505");
        validatedPayment.setToAccountName("Bruce Wayne");
        validatedPayment.setToAccountNumber("7162964");
        return validatedPayment;
    }
}