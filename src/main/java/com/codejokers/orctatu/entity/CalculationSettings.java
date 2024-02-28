package com.codejokers.orctatu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CalculationSettings {

    @Id
    @EqualsAndHashCode.Include
    private Long id;
    private BigDecimal pricePerCentimeter;
    private BigDecimal studioPercentage;
    private BigDecimal parkingCost;
    private BigDecimal materialCost;
    private BigDecimal creditCardFee;
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}