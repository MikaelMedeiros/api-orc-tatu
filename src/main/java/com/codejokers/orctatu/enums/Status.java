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
    DONE("Done"),
    FORGOTTEN("Forgotten"),
    UNKNOWN("Unknown"),
    CANCELED("Canceled");

    private final String name;
}