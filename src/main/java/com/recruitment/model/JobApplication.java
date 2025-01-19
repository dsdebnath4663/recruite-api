package com.recruitment.model;

import com.recruitment.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate; // Reference to the candidate

    @ManyToOne
    @JoinColumn(name = "job_opening_id", nullable = false)
    private JobOpening jobOpening; // Reference to the job opening

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status; // Application status

    @Lob
    private String comments; // Optional comments for the application
}
