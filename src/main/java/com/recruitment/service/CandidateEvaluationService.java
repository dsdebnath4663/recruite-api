package com.recruitment.service;

import com.recruitment.dto.CandidateEvaluationDTO;
import com.recruitment.mapper.CandidateEvaluationMapper;
import com.recruitment.model.*;
import com.recruitment.repository.CandidateEvaluationRepository;
import com.recruitment.repository.CandidateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateEvaluationService {

    private final CandidateEvaluationRepository candidateEvaluationRepository;
    private final CandidateRepository candidateRepository;
    private final CandidateEvaluationMapper candidateEvaluationMapper;

    public CandidateEvaluationService(CandidateEvaluationRepository candidateEvaluationRepository,
                                      CandidateRepository candidateRepository,
                                      CandidateEvaluationMapper candidateEvaluationMapper) {
        this.candidateEvaluationRepository = candidateEvaluationRepository;
        this.candidateRepository = candidateRepository;
        this.candidateEvaluationMapper = candidateEvaluationMapper;
    }

    /**
     * Create or update candidate evaluation.
     *
     * @param candidateId  ID of the candidate being evaluated.
     * @param evaluationDTO DTO containing evaluation details.
     * @return Saved CandidateEvaluation entity.
     */
    @Transactional
    public CandidateEvaluationDTO evaluateCandidate(Long candidateId, CandidateEvaluationDTO evaluationDTO) {
        // Fetch candidate
        Candidate candidate = candidateRepository.findById(candidateId)
                                                 .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));

        // Convert DTO to Entity
        CandidateEvaluation evaluation = new CandidateEvaluation();
        evaluation.setCandidate(candidate);

        // Map General Review
        GeneralReview generalReview = new GeneralReview();
        generalReview.setRating(evaluationDTO.getGeneralReview().getRating());
        generalReview.setCandidateStatus(evaluationDTO.getGeneralReview().getCandidateStatus());
        generalReview.setOverallComments(evaluationDTO.getGeneralReview().getOverallComments());
        evaluation.setGeneralReview(generalReview);

        // Map Screening Reviews
        List<ScreeningReview> screeningReviews = evaluationDTO.getScreeningReviews().stream().map(reviewDTO -> {
            ScreeningReview screeningReview = new ScreeningReview();
            screeningReview.setReviewType(reviewDTO.getReviewType());
            screeningReview.setOverallRating(reviewDTO.getOverallRating());
            screeningReview.setStatus(reviewDTO.getStatus());
            screeningReview.setOverallComments(reviewDTO.getOverallComments());

            // Map Question Reviews
            List<QuestionReview> questionReviews = reviewDTO.getQuestionReviews().stream().map(questionDTO -> {
                QuestionReview questionReview = new QuestionReview();
                questionReview.setQuestion(questionDTO.getQuestion());
                questionReview.setRating(questionDTO.getRating());
                questionReview.setComments(questionDTO.getComments());
                questionReview.setScreeningReview(screeningReview);
                return questionReview;
            }).collect(Collectors.toList());

            screeningReview.setQuestionReviews(questionReviews);
            screeningReview.setCandidateEvaluation(evaluation);

            return screeningReview;
        }).collect(Collectors.toList());

        evaluation.setScreeningReviews(screeningReviews);

        // Save evaluation and return as DTO
        CandidateEvaluation savedEvaluation = candidateEvaluationRepository.save(evaluation);
        return candidateEvaluationMapper.toDTO(savedEvaluation);
    }

    /**
     * Get candidate evaluation by candidate ID.
     *
     * @param candidateId Candidate ID.
     * @return CandidateEvaluationDTO entity.
     */
//    @Transactional(readOnly = true)
//    public CandidateEvaluationDTO getEvaluationByCandidate(Long candidateId) {
//        CandidateEvaluation evaluation = candidateEvaluationRepository.findByCandidateId(candidateId)
//                                                                      .orElseThrow(() -> new IllegalArgumentException("Evaluation not found for the candidate"));
//
//        // Convert Entity to DTO
//        return candidateEvaluationMapper.toDTO(evaluation);
//    }
    @Transactional(readOnly = true)
    public List<CandidateEvaluationDTO> getEvaluationByCandidate(Long candidateId) {
        List<CandidateEvaluation> evaluations = candidateEvaluationRepository.findByCandidateId(candidateId);
        if (evaluations.isEmpty()) {
            throw new IllegalArgumentException("No evaluations found for the candidate");
        }
        // Convert each evaluation to DTO
        return evaluations.stream()
                          .map(candidateEvaluationMapper::toDTO)
                          .collect(Collectors.toList());
    }


    /**
     * Delete evaluation for a candidate.
     *
     * @param evaluationId Evaluation ID.
     */
    @Transactional
    public void deleteEvaluation(Long evaluationId) {
        CandidateEvaluation evaluation = candidateEvaluationRepository.findById(evaluationId)
                                                                      .orElseThrow(() -> new IllegalArgumentException("Evaluation not found"));
        candidateEvaluationRepository.delete(evaluation);
    }
}
