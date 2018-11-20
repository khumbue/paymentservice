package com.paymentservice.paymentservice.util;

import com.paymentservice.paymentservice.exception.PaymentServiceException;
import com.prowidesoftware.swift.model.SwiftMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class performs selected validations on the SwiftMessage generate from the Swift MT101 message.
 * It checks if the mandatory fields have been mapped correctly.
 */
@Component
public class SwiftMessageCustomValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwiftMessageCustomValidator.class);

    public boolean isMessageValid(SwiftMessage swiftMessage) throws PaymentServiceException {
        boolean messageValid = true;
        if (swiftMessage.getBlock1() == null
                || swiftMessage.getBlock2() == null
                || swiftMessage.getBlock3() == null
                || swiftMessage.getBlock4() == null
                || swiftMessage.getBlock5() == null) {
            LOGGER.error("Swift Message Validation error! Invalid message format! Please check message.");
            throw new PaymentServiceException("Invalid message format! Please check message.");
        }
        if (swiftMessage.getBlock4().getTagValue("20") == null) {
            LOGGER.error("Swift Message Validation error! Sender Reference required!");
            throw new PaymentServiceException("Sender Reference required!");
        }
        if (swiftMessage.getBlock4().getTagValue("50H") == null) {
            LOGGER.error("Swift Message Validation error! Ordering Customer required!");
            throw new PaymentServiceException("Ordering Customer required!");
        }
        if (swiftMessage.getBlock4().getTagValue("30") == null) {
            LOGGER.error("Swift Message Validation error! Requested Execution Date required!");
            throw new PaymentServiceException("Requested Execution Date required!");
        }
        if (swiftMessage.getBlock4().getTagValue("21") == null) {
            LOGGER.error("Swift Message Validation error! Transaction Reference required!");
            throw new PaymentServiceException("Transaction Reference required!");
        }
        if (swiftMessage.getBlock4().getTagValue("32B") == null) {
            LOGGER.error("Swift Message Validation error! Currency / Transaction Amount required!");
            throw new PaymentServiceException("Currency / Transaction Amount required!");
        }
        if (swiftMessage.getBlock4().getTagValue("59") == null) {
            LOGGER.error("Swift Message Validation error! Ordering Customer required!");
            throw new PaymentServiceException("Ordering Customer required!");
        }
        if (swiftMessage.getBlock4().getTagValue("71A") == null) {
            LOGGER.error("Swift Message Validation error! Details of Charges required!");
            throw new PaymentServiceException("Details of Charges required!");
        }
        return messageValid;
    }
}
