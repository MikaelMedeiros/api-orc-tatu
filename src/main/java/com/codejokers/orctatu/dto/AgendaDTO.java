package com.codejokers.orctatu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendaDTO {

    private String budgetID;
    private String description;
    private String summary;
    @NotNull(message = "Preencha a hora de início da tutuagem.")
    private LocalDateTime startDateTime;
    @NotNull(message = "Preencha a duração da tatuagem.")
    private LocalDateTime endDateTime;
    private String tipoTattoo;
}
