package com.codejokers.orctatu.enums;

import com.codejokers.orctatu.dto.EnumDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Style {

    FINELINE("Fineline", "Fineline"),
    BOLDLINE("Boldline", "Boldline"),
    REALISM("Realism", "Realismo"),
    OLD_SCHOOL("Old school", "Old school"),
    BLACK_WORK("Black work", "Black work"),
    MINIMALIST("Minimalist", "Minimalista"),
    GEEK("Geek", "Geek"),
    FREE_HAND("Free Hand", "Free Hand"),
    CALLIGRAPHY("Calligraphy", "Caligrafia"),
    NEO_TRADITIONAL("Neo traditional", "Neo tradicional"),
    TRIBAL("Tribal", "Tribal");

    private final String name;
    private final String namePtBr;

    public static Map<String, EnumDTO> getStyles() {

        final Map<String, EnumDTO> styles = new HashMap<>();
        for (final Style style : Style.values()) {
            styles.put(style.name(), new EnumDTO(style.name(), style.name, style.namePtBr));
        }
        return styles;
    }
}