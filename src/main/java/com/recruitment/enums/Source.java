package com.recruitment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Source {
    NONE,
    ADDED_BY_USER,
    ADVERTISEMENT,
    API,
    CAREERSITE,
    COLD_CALL,
    EMBED,
    EMPLOYEE_REFERRAL,
    EXTERNAL_REFERRAL,
    FACEBOOK,
    GAPPS,
    GOOGLE_IMPORT,
    IMPORT,
    IMPORTED_BY_PARSER,
    INTERNAL,
    PARTNER,
    RESUME_INBOX,
    SEARCH_ENGINE,
    TWITTER,
    IMPORTED_FROM_ZOHO_CRM,
    INDEED_RESUME,
    VENDOR,
    CAREERSITE_CHATBOT,
    CALENDAR_BOOKING,
    CANDIDATE_PORTAL;

    @JsonCreator
    public static Source fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Source value cannot be null or empty");
        }
        try {
            // Convert input to enum-compatible format
            return Source.valueOf(value.toUpperCase().replaceAll(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid source value: " + value);
        }
    }

    public String toSentenceCase() {
        String formatted = this.name().replaceAll("_", " ").toLowerCase();
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1); // Capitalize first letter
    }
}

