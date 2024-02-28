package com.codejokers.orctatu.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BodyLocal {

    HEAD("Head", "Cabeça"),
    EAR("Ear", "Orelha"),
    NECK("Neck", "Pescoço"),
    SHOULDER("Shoulder", "Ombro"),
    ARM("Arm", "Braço"),
    FOREARM("Forearm", "Antebraço"),
    HAND("Hand", "Mão"),
    CHEST("Chest", "Peito"),
    RIB("Rib", "Costela"),
    BELLY("Belly", "Barriga"),
    BACK("Back", "Costa"),
    ASS("Ass", "Nádega"),
    LEG("Leg", "Perna"),
    CALF("Calf", "Panturrilha"),
    CINNAMON("Cinnamon","Canela"),
    FOOT("Foot", "Pé");

    private final String name;
    private final String ptbr;
}