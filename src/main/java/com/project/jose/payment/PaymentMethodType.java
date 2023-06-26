package com.project.jose.payment;

public enum PaymentMethodType {
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    NET_BANKING("Net Banking");

    private final String method;

    PaymentMethodType(String method) {
        this.method = method;
    }

    public final String getMethod() {
        return method;
    }
}