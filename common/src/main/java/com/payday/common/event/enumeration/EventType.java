package com.payday.common.event.enumeration;

import lombok.Getter;

@Getter
public enum EventType {

    EMAIL_VERIFICATION("Email verification", "email-verification"),
    EMAIL_VERIFIED("Email verified", "email-verified"),
    USER_REGISTRATION("User registration", "user-registration"),
    USER_LOGIN("User login", "user-login"),
    STOCK_PRICE_UPDATE("Stock price updated", "stock-price-update"),
    NEW_USER_BALANCE("New user balance create", "new-user-balance"),
    BALANCE_RESERVE("Balance reserve", "balance-reserve"),
    BALANCE_RESERVED("Balance reserved", "balance-reserved"),
    BALANCE_INSUFFICIENT("Balance insufficient", "balance-insufficient"),
    BALANCE_RELEASE("Balance release", "balance-release");

    private final String name;
    private final String topic;

    EventType(String name, String topic) {
        this.name = name;
        this.topic = topic;
    }
}
