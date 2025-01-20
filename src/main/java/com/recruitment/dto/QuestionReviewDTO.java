package com.recruitment.dto;

import lombok.Data;

@Data
public class QuestionReviewDTO {
    private Long id;
    private String question;
    private int rating;
    private String comments;
}
