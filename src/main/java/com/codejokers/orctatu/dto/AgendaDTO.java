package com.codejokers.orctatu.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendaDTO {

    private String budgetID;
    private String description;
    private String summary;
    private LocalDateTime startDateTime;
    private String tipoTattoo;
}
