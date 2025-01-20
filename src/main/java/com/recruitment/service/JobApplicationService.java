package com.recruitment.service;


import com.recruitment.dto.ApplicationDTO;
import com.recruitment.enums.ApplicationStatus;
import com.recruitment.model.Candidate;
import com.recruitment.model.JobApplication;
import com.recruitment.model.JobOpening;
import com.recruitment.repository.CandidateRepository;
import com.recruitment.repository.JobApplicationRepository;
import com.recruitment.repository.JobOpeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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
     * Creates a new Job Application.
     *
     * @param applicationDTO Data transfer object containing application details.
     * @return Created JobApplication entity.
     */
    public JobApplication createApplication(ApplicationDTO applicationDTO) {
        Candidate candidate = candidateRepository.findById(applicationDTO.getCandidateId())
                                                 .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));

        JobOpening jobOpening = jobOpeningRepository.findById(applicationDTO.getJobOpeningId())
                                                    .orElseThrow(() -> new IllegalArgumentException("Job Opening not found"));

        JobApplication application = JobApplication.builder()
                                                   .candidate(candidate)
                                                   .jobOpening(jobOpening)
                                                   .status(ApplicationStatus.fromValue(applicationDTO.getStatus()))
                                                   .comments(applicationDTO.getComments())
                                                   .build();

        return applicationRepository.save(application);
    }

    /**
     * Fetches job applications for a specific job opening.
     *
     * Applied Change 2: findByJobOpeningIdWithDetails method ensures eager loading of related entities.
     *
     * @param jobOpeningId ID of the Job Opening.
     * @return List of Job Applications for the specified job opening.
     */
    @Transactional
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


}
