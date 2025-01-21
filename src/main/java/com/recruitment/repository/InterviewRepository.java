package com.recruitment.repository;

import com.recruitment.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    // Fetch interviews by candidate ID
    List<Interview> findByCandidateId(Long candidateId);

    // Fetch interviews by interviewer ID (e.g., if many-to-many relationship exists)
    List<Interview> findByInterviewers_Id(Long interviewerId);
}
