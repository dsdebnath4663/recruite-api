package com.recruitment.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EducationalDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instituteOrSchool;
    private String majorOrDepartment;
    private String degree;
    private String durationFrom; // Format: mm/yyyy
    private String durationTo; // Format: mm/yyyy
    private boolean currentlyPursuing;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
