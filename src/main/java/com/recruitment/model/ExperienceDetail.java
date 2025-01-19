package com.recruitment.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ExperienceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String occupationOrTitle;
    private String company;
    @Lob
    private String summary;
    private String workDurationFrom; // Format: mm/yyyy
    private String workDurationTo; // Format: mm/yyyy
    private boolean currentlyWorking;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
