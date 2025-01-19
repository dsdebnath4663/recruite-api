package com.recruitment.repository;


import com.recruitment.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobOpeningId(Long jobOpeningId);

    List<JobApplication> findByCandidateId( Long candidateId);
}
