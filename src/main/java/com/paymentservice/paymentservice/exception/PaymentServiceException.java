package com.paymentservice.paymentservice.exception;

public class PaymentServiceException extends Exception {

    public PaymentServiceException(String errorMessage) {
        super(errorMessage);
    }

    public PaymentServiceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
