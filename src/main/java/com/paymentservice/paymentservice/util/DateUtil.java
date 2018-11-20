package com.paymentservice.paymentservice.util;

import com.paymentservice.paymentservice.exception.PaymentServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String dateFormat = "yyMMdd";

    public static Date parseDate(String dateString) throws PaymentServiceException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new PaymentServiceException(e.getMessage(), e);
        }
    }
}
