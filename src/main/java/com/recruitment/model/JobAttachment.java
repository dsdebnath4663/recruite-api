package com.recruitment.model;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class JobAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobOpening jobOpening;

    private String attachmentType; // Example: Job Summary, Others
    private String filePath;       // File path or URL to the attachment
}
