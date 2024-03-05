package com.codejokers.orctatu.dto;

import com.codejokers.orctatu.enums.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendaDTO {

    private String budgetID;
    private String description;
    private String summary;
    @NotNull(message = "Preencha a hora de início da tutuagem.")
    private Long startDateTime;
    @NotNull(message = "Preencha a duração da tatuagem.")
    private Long endDateTime;
    @NotBlank(message = "Preencha o tipo de agendamento")
    private String tipoAgendamento;
    private boolean paid;
    @NotNull(message = "Preencha o tipo de pagamento.")
    private PaymentMethod paymentMehtod;
}
