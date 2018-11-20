package com.paymentservice.paymentservice.util;

import com.paymentservice.paymentservice.exception.PaymentServiceException;
import com.prowidesoftware.swift.model.SwiftMessage;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class SwiftMessageConverterTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public SwiftMessageConverter swiftMessageConverter() {
            return new SwiftMessageConverter();
        }

        @Bean
        public SwiftMessageCustomValidator swiftMessageCustomValidator() {
            return new SwiftMessageCustomValidator();
        }
    }

    @Autowired
    SwiftMessageConverter swiftMessageConverter;

    @Test
    public void getXmlMessage() {
        String xmlMessage = swiftMessageConverter.getXmlMessage(generateSampleSwiftMessage());
        assertTrue(xmlMessage.contains("<name>20</name>"));
        assertTrue(xmlMessage.contains("123456789"));
        assertTrue(xmlMessage.contains("<name>52A</name>"));
        assertTrue(xmlMessage.contains("<value>BANKGB01XXX</value>"));
        assertTrue(xmlMessage.contains("<value>11FEB2016INV1</value>"));
    }

    @Test
    public void getSwiftMessageObject() throws Exception {
        SwiftMessage swiftMessage = swiftMessageConverter.getSwiftMessageObject(generateSampleSwiftMessage());
        String beneficiaryStr = swiftMessage.getBlock4().getTagValue("59");

        assertEquals(swiftMessage.getSender(), "LRLRXXXX4A07");
        assertEquals(swiftMessage.getReceiver(), "SAESVAV0AXXX");

        assertEquals(SwiftMessageUtil.extractAccountName(beneficiaryStr), "JAMES BOND");
        assertEquals(SwiftMessageUtil.extractAccountNumber(beneficiaryStr), "/GB12SEPA12341234123498");

        assertEquals(swiftMessage.getBlock4().getTagValue("30"), "160211");
        assertEquals(StringUtils.substring(swiftMessage.getBlock4().getTagValue("32B"), 0, 3), "EUR");
        assertEquals(new BigDecimal(StringUtils.substring(swiftMessage.getBlock4().getTagValue("32B"), 3).replace(',', '.')), new BigDecimal("123.45"));
    }

    @Test(expected = PaymentServiceException.class)
    public void whenMessageInvalid_PaymentServiceExceptionThrown() throws Exception {
        SwiftMessage swiftMessage = swiftMessageConverter.getSwiftMessageObject(generateSampleSwiftMessage2());
    }

    private String generateSampleSwiftMessage() {
        return "{1:F01SAESVAV0AXXX0466020121}{2:O1011538070522LRLRXXXX4A0700005910650705221739N}{3:{108:MT101 001 OF 019}}{4:\n" +
                ":20:123456789\n" +
                ":28D:1/1\n" +
                ":50H:/GB12SEPA12341234123412\n" +
                "ORDERING CUST NAME\n" +
                "ORDERING CUST ADDR LINE 1\n" +
                "ORDERING CUST ADDR LINE 2\n" +
                "ORDERING CUST ADDR LINE 3\n" +
                ":52A:BANKGB01XXX\n" +
                ":30:160211\n" +
                ":21:11FEB2016INV1\n" +
                ":23E:URGP\n" +
                ":32B:EUR123,45\n" +
                ":57A:BANKGB02XXX\n" +
                ":59:/GB12SEPA12341234123498\n" +
                "JAMES BOND\n" +
                "SUPPLIER ADDR LINE 1\n" +
                "SUPPLIER ADDR LINE 2\n" +
                "SUPPLIER ADDR LINE 3\n" +
                ":70:SUPPLIER-INV-REF1\n" +
                ":77B:/BENEFRES/GB\n" +
                ":71A:SHA\n" +
                "-}{5:{MAC:00000000}{CHK:24857F4599E7}{TNG:}}";
    }

    private String generateSampleSwiftMessage1() {
        return "{1:F01SAESVAV0AXXX0466020121}{2:O1011538070522LRLRXXXX4A0700005910650705221739N}{3:{108:MT101 001 OF 019}}{4:\n" +
                ":20:00028\n" +
                ":28D:1/1\n" +
                ":50H:/VTB.2003.02\n" +
                "19Apr2002\n" +
                ":30:020419\n" +
                ":21:x\n" +
                ":32B:USD1,0\n" +
                ":50L:x\n" +
                ":59:/x\n" +
                "x\n" +
                ":71A:OUR\n" +
                "-}{5:{MAC:00000000}{CHK:24857F4599E7}{TNG:}}";
    }

    private String generateSampleSwiftMessage2() {
        return "{1:F21FOOLHKH0AXXX0304009999}{4:{177:1608140809}{451:0}}{1:F01FOOLHKH0AXXX0304009999}{2:O9401609160814FOOLHKH0AXXX03040027341608141609N}{4:\n" +
//                ":20:USD940NO1\n" +
                ":21:123456/DEV\n" +
                ":25:USD234567\n" +
                ":28C:1/1\n" +
                ":60F:C160418USD672,\n" +
                ":61:160827C642,S1032\n" +
                ":86:ANDY\n" +
                ":61:160827D42,S1032\n" +
                ":86:BANK CHARGES\n" +
                ":62F:C160418USD1872,\n" +
                ":64:C160418USD1872,\n" +
                "-}{5:{CHK:0FEC1E4AEC53}{TNG:}}{S:{COP:S}}";
    }
}