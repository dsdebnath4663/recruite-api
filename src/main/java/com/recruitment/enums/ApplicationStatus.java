package com.recruitment.enums;


import com.fasterxml.jackson.annotation.JsonCreator;



public enum ApplicationStatus {
    // Define status categories and values
    SCREENING("In Review", "Qualified", "Junk Candidate", "Associated", "Applied"),
    SUBMISSIONS("Submitted-to-hiring manager", "Approved by hiring manager"),
    INTERVIEW("Interview to be scheduled", "Interview-Scheduled", "Interview in progress", "On hold", "Rejected hirable"),
    OFFERED("Offer planned", "Offer accepted", "Offer made", "Offer declined", "Offer withdrawn"),
    HIRED("Hired", "Joined", "No show", "Converted - Employee", "Converted - Temp", "Hired by hiring manager", "Hired-for-Interview", "Forward-to-Onboarding"),
    REJECTED("Unqualified", "Rejected by hiring manager", "Rejected for interview", "Rejected"),
    ARCHIVED("Archived");

    private final String[] subStatuses;

    ApplicationStatus(String... subStatuses) {
        this.subStatuses = subStatuses;
    }

    public String[] getSubStatuses() {
        return subStatuses;
    }

    @JsonCreator
    public static ApplicationStatus fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("ApplicationStatus value cannot be null or empty");
        }
        for (ApplicationStatus status : ApplicationStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid ApplicationStatus value: " + value);
    }


    public String toSentenceCase() {
        String formatted = this.name().replaceAll("_", " ").toLowerCase();
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1); // Capitalize first letter
    }
}
