package com.codejokers.orctatu.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Style {

    FINELINE("Fineline"),
    BOLD_LINE("Bold line"),
    REALISM("Realism"),
    OLD_SCHOOL("Old school"),
    BLACK_WORK("Black work"),
    MINIMALIST("Minimalist"),
    GEEK("Geek"),
    FREE_HAND("Free Hand"),
    CALLIGRAPHY("Calligraphy"),
    NEOTRADITIONAL("Neotraditional"),
    TRIBAL("Tribal");

    private final String name;
}