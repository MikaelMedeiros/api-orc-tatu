package com.codejokers.orctatu.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Status {
    BUDGETED("Budgeted"),
    SCHEDULED_WITHOUT_PAYMENT("Scheduled without payment"),
    SCHEDULED_PAID("Scheduled paid"),
    FORGOTTEN("Forgotten"),
    UNKNOWN("Unknown");

    private final String name;
}