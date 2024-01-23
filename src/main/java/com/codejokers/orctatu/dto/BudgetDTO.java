package com.codejokers.orctatu.dto;

import com.codejokers.orctatu.enums.BodyLocal;
import com.codejokers.orctatu.enums.Detail;
import com.codejokers.orctatu.enums.Style;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record BudgetDTO(@NotBlank(message = "Preencha o nome do cliente.") String clientName,
                        @NotBlank(message = "Preencha o desenho.") String draw,
                        @NotNull(message = "Preencha o tamanho em centímetros.")
                        @Min(value = 1, message = "O tamanho deve ser de pelo menos 1 centímetro.")
                        Integer centimeter,
                        @NotEmpty(message = "Preencha o(s) local(is) do corpo.") List<BodyLocal> bodyLocals,
                        @NotEmpty(message = "Preencha o(s) estilo(s).") List<Style> styles,
                        @NotEmpty(message = "Preencha o(s) detalhe(s).") List<Detail> details,
                        @NotBlank(message = "Preencha a descrição.") String description,
                        @NotNull(message = "Preencha a porcentagem de imposto.")
                        @DecimalMin(value = "0.0", message = "A porcentagem do estúdio deve estar entre 0 e 100.")
                        @DecimalMax(value = "100.0", message = "A porcentagem do estúdio deve estar entre 0 e 100.")
                        BigDecimal studioPercentage,
                        @NotNull(message = "Preencha o preço do estacionamento.")
                        @DecimalMin(value = "0.0", message = "O preço do estacionamento não pode ser menor que 0.")
                        BigDecimal parkingPrice,
                        @NotNull(message = "Preencha a porcentagem de taxa do cartão de crédito.")
                        @DecimalMin(value = "0.0", message = "A porcentagem de taxa do cartão de crédito deve estar entre 0 e 100.")
                        @DecimalMax(value = "100.0", message = "A porcentagem de taxa do cartão de crédito deve estar entre 0 e 100.")
                        BigDecimal creditFee,
                        @NotNull(message = "Preencha o preço do material.")
                        @DecimalMin(value = "0.0", message = "O preço do material não pode ser menor que 0.")
                        BigDecimal materialPrice,
                        @NotNull(message = "Preencha o preço por centímetro.")
                        @DecimalMin(value = "0.0", message = "O preço por centímetro não pode ser menor que 0.")
                        BigDecimal pricePerCentimeter) {}