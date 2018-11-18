package com.paymentservice.paymentservice.util;

import com.paymentservice.paymentservice.dto.ValidatedPayment;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class GenericUnmarshallerTest {

    @Test
    public void unmarshall() throws Exception {
        GenericUnmarshaller<ValidatedPayment> validatedPaymentGenericUnMarshaller = new GenericUnmarshaller<ValidatedPayment>();
        ValidatedPayment validatedPayment = validatedPaymentGenericUnMarshaller.unmarshall(generateSampleValidatedPaymentXml(), ValidatedPayment.class);
        assertEquals("Clark Kent", validatedPayment.getToAccountName());
        assertEquals("215461644", validatedPayment.getToAccountNumber());
        assertEquals(new BigDecimal("2000.00"), validatedPayment.getAmount());
    }

    @Test
    public void unmarshallException() {
        //TODO: Test that a JAXBException is thrown if an incorrect xml is passed.
    }

    private String generateSampleValidatedPaymentXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<validatedPayment>\n" +
                "   <fromAccountName>Luke Cage</fromAccountName>\n" +
                "   <fromAccountNumber>45145105</fromAccountNumber>\n" +
                "   <amount>2000.00</amount>\n" +
                "   <toAccountName>Clark Kent</toAccountName>\n" +
                "   <toAccountNumber>215461644</toAccountNumber>\n" +
                "</validatedPayment>";
    }
}