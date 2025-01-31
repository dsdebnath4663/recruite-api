package com.recruitment.service;


import com.recruitment.dto.ApplicationDTO;
import com.recruitment.enums.ApplicationStatus;
import com.recruitment.model.Candidate;
import com.recruitment.model.JobApplication;
import com.recruitment.model.JobOpening;
import com.recruitment.repository.CandidateRepository;
import com.recruitment.repository.JobApplicationRepository;
import com.recruitment.repository.JobOpeningRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
@Service
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final JobOpeningRepository jobOpeningRepository;

    public JobApplicationService(JobApplicationRepository applicationRepository,
                                 CandidateRepository candidateRepository,
                                 JobOpeningRepository jobOpeningRepository) {
        this.applicationRepository = applicationRepository;
        this.candidateRepository = candidateRepository;
        this.jobOpeningRepository = jobOpeningRepository;
    }

    /**
     * Prevents duplicate applications – If a candidate has already applied for the job, they won't be added again.
     * ✅ Returns an error if all candidates have already applied – This avoids unnecessary database writes.
     * ✅ Improves efficiency – We batch process only new applications, reducing DB load.
     */
    @Transactional
    public List<JobApplication> createApplications(ApplicationDTO applicationDTO) {
        JobOpening jobOpening = jobOpeningRepository.findById(applicationDTO.getJobOpeningId())
                                                    .orElseThrow(() -> new IllegalArgumentException("Job Opening not found"));

        List<JobApplication> existingApplications = applicationRepository.findByJobOpeningId(applicationDTO.getJobOpeningId());

        Set<Long> existingCandidateIds = existingApplications.stream()
                                                             .map(app -> app.getCandidate().getId())
                                                             .collect(Collectors.toSet());

        List<JobApplication> newApplications = applicationDTO.getCandidateIds().stream()
                                                             .filter(candidateId -> !existingCandidateIds.contains(candidateId)) // Skip existing applications
                                                             .map(candidateId -> {
                                                                 Candidate candidate = candidateRepository.findById(candidateId)
                                                                                                          .orElseThrow(() -> new IllegalArgumentException("Candidate not found with ID: " + candidateId));

                                                                 return JobApplication.builder()
                                                                                      .candidate(candidate)
                                                                                      .jobOpening(jobOpening)
                                                                                      .status(ApplicationStatus.fromValue(applicationDTO.getStatus()))
                                                                                      .comments(applicationDTO.getComments())
                                                                                      .build();
                                                             })
                                                             .collect(Collectors.toList());

        if (newApplications.isEmpty()) {
            throw new IllegalArgumentException("All candidates have already applied for this job.");
        }

        return applicationRepository.saveAll(newApplications);
    }


    /**
     * Fetches job applications for a specific job opening.
     *
     * Applied Change 2: findByJobOpeningIdWithDetails method ensures eager loading of related entities.
     *
     * @param jobOpeningId ID of the Job Opening.
     * @return List of Job Applications for the specified job opening.
     */
    @Transactional(readOnly = true)
    public List<JobApplication> getApplicationsByJob(Long jobOpeningId) {
        return applicationRepository.findByJobOpeningIdWithDetails(jobOpeningId);
    }

    /**
     * Fetches job applications for a specific candidate.
     *
     * Applied Change 3: Enabled streaming for large objects by wrapping this method in @Transactional.
     *
     * @param candidateId ID of the Candidate.
     * @return List of Job Applications for the specified candidate.
     */
    @Transactional // Ensures proper transaction management for LOB fields
    public List<JobApplication> getApplicationsByCandidate(Long candidateId) {
        return applicationRepository.findByCandidateId(candidateId);
    }

    @Transactional
    public void streamLargeObject(Long applicationId, String contentType, OutputStream outputStream) throws IOException {
        // Fetch the job application with its LOB fields
        JobApplication application = applicationRepository.findById(applicationId)
                                                          .orElseThrow(() -> new IllegalArgumentException("Job Application not found"));

        InputStream inputStream = null;

        try {
            switch (contentType.toLowerCase()) {
//                case "attachment":
//                    if (application.getAttachment() != null) {
//                        inputStream = new ByteArrayInputStream(application.getAttachment());
//                    } else {
//                        throw new IllegalArgumentException("No attachment found for this application");
//                    }
//                    break;

                case "comments":
                    if (application.getComments() != null) {
                        inputStream = new ByteArrayInputStream(application.getComments().getBytes());
                    } else {
                        throw new IllegalArgumentException("No comments found for this application");
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid content type specified. Use 'attachment' or 'comments'.");
            }

            // Stream the content
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }



    @Transactional(readOnly = true)
    public List<JobApplication> getAllJobApplications() {
        return applicationRepository.findAll();
    }


    @Transactional
    public void deleteApplicationById(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new IllegalArgumentException("Job application not found with ID: " + id);
        }
        log.info("Deleting job application with ID: {}", id);
        applicationRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllApplications() {
        log.info("Deleting all job applications.");
        applicationRepository.deleteAll();
    }
}
