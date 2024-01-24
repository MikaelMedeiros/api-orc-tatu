package com.codejokers.orctatu.entity;

import com.codejokers.orctatu.enums.BodyLocal;
import com.codejokers.orctatu.enums.Detail;
import com.codejokers.orctatu.enums.PaymentMethod;
import com.codejokers.orctatu.enums.Status;
import com.codejokers.orctatu.enums.Style;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String googleId;
    private String clientName;
    private String draw;
    private Integer centimeter;
    private BigDecimal pricePerCentimeter;
    @Enumerated(EnumType.STRING)
    private List<BodyLocal> bodyLocals;
    @Enumerated(EnumType.STRING)
    private List<Style> styles;
    @Enumerated(EnumType.STRING)
    private List<Detail> details;
    private String description;
    private BigDecimal studioPercentage;
    private BigDecimal parkingCost;
    private BigDecimal materialCost;
    private BigDecimal creditCardFee;
    private BigDecimal tattooValue;
    private BigDecimal studioFee;
    private BigDecimal netValue;
    private BigDecimal grossValue;
    private BigDecimal creditCardValue;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
}