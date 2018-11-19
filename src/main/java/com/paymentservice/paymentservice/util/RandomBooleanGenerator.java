package com.paymentservice.paymentservice.util;

public class RandomBooleanGenerator {
    public static boolean generateRandomStatus() {
        return Math.random() < 0.5;
    }
}
