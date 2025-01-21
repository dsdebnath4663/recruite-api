package com.recruitment.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String attachmentType; // Example: Resume, Cover Letter, Job Summary, Others
    private String filePath;       // File path or URL to the attachment

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = true)
    private Candidate candidate; // Nullable field for candidate-specific attachments

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = true)
    private JobOpening jobOpening; // Nullable field for job-opening-specific attachments

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;
 //kk
}
