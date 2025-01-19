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

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final JobOpeningRepository jobOpeningRepository;

    public JobApplicationService ( JobApplicationRepository applicationRepository ,
                                   CandidateRepository candidateRepository ,
                                   JobOpeningRepository jobOpeningRepository ) {
        this.applicationRepository = applicationRepository;
        this.candidateRepository = candidateRepository;
        this.jobOpeningRepository = jobOpeningRepository;
    }

    public JobApplication createApplication ( ApplicationDTO applicationDTO ) {
        Candidate candidate = candidateRepository.findById(applicationDTO.getCandidateId())
                                                 .orElseThrow(
                                                         () -> new IllegalArgumentException("Candidate not found"));

        JobOpening jobOpening = jobOpeningRepository.findById(applicationDTO.getJobOpeningId())
                                                    .orElseThrow(() -> new IllegalArgumentException(
                                                            "Job Opening not found"));

        JobApplication application = JobApplication.builder()
                                                   .candidate(candidate)
                                                   .jobOpening(jobOpening)
                                                   .status(ApplicationStatus.fromValue(applicationDTO.getStatus()))
                                                   .comments(applicationDTO.getComments())
                                                   .build();


        return applicationRepository.save(application);
    }

    public List<JobApplication> getApplicationsByJob ( Long jobOpeningId ) {
        return applicationRepository.findByJobOpeningId(jobOpeningId);
    }

    public List<JobApplication> getApplicationsByCandidate ( Long candidateId ) {
        return applicationRepository.findByCandidateId(candidateId);
    }
}
