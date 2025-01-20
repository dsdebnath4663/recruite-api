package com.recruitment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class QuestionReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_review_id", nullable = false)
    @JsonBackReference // Back-reference to ScreeningReview
    private ScreeningReview screeningReview;

    private String question;
    private int rating; // Out of 5
    private String comments;
}
