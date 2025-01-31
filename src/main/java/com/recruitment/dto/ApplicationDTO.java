package com.recruitment.dto;


import lombok.Data;

import java.util.List;

@Data
public class ApplicationDTO {

//    private Long candidateId; // ID of the Candidate
    private List<Long> candidateIds; // List of candidate IDs
    private Long jobOpeningId; // ID of the Job Opening
    private String status; // Application Status (must match the enum values)
    private String comments; // Optional comments
}
