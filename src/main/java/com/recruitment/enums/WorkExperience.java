package com.recruitment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WorkExperience {
    NONE,
    FRESHER,
    ZERO_TO_ONE_YEAR,
    ONE_TO_THREE_YEARS,
    FOUR_TO_FIVE_YEARS,
    FIVE_PLUS_YEARS;

    @JsonCreator
    public static WorkExperience fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty");
        }
        switch (value.trim().toUpperCase()) {
            case "5_PLUS_YEARS":
            case "FIVE_PLUS_YEARS":
                return FIVE_PLUS_YEARS;
            case "0_TO_1_YEAR":
            case "ZERO_TO_ONE_YEAR":
                return ZERO_TO_ONE_YEAR;
            case "1_TO_3_YEARS":
            case "ONE_TO_THREE_YEARS":
                return ONE_TO_THREE_YEARS;
            case "4_TO_5_YEARS":
            case "FOUR_TO_FIVE_YEARS":
                return FOUR_TO_FIVE_YEARS;
            case "NONE":
                return NONE;
            case "FRESHER":
                return FRESHER;
            default:
                throw new IllegalArgumentException("Unknown value: " + value);
        }
    }
}
