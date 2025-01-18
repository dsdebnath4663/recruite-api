package com.recruitment.model;

import com.recruitment.enums.JobStatus;
import com.recruitment.enums.JobType;
import com.recruitment.enums.WorkExperience;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
//The @Builder annotation simplifies object creation by allowing flexible field assignment during updates.
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class JobOpening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Posting title is required")
    private String postingTitle;

    @NotBlank(message = "Title is required")
    private String title;

    @ManyToOne
    @JoinColumn(name = "assigned_recruiter_id")
    @NotNull(message = "Assigned recruiter(s) cannot be null")
    private User assignedRecruiter; // Relationship with User entity for recruiter


    @NotNull(message = "Target date cannot be null")
    @Future(message = "Target date must be a future date")
    private LocalDate targetDate;

    @NotNull(message = "Job opening status is required")
    @Enumerated(EnumType.STRING)
    private JobStatus status; // Enum for job status: None, In-progress, etc.


    private String industry;

    @Positive(message = "Salary must be a positive value")
    private String salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @NotNull(message = "Department name is required")
    private Department department; // Relationship with Department entity

    @ManyToOne
    @JoinColumn(name = "hiring_manager_id")
    @NotNull(message = "Hiring manager is required")
    private User hiringManager; // Relationship with User entity for hiring manager


    @NotNull(message = "Date opened cannot be null")
    @PastOrPresent(message = "Date opened cannot be in the future")
    private LocalDate dateOpened;

    @NotNull(message = "Job type is required")
    @Enumerated(EnumType.STRING)
    private JobType jobType; // Enum for job type

    @Size(max = 10, message = "Required skills list cannot have more than 10 skills")
    @ElementCollection
    @CollectionTable(name = "job_required_skills", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "skill")
    private List<String> requiredSkills; // List of required skills


    @Embedded
    private AddressInformation addressInformation;

    @Embedded
    private DescriptionInformation descriptionInformation;


    //For attachments, cascading ensures proper handling of child entities during updates.
    @OneToMany(mappedBy = "jobOpening", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobAttachment> attachments; // Attachment information

    @NotNull(message = "Work experience is required")
    @Enumerated(EnumType.STRING)
    private WorkExperience workExperience; // Enum for work experience


}
