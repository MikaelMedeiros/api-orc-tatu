package com.codejokers.orctatu.entities;

import com.codejokers.orctatu.enums.BudgetStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;
    private String desc;
    private BigDecimal netValue;
    private BigDecimal studioPercent;
    private BigDecimal tattooValue;

    @Enumerated(EnumType.STRING)
    private BudgetStatus status;

    @ManyToOne
    private Usuario usuario;
}
