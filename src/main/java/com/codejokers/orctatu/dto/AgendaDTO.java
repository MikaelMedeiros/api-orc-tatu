package com.codejokers.orctatu.dto;

import com.google.api.client.util.DateTime;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
