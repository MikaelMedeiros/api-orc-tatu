package com.codejokers.orctatu.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalculationSettingsDTO(@NotNull(message = "Preencha o preço por centímetro.")
                                     @DecimalMin(value = "0.0", message = "O preço por centímetro não pode ser menor que 0.")
                                     BigDecimal pricePerCentimeter,
                                     @NotNull(message = "Preencha a porcentagem do studio.")
                                     @DecimalMin(value = "0.0", message = "A porcentagem do studio deve estar entre 0 e 100.")
                                     @DecimalMax(value = "100.0", message = "A porcentagem do studio deve estar entre 0 e 100.")
                                     BigDecimal studioPercentage,
                                     @NotNull(message = "Preencha o custo do estacionamento.")
                                     @DecimalMin(value = "0.0", message = "O custo do estacionamento não pode ser menor que 0.")
                                     BigDecimal parkingCost,
                                     @NotNull(message = "Preencha o custo do material.")
                                     @DecimalMin(value = "0.0", message = "O custo do material não pode ser menor que 0.")
                                     BigDecimal materialCost,
                                     @NotNull(message = "Preencha a taxa de cartão de crédito.")
                                     @DecimalMin(value = "0.0", message = "A taxa de cartão de crédito não pode ser menor que 0.")
                                     BigDecimal creditCardFee) {}