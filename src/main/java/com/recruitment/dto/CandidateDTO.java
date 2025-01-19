package com.recruitment.dto;

import com.recruitment.model.AddressInformation;
import com.recruitment.model.Attachment;
import com.recruitment.model.EducationalDetail;
import com.recruitment.model.ExperienceDetail;
import lombok.Data;

import java.util.List;

@Data
public class CandidateDTO {

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
    private List<String> skillSet;

    // Social Links
    private String linkedIn;
    private String twitter;
    private String facebook;

    // Other Info
    private String candidateStatus;
//    private String candidateOwner;
    private Long candidateOwnerId; // Reference to the User ID
    private String source;
    private boolean optOut;

    // Educational Details
    private List<EducationalDetail> educationalDetails;

    // Experience Details
    private List<ExperienceDetail> experienceDetails;

    // Attachments
    private List<Attachment> attachments;
}
