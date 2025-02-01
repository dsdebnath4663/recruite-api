package com.recruitment.repository;

import com.recruitment.model.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM JobOpening")
    void deleteAllJobOpenings();
}
