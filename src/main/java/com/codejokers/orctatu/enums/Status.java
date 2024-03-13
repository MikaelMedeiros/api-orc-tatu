package com.codejokers.orctatu.enums;

import com.codejokers.orctatu.dto.EnumDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Status {

    BUDGETED("Budgeted", "Orçado"),
    SCHEDULED_WITHOUT_PAYMENT("Scheduled without payment", "Agendado sem pagamento"),
    SCHEDULED_AND_PAID("Scheduled and paid", "Agendado e pago"),
    DONE("Done", "Concluído"),
    FORGOTTEN("Forgotten", "Esquecido"),
    UNKNOWN("Unknown", "Desconhecido"),
    CANCELED("Canceled", "Cancelado");

    private final String name;
    private final String namePtBr;

    public static Map<String, EnumDTO> getStatus() {

        final Map<String, EnumDTO> status = new HashMap<>();
        for (final Status statusUnit : Status.values()) {
            status.put(statusUnit.name(), new EnumDTO(statusUnit.name(), statusUnit.name, statusUnit.namePtBr));
        }
        return status;
    }
}