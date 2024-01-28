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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CalculationSettings {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private BigDecimal pricePerCentimeter;
    private BigDecimal studioPercentage;
    private BigDecimal parkingCost;
    private BigDecimal materialCost;
    private BigDecimal creditCardFee;
}