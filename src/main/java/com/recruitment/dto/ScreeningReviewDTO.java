package com.recruitment.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScreeningReviewDTO {
    private Long id;
    private String reviewType;
    private List<QuestionReviewDTO> questionReviews;
    private int overallRating;
    private String status;
    private String overallComments;
}
