package com.recruitment.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class GeneralReview {
    private int rating; // Out of 5
    private String candidateStatus;
    private String overallComments;
}
