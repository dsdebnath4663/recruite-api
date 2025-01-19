package com.recruitment.dto;


import lombok.Data;

@Data
public class ApplicationDTO {

    private Long candidateId; // ID of the Candidate
    private Long jobOpeningId; // ID of the Job Opening
    private String status; // Application Status (must match the enum values)
    private String comments; // Optional comments
}
