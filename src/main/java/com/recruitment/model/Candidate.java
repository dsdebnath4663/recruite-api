package com.recruitment.model;

import com.recruitment.enums.Source;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Info
    private String firstName;
    private String lastName;
    private String email;
    private String secondaryEmail;
    private String phone;
    private String mobile;
    private String fax;
    private String website;

    // Address Information
    @Embedded
    private AddressInformation addressInformation;

    // Professional Details
    private int experienceInYears;
    private String currentJobTitle;
    private String expectedSalary;
    private String currentSalary;
    private String currentEmployer;
    private String highestQualificationHeld;
    private String additionalInfo;
    private String skypeId;

    @ElementCollection
    private List<String> skillSet;

    // Social Links
    private String linkedIn;
    private String twitter;
    private String facebook;

    // Other Info
    private String candidateStatus;

    @ManyToOne
    @JoinColumn(name = "candidate_owner_id", nullable = false)
    private User candidateOwner; // Map candidateOwner to the User entity

    @Enumerated(EnumType.STRING) // Use enum type in the database
    @Column(nullable = false)
    private Source source; // Use Source enum instead of String

    private boolean optOut;

    // Educational Details
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EducationalDetail> educationalDetails;

    // Experience Details
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceDetail> experienceDetails;

    // Attachments
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;
}
