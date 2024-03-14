package com.codejokers.orctatu.enums;

import com.codejokers.orctatu.dto.EnumDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Detail {

    SHADING("Shading", "Sombreamento"),
    POINTILLISM("Pointillism", "Pontilhismo"),
    LINES("Lines", "Linhas"),
    COLORFUL("Colorful", "Colorido"),
    WHITE_INK("White Ink", "Tinta branca");

    private final String name;
    private final String namePtBr;

    public static Map<String, EnumDTO> getDetails() {

        final Map<String, EnumDTO> details = new HashMap<>();
        for (final Detail detail : Detail.values()) {
            details.put(detail.name(), new EnumDTO(detail.name(), detail.name, detail.namePtBr));
        }
        return details;
    }
}