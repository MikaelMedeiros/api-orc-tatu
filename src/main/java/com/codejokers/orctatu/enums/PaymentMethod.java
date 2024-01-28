package com.codejokers.orctatu.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaymentMethod {

    PIX("Pix"),
    MONEY("Money"),
    CREDIT_CARD("Credit card");

    private final String name;
}