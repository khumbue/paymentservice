package com.paymentservice.paymentservice.util;

import com.paymentservice.paymentservice.exception.PaymentServiceException;
import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.parser.SwiftParser;
import com.prowidesoftware.swift.model.SwiftMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;

/**
 * Class uses the SwiftParser class to parse a Swift MT101 string to a SwiftMessage Java Object.
 */
@Component
public class SwiftMessageConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwiftMessageConverter.class);

    @Autowired
    private SwiftMessageCustomValidator swiftMessageCustomValidator;

    public String getXmlMessage(String swiftMessage) {
        ConversionService conversionService = new ConversionService();
        String xmlMessage = conversionService.getXml(swiftMessage);
        return xmlMessage;
    }

    public SwiftMessage getSwiftMessageObject(String swiftMessageStr) throws PaymentServiceException {
        SwiftParser swiftParser = new SwiftParser();
        SwiftMessage swiftMessage = null;

        try {
            swiftMessage = swiftParser.parse(swiftMessageStr);
        } catch (IOException e) {
            LOGGER.error("Error parsing Swift Message: \n{}",e.getMessage());
            throw new PaymentServiceException(e.getMessage(), e);
        }

        //If any error is encountered during parsing, throw a system error
        if (!CollectionUtils.isEmpty(swiftParser.getErrors())) {
            LOGGER.error("Error parsing Swift Message: \n{}", swiftParser.getErrors().toString());
            throw new PaymentServiceException(swiftParser.getErrors().toString());
        }

        swiftMessageCustomValidator.isMessageValid(swiftMessage);
        return swiftMessage;
    }
}
