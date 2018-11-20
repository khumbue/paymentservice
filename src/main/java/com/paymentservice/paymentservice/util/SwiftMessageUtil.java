package com.paymentservice.paymentservice.util;

public class SwiftMessageUtil {

    public static String extractAccountName(String accountStr) {
        String accountName = null;
        String[] strings = accountStr.split("\n");

        if (strings.length > 1) {
            accountName = strings[1];
        }
        return accountName;
    }

    public static String extractAccountNumber(String accountStr) {
        String accountNumber = null;
        String[] strings = accountStr.split("\n");
        if (strings.length > 0) {
            accountNumber = strings[0];
        }
        return accountNumber;
    }
}
