package com.codejokers.orctatu.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BodyLocal {

    HEAD("Head"),
    NECK("Neck"),
    SHOULDER("Shoulder"),
    ARM("Arm"),
    HAND("Hand"),
    CHEST("Chest"),
    RIB("Rib"),
    BELLY("Belly"),
    BACK("Back"),
    ASS("Ass"),
    LEG("Leg"),
    CALF("Calf"),
    CINNAMON("Cinnamon"),
    FOOT("Foot");

    private final String name;
}