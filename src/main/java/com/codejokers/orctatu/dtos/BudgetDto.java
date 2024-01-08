package com.codejokers.orctatu.dtos;

import com.codejokers.orctatu.entities.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetDto {
    private String clientName;
    @NotBlank(message = "Descrição não informada.") String desc;
    @NotNull(message = "Valor líquido não informado") BigDecimal netValue;
    @NotNull(message = "Porcenagem do estúdio não informado") BigDecimal studioPercent;
    @NotNull(message = "Valor da tautagem não informado") BigDecimal tattooValue;
    private Usuario usuario;
}
