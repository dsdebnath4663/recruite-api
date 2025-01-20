package com.recruitment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ScreeningReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evaluation_id", nullable = false)
//    @JsonBackReference // Prevents infinite recursion
    @JsonIgnoreProperties("screeningReviews") // Ignore "screeningReviews" in CandidateEvaluation
    private CandidateEvaluation candidateEvaluation;

    private String reviewType; // e.g., Pre-Screening, Behavioral, etc.

    @OneToMany(mappedBy = "screeningReview", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference // Manages the relationship to QuestionReview
    @JsonIgnoreProperties("screeningReview") // Ignore "screeningReview" in QuestionReview
    private List<QuestionReview> questionReviews;

    private int overallRating;
    private String status;
    private String overallComments;
}
