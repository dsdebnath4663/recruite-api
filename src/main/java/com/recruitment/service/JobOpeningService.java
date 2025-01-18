package com.recruitment.service;

import com.recruitment.model.JobOpening;
import com.recruitment.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOpeningService {

    private final JobOpeningRepository jobOpeningRepository;

    public JobOpeningService(JobOpeningRepository jobOpeningRepository) {
        this.jobOpeningRepository = jobOpeningRepository;
    }

    // Create a new job opening
    public JobOpening createJobOpening(JobOpening jobOpening) {
        return jobOpeningRepository.save(jobOpening);
    }

    // Get all job openings
    public List<JobOpening> getAllJobOpenings() {
        return jobOpeningRepository.findAll();
    }

    // Get a job opening by ID
    public JobOpening getJobOpeningById(Long id) {
        return jobOpeningRepository.findById(id)
                                   .orElseThrow(() -> new IllegalArgumentException("Job Opening not found with ID: " + id));
    }

    // Update an existing job opening
//    public JobOpening updateJobOpening(Long id, JobOpening jobOpeningDetails) {
//        JobOpening jobOpening = getJobOpeningById(id);
//
//        jobOpening.setPostingTitle(jobOpeningDetails.getPostingTitle());
//        jobOpening.setTitle(jobOpeningDetails.getTitle());
//        jobOpening.setAssignedRecruiter(jobOpeningDetails.getAssignedRecruiter());
//        jobOpening.setTargetDate(jobOpeningDetails.getTargetDate());
//        jobOpening.setStatus(jobOpeningDetails.getStatus());
//        jobOpening.setIndustry(jobOpeningDetails.getIndustry());
//        jobOpening.setSalary(jobOpeningDetails.getSalary());
//        jobOpening.setDepartment(jobOpeningDetails.getDepartment());
//        jobOpening.setHiringManager(jobOpeningDetails.getHiringManager());
//        jobOpening.setDateOpened(jobOpeningDetails.getDateOpened());
//        jobOpening.setJobType(jobOpeningDetails.getJobType());
//        jobOpening.setRequiredSkills(jobOpeningDetails.getRequiredSkills());
//        jobOpening.setCity(jobOpeningDetails.getCity());
//        jobOpening.setProvince(jobOpeningDetails.getProvince());
//        jobOpening.setCountry(jobOpeningDetails.getCountry());
//        jobOpening.setPostalCode(jobOpeningDetails.getPostalCode());
//        jobOpening.setJobDescription(jobOpeningDetails.getJobDescription());
//        jobOpening.setRequirements(jobOpeningDetails.getRequirements());
//        jobOpening.setBenefits(jobOpeningDetails.getBenefits());
//
//        return jobOpeningRepository.save(jobOpening);
//    }

    public JobOpening updateJobOpening(Long id, JobOpening jobOpeningDetails) {
        JobOpening existingJobOpening = jobOpeningRepository.findById(id)
                                                            .orElseThrow(() -> new IllegalArgumentException("JobOpening not found with ID: " + id));

        // Using the builder to update the fields
        JobOpening updatedJobOpening = JobOpening.builder()
                                                 .id(existingJobOpening.getId()) // Preserve the same ID
                                                 .postingTitle(jobOpeningDetails.getPostingTitle() != null ? jobOpeningDetails.getPostingTitle() : existingJobOpening.getPostingTitle())
                                                 .title(jobOpeningDetails.getTitle() != null ? jobOpeningDetails.getTitle() : existingJobOpening.getTitle())
                                                 .assignedRecruiter(jobOpeningDetails.getAssignedRecruiter() != null ? jobOpeningDetails.getAssignedRecruiter() : existingJobOpening.getAssignedRecruiter())
                                                 .targetDate(jobOpeningDetails.getTargetDate() != null ? jobOpeningDetails.getTargetDate() : existingJobOpening.getTargetDate())
                                                 .status(jobOpeningDetails.getStatus() != null ? jobOpeningDetails.getStatus() : existingJobOpening.getStatus())
                                                 .industry(jobOpeningDetails.getIndustry() != null ? jobOpeningDetails.getIndustry() : existingJobOpening.getIndustry())
                                                 .salary(jobOpeningDetails.getSalary() != null ? jobOpeningDetails.getSalary() : existingJobOpening.getSalary())
                                                 .department(jobOpeningDetails.getDepartment() != null ? jobOpeningDetails.getDepartment() : existingJobOpening.getDepartment())
                                                 .hiringManager(jobOpeningDetails.getHiringManager() != null ? jobOpeningDetails.getHiringManager() : existingJobOpening.getHiringManager())
                                                 .dateOpened(jobOpeningDetails.getDateOpened() != null ? jobOpeningDetails.getDateOpened() : existingJobOpening.getDateOpened())
                                                 .jobType(jobOpeningDetails.getJobType() != null ? jobOpeningDetails.getJobType() : existingJobOpening.getJobType())
                                                 .requiredSkills(jobOpeningDetails.getRequiredSkills() != null ? jobOpeningDetails.getRequiredSkills() : existingJobOpening.getRequiredSkills())
                                                 .addressInformation(jobOpeningDetails.getAddressInformation() != null ? jobOpeningDetails.getAddressInformation() : existingJobOpening.getAddressInformation())
                                                 .descriptionInformation(jobOpeningDetails.getDescriptionInformation() != null ? jobOpeningDetails.getDescriptionInformation() : existingJobOpening.getDescriptionInformation())
                                                 .attachments(jobOpeningDetails.getAttachments() != null ? jobOpeningDetails.getAttachments() : existingJobOpening.getAttachments())
                                                 .workExperience(jobOpeningDetails.getWorkExperience() != null ? jobOpeningDetails.getWorkExperience() : existingJobOpening.getWorkExperience())
                                                 .build();

        return jobOpeningRepository.save(updatedJobOpening);
    }


    // Delete a job opening
    public void deleteJobOpening(Long id) {
        JobOpening jobOpening = getJobOpeningById(id);
        jobOpeningRepository.delete(jobOpening);
    }
}
