package com.recruitment.dto;

import lombok.Data;

import java.util.List;

@Data
public class CandidateEvaluationDTO {
    private Long id;
    private Long candidateId;
    private GeneralReviewDTO generalReview;
    private List<ScreeningReviewDTO> screeningReviews;
}
