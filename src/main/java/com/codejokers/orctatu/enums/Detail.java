package com.codejokers.orctatu.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Detail {

    SHADING("Shading"),
    POINTILLISM("Pointillism"),
    LINES("Lines"),
    COLORFUL("Colorful"),
    WHITE_INK("White Ink");

    private final String name;
}