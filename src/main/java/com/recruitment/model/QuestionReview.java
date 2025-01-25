package com.recruitment.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_review_id", nullable = false)
    @JsonBackReference
    private ScreeningReview screeningReview;

    // We'll store only rating/comments in JSON,
    // but we also want to link to a QuestionBankTemplate by ID.
    private int rating;
    private String comments;

    @ManyToOne
    @JoinColumn(name = "question_bank_id")
    private QuestionBankTemplate questionBankTemplate; // Referenced via the ID
}
