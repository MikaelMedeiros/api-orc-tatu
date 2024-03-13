package com.codejokers.orctatu.enums;

import com.codejokers.orctatu.dto.EnumDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ScheduleType {

    TATTOO("Tattoo", "Tatuagem"),
    RETOUCHING("Retouching", "Retoque");

    private final String name;
    private final String namePtBr;

    public static Map<String, EnumDTO> getScheduleTypes() {

        final Map<String, EnumDTO> scheduleTypes = new HashMap<>();
        for (final ScheduleType scheduleType : ScheduleType.values()) {
            scheduleTypes.put(scheduleType.name(), new EnumDTO(scheduleType.name(), scheduleType.name, scheduleType.namePtBr));
        }
        return scheduleTypes;
    }
}