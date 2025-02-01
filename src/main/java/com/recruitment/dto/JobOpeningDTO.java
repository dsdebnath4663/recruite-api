package com.recruitment.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class JobOpeningDTO {
    private Long id;                     // Job Opening ID
    private String postingTitle;         // Job Posting Title
    private String assignedRecruiter;    // Assigned Recruiter(s)
    private LocalDate targetDate;        // Target Date for hiring
    private String jobOpeningStatus;     // Job Opening Status
    private String industry;             // Industry type
    private String salary;               // Salary Details
    private String createdBy;            // User who created the job
    private LocalDate createdOn;         // Job Creation Date
    private String departmentName;       // Department Name
    private String hiringManager;        // Hiring Manager Name
    private int numberOfPositions;       // Number of Positions Open
    private LocalDate dateOpened;        // Job Opening Date
    private String jobType;              // Job Type (Full-Time, Contract, etc.)
    private String workExperience;       // Required Work Experience
    private List<String> requiredSkills; // List of Required Skills
    private String modifiedBy;           // Last Modified By
    private LocalDate modifiedOn;        // Last Modified Date
    private String city;                 // Job Location - City
    private String stateProvince;        // Job Location - State/Province
    private String country;              // Job Location - Country
    private String zipPostalCode;        // Job Location - Zip/Postal Code
    private String jobDescription;       // Detailed Job Description
}
