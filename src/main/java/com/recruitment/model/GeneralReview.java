package com.recruitment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralReview {
    private int rating;
    private String candidateStatus;
    private String overallComments;
}
