package com.recruitment.dto;


import lombok.Data;

@Data
public class AttachmentDTO {

    private Long id;                // Unique identifier for the attachment
    private String attachmentType;  // Example: Resume, Cover Letter, Job Summary, Others
    private String filePath;        // File path or URL where the attachment is stored

    private Long candidateId;       // Candidate ID associated with the attachment (nullable)
    private Long jobOpeningId;      // Job Opening ID associated with the attachment (nullable)
    private Long interviewId;       // Interview ID associated with the attachment (nullable)
}
