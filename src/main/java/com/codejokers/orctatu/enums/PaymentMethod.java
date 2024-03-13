package com.codejokers.orctatu.enums;

import com.codejokers.orctatu.dto.EnumDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaymentMethod {

    PIX("Pix", "Pix"),
    MONEY("Money", "Dinheiro"),
    CREDIT_CARD("Credit card", "Cartão de crédito");

    private final String name;
    private final String namePtBr;

    public static Map<String, EnumDTO> getPaymentMethods() {

        final Map<String, EnumDTO> paymentMethods = new HashMap<>();
        for (final PaymentMethod paymentMethod : PaymentMethod.values()) {
            paymentMethods.put(paymentMethod.name(), new EnumDTO(paymentMethod.name(), paymentMethod.name, paymentMethod.namePtBr));
        }
        return paymentMethods;
    }
}