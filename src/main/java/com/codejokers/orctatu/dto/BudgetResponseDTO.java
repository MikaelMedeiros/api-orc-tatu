package com.codejokers.orctatu.dto;

import com.codejokers.orctatu.enums.BodyLocal;
import com.codejokers.orctatu.enums.Detail;
import com.codejokers.orctatu.enums.PaymentMethod;
import com.codejokers.orctatu.enums.Status;
import com.codejokers.orctatu.enums.Style;

import java.math.BigDecimal;
import java.util.List;

public record BudgetResponseDTO(Long id,
                                String clientName,
                                String draw,
                                Integer centimeter,
                                BigDecimal pricePerCentimeter,
                                BodyLocal bodyLocal,
                                List<Style> styles,
                                List<Detail> details,
                                String description,
                                BigDecimal studioPercentage,
                                BigDecimal parkingCost,
                                BigDecimal materialCost,
                                BigDecimal creditCardFee,
                                BigDecimal tattooValue,
                                BigDecimal studioFee,
                                BigDecimal netValue,
                                BigDecimal grossValue,
                                BigDecimal creditCardValue,
                                Status status,
                                PaymentMethod paymentMethod) {}