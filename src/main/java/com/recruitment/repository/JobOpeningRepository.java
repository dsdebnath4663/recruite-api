package com.recruitment.repository;

import com.recruitment.model.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {
}
