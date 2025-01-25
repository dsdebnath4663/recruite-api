package com.recruitment.service;

import com.recruitment.model.*;
import com.recruitment.repository.CandidateEvaluationRepository;
import com.recruitment.repository.CandidateRepository;
import com.recruitment.repository.QuestionBankTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CandidateEvaluationService {



    private final CandidateRepository candidateRepository;
    private final CandidateEvaluationRepository candidateEvaluationRepository;
    private final QuestionBankTemplateRepository questionBankTemplateRepository;

    public CandidateEvaluationService(CandidateRepository candidateRepository,
                                      CandidateEvaluationRepository candidateEvaluationRepository,
                                      QuestionBankTemplateRepository questionBankTemplateRepository) {
        this.candidateRepository = candidateRepository;
        this.candidateEvaluationRepository = candidateEvaluationRepository;
        this.questionBankTemplateRepository = questionBankTemplateRepository;
    }

    /**
     * Creates or updates a CandidateEvaluation by parsing a raw JSON payload.
     *
     * @param candidateId ID of the Candidate
     * @param payload     Raw JSON as a map
     * @return The saved CandidateEvaluation
     */
    public CandidateEvaluation evaluateCandidate( Long candidateId, Map<String, Object> payload) {
        log.info("Evaluating candidate ID: {}", candidateId);

        Candidate candidate = candidateRepository.findById(candidateId)
                                                 .orElseThrow(() -> new IllegalArgumentException("Candidate not found with ID " + candidateId));

        CandidateEvaluation evaluation = new CandidateEvaluation();
        evaluation.setCandidate(candidate);

        // parse "generalReview"
        Map<String, Object> generalReviewMap = (Map<String, Object>) payload.get("generalReview");
        if (generalReviewMap != null) {
            GeneralReview gr = new GeneralReview();
            gr.setRating(((Number) generalReviewMap.getOrDefault("rating", 0)).intValue());
            gr.setCandidateStatus((String) generalReviewMap.getOrDefault("candidateStatus", ""));
            gr.setOverallComments((String) generalReviewMap.getOrDefault("overallComments", ""));
            evaluation.setGeneralReview(gr);
        }

        // parse "screeningReviews"
        Map<String, Object> screeningReviewsMap = (Map<String, Object>) payload.get("screeningReviews");
        if (screeningReviewsMap != null) {
            // optional overallRating, status, overallComments
            int overallRating = ((Number) screeningReviewsMap.getOrDefault("overallRating", 0)).intValue();
            String status = (String) screeningReviewsMap.getOrDefault("status", "");
            String overallComments = (String) screeningReviewsMap.getOrDefault("overallComments", "");

            List<Map<String, Object>> candidateGeneralAssessment =
                    (List<Map<String, Object>>) screeningReviewsMap.get("CandidateGeneralAssessment");

            if (candidateGeneralAssessment != null) {
                List<ScreeningReview> screeningList = new ArrayList<>();
                for (Map<String, Object> assessment : candidateGeneralAssessment) {
                    ScreeningReview sr = new ScreeningReview();
                    sr.setCandidateEvaluation(evaluation);
                    sr.setOverallRating(overallRating);
                    sr.setStatus(status);
                    sr.setOverallComments(overallComments);

                    String competencyType = (String) assessment.getOrDefault("competencyType", "");
                    sr.setReviewType(competencyType);

                    // parse questionReviews
                    List<Map<String, Object>> questionReviews =
                            (List<Map<String, Object>>) assessment.get("questionReviews");
                    if (questionReviews != null) {
                        List<QuestionReview> questionReviewEntities = questionReviews.stream()
                                                                                     .map(qrMap -> mapToQuestionReview(sr, qrMap))
                                                                                     .collect(Collectors.toList());

                        sr.setQuestionReviews(questionReviewEntities);
                    }

                    screeningList.add(sr);
                }
                evaluation.setScreeningReviews(screeningList);
            }
        }

        CandidateEvaluation saved = candidateEvaluationRepository.save(evaluation);
        log.info("Saved CandidateEvaluation with ID: {}", saved.getId());
        return saved;
    }

    private QuestionReview mapToQuestionReview(ScreeningReview screeningReview, Map<String, Object> qrMap) {
        QuestionReview qr = new QuestionReview();
        qr.setScreeningReview(screeningReview);

        // rating, comments
        qr.setRating(((Number) qrMap.getOrDefault("rating", 0)).intValue());
        qr.setComments((String) qrMap.getOrDefault("comments", ""));

        // questionBankTemplateId
        Number templateId = (Number) qrMap.get("questionBankTemplateId");
        if (templateId == null) {
            throw new IllegalArgumentException("questionBankTemplateId is required and cannot be null.");
        }
        QuestionBankTemplate qbt = questionBankTemplateRepository.findById(templateId.longValue())
                                                                 .orElseThrow(() -> new IllegalArgumentException("QuestionBankTemplate not found: " + templateId));
        qr.setQuestionBankTemplate(qbt);

        return qr;
    }

    // Additional CRUD methods

    @Transactional(readOnly = true)
    public List<CandidateEvaluation> getAllEvaluations() {
        log.debug("Fetching all candidate evaluations...");
        return candidateEvaluationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CandidateEvaluation getEvaluationById(Long evaluationId) {
        log.debug("Fetching evaluation by ID: {}", evaluationId);
        return candidateEvaluationRepository.findById(evaluationId)
                                            .orElseThrow(() -> new IllegalArgumentException("Evaluation not found with ID: " + evaluationId));
    }

    @Transactional(readOnly = true)
    public CandidateEvaluation getEvaluationByCandidate(Long candidateId) {
        log.debug("Fetching evaluation for candidate ID: {}", candidateId);
        return candidateEvaluationRepository.findByCandidateId(candidateId)
                                            .orElseThrow(() -> new IllegalArgumentException("No evaluation found for candidate: " + candidateId));
    }

    @Transactional
    public void deleteEvaluation(Long evaluationId) {
        log.debug("Deleting evaluation ID: {}", evaluationId);
        CandidateEvaluation evaluation = getEvaluationById(evaluationId);
        candidateEvaluationRepository.delete(evaluation);
        log.info("Evaluation ID: {} deleted successfully.", evaluationId);
    }
}