package com.recruitment.repository;

import com.recruitment.model.CandidateEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateEvaluationRepository extends JpaRepository<CandidateEvaluation, Long> {

    Optional<CandidateEvaluation> findByCandidateId ( Long candidateId );
}
