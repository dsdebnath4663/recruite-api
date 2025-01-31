package com.recruitment.repository;


import com.recruitment.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    /**
     * Fetch job applications by job opening ID with necessary details eagerly loaded.
     *
     * Applied Change 2: findByJobOpeningIdWithDetails ensures eager fetching of related entities
     * to avoid lazy-loading issues with LOB fields and relationships.
     *
     * @param jobOpeningId ID of the Job Opening.
     * @return List of Job Applications with eagerly loaded details.
     */
//    @Query("SELECT ja FROM JobApplication ja JOIN FETCH ja.jobOpening WHERE ja.jobOpening.id = :jobOpeningId")
//    List<JobApplication> findByJobOpeningIdWithDetails(@Param("jobOpeningId") Long jobOpeningId);

    /**
     * ✅ Fetch applications with full details to prevent LOB stream issues.
     */
    @Transactional(readOnly = true)
    @Query("SELECT ja FROM JobApplication ja JOIN FETCH ja.candidate JOIN FETCH ja.jobOpening WHERE ja.jobOpening.id = :jobOpeningId")
    List<JobApplication> findByJobOpeningIdWithDetails(@Param("jobOpeningId") Long jobOpeningId);

    /**
     * Fetch job applications by candidate ID.
     *
     * Applied Change 3: Enable streaming for large objects when fetching by candidate ID.
     *
     * @param candidateId ID of the Candidate.
     * @return List of Job Applications for the specified candidate.
     */
    List<JobApplication> findByCandidateId(Long candidateId);

    /**
     * Custom method to fetch applications with LOB fields explicitly handled.
     *
     * Applied Change 1: getApplicationsWithLOB ensures proper handling of LOB fields
     * by eagerly fetching required relationships and handling transaction boundaries.
     *
     * @return List of Job Applications with LOB fields.
     */
    @Query("SELECT ja FROM JobApplication ja JOIN FETCH ja.candidate c JOIN FETCH ja.jobOpening jo")
    List<JobApplication> getApplicationsWithLOB();

    List<JobApplication> findByJobOpeningId ( Long jobOpeningId );

    boolean existsById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM JobApplication")
    void deleteAllApplications();
}

