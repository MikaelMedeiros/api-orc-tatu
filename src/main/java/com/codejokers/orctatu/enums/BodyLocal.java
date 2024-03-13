package com.codejokers.orctatu.enums;

import com.codejokers.orctatu.dto.EnumDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BodyLocal {

    HEAD("Head", "Cabeça"),
    EAR("Ear", "Orelha"),
    NECK("Neck", "Pescoço"),
    SHOULDER("Shoulder", "Ombro"),
    CLAVICLE("Clavicle", "Clavícula"),
    ARM("Arm", "Braço"),
    FOREARM("Forearm", "Antebraço"),
    HAND("Hand", "Mão"),
    CHEST("Chest", "Peito"),
    RIB("Rib", "Costela"),
    BELLY("Belly", "Barriga"),
    BACK("Back", "Costas"),
    WAIST("Waist", "Cintura"),
    GROIN("Groin", "Virilha"),
    ASS("Ass", "Nádega"),
    LEG("Leg", "Perna"),
    CALF("Calf", "Panturrilha"),
    CINNAMON("Cinnamon","Canela"),
    ANKLE("Ankle", "Tornozelo"),
    FOOT("Foot", "Pé");

    private final String name;
    private final String namePtBr;

    public static Map<String, EnumDTO> getBodyLocals() {

        final Map<String, EnumDTO> bodyLocals = new HashMap<>();
        for (final BodyLocal bodyLocal : BodyLocal.values()) {
            bodyLocals.put(bodyLocal.name(), new EnumDTO(bodyLocal.name(), bodyLocal.name, bodyLocal.namePtBr));
        }
        return bodyLocals;
    }
}