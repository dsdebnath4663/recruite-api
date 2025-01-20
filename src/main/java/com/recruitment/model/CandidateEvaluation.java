    package com.recruitment.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.Data;

    import java.util.List;

    @Entity
    @Data
    public class CandidateEvaluation {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "candidate_id", nullable = false)
        @JsonIgnore // Prevents serialization of candidate details
        private Candidate candidate;

        @Embedded
        private GeneralReview generalReview;

        @OneToMany(mappedBy = "candidateEvaluation", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference // Indicates the "managed" side of the relationship
        private List<ScreeningReview> screeningReviews;
    }
