package com.recruitment.service;

import com.recruitment.dto.JobOpeningDTO;
import com.recruitment.model.JobOpening;
import com.recruitment.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<JobOpeningDTO> getAllJobOpenings() {
        List<JobOpening> jobOpenings = jobOpeningRepository.findAll();
        return jobOpenings.stream()
                          .map(this::toJobOpeningDTO) // Convert each entity to DTO
                          .collect(Collectors.toList());
    }



    // Get a job opening by ID
    public JobOpeningDTO getJobOpeningById(Long id) {
        JobOpening jobOpening = jobOpeningRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException("Job Opening not found with ID: " + id));

        return toJobOpeningDTO(jobOpening);
    }

    private JobOpeningDTO toJobOpeningDTO(JobOpening job) {
        JobOpeningDTO dto = new JobOpeningDTO();
        dto.setId(job.getId());
        dto.setPostingTitle(job.getPostingTitle());
        dto.setAssignedRecruiter(job.getAssignedRecruiter() != null ? job.getAssignedRecruiter().getFirstName() : "N/A");
        dto.setTargetDate(job.getTargetDate());
        dto.setJobOpeningStatus(job.getStatus().name()); // Convert Enum to String
        dto.setIndustry(job.getIndustry());
        dto.setSalary(job.getSalary());
        dto.setCreatedBy(job.getAssignedRecruiter() != null ? job.getAssignedRecruiter().getFirstName() : "N/A");
        dto.setCreatedOn(job.getDateOpened());
        dto.setDepartmentName(job.getDepartment() != null ? job.getDepartment().getDepartmentName() : "N/A");
        dto.setHiringManager(job.getHiringManager() != null ? job.getHiringManager().getFirstName() : "N/A");
        dto.setNumberOfPositions(1); // Update this if you have actual data for positions
        dto.setDateOpened(job.getDateOpened());
        dto.setJobType(job.getJobType().name()); // Convert Enum to String
        dto.setWorkExperience(job.getWorkExperience().name()); // Convert Enum to String
        dto.setRequiredSkills(job.getRequiredSkills());
        dto.setModifiedBy("N/A"); // Update with actual modified by user
        dto.setModifiedOn(LocalDate.now()); // Replace with actual modified date
        dto.setCity(job.getAddressInformation() != null ? job.getAddressInformation().getCity() : "N/A");
        dto.setStateProvince(job.getAddressInformation() != null ? job.getAddressInformation().getProvince() : "N/A");
        dto.setCountry(job.getAddressInformation() != null ? job.getAddressInformation().getCountry() : "N/A");
        dto.setZipPostalCode(job.getAddressInformation() != null ? job.getAddressInformation().getPostalCode() : "N/A");
        dto.setJobDescription(job.getDescriptionInformation() != null ? job.getDescriptionInformation().getJobDescription() : "N/A");
        return dto;
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
    @Transactional
    public void deleteJobOpening(Long id) {
        JobOpening jobOpening = jobOpeningRepository.findById(id)
                                                    .orElseThrow(() -> new IllegalArgumentException("Job Opening not found with ID: " + id));

        jobOpeningRepository.delete(jobOpening);
    }


    public void deleteAllJobOpenings () {
        jobOpeningRepository.deleteAllJobOpenings();
    }
}
