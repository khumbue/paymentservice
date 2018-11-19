package com.paymentservice.paymentservice.util;

import com.paymentservice.paymentservice.exception.PaymentServiceException;
import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SwiftMessageConverter {

    public String getXmlMessage(String swiftMessage) {
        ConversionService conversionService = new ConversionService();
        String xmlMessage = conversionService.getXml(swiftMessage);
        return xmlMessage;
    }

    public SwiftMessage getSwiftMessageObject(String swiftMessage) {
       /* ConversionService conversionService = new ConversionService();
        SwiftMessage sm = conversionService.getMessageFromFIN(swiftMessage);*/
        SwiftParser swiftParser = new SwiftParser();
        SwiftMessage sm = null;

        try {
            sm = swiftParser.parse(swiftMessage);
        } catch (IOException e) {
//            throw new PaymentServiceException(e.getMessage());
        }

        swiftParser.getErrors();
        return sm;
    }
}
