package com.recruitment.controller;

import com.recruitment.model.Candidate;
import com.recruitment.model.CandidateEvaluation;
import com.recruitment.model.GeneralReview;
import com.recruitment.model.QuestionBankTemplate;
import com.recruitment.model.QuestionReview;
import com.recruitment.model.ScreeningReview;
import com.recruitment.repository.CandidateEvaluationRepository;
import com.recruitment.repository.CandidateRepository;
import com.recruitment.repository.QuestionBankTemplateRepository;
import com.recruitment.service.CandidateEvaluationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/candidate-evaluations")
public class CandidateEvaluationController {

    private final CandidateRepository candidateRepository;
    private final CandidateEvaluationRepository candidateEvaluationRepository;
    private final QuestionBankTemplateRepository questionBankTemplateRepository;
    private  final CandidateEvaluationService candidateEvaluationService;
    public CandidateEvaluationController( CandidateRepository candidateRepository,
                                          CandidateEvaluationRepository candidateEvaluationRepository,
                                          QuestionBankTemplateRepository questionBankTemplateRepository , CandidateEvaluationService candidateEvaluationService ) {
        this.candidateRepository = candidateRepository;
        this.candidateEvaluationRepository = candidateEvaluationRepository;
        this.questionBankTemplateRepository = questionBankTemplateRepository;
        this.candidateEvaluationService = candidateEvaluationService;
    }

    @PostMapping("/{candidateId}")
    @Transactional
    public ResponseEntity<CandidateEvaluation> evaluateCandidate(
            @PathVariable Long candidateId,
            @RequestBody Map<String, Object> payload
    ) {
        // 1) Fetch Candidate
        Candidate candidate = candidateRepository.findById(candidateId)
                                                 .orElseThrow(() -> new IllegalArgumentException("Candidate not found with ID: " + candidateId));

        // 2) Create a new CandidateEvaluation entity
        CandidateEvaluation evaluation = new CandidateEvaluation();
        evaluation.setCandidate(candidate);

        // 3) Parse the generalReview section
        Map<String, Object> generalReviewMap = (Map<String, Object>) payload.get("generalReview");
        if (generalReviewMap != null) {
            GeneralReview gr = new GeneralReview();
            gr.setRating( ((Number) generalReviewMap.getOrDefault("rating", 0)).intValue() );
            gr.setCandidateStatus( (String) generalReviewMap.getOrDefault("candidateStatus", ""));
            gr.setOverallComments( (String) generalReviewMap.getOrDefault("overallComments", ""));
            evaluation.setGeneralReview(gr);
        }

        // 4) Parse the screeningReviews section
        Map<String, Object> screeningReviewsMap = (Map<String, Object>) payload.get("screeningReviews");
        List<ScreeningReview> finalScreeningList = new ArrayList<>();
        if (screeningReviewsMap != null) {
            // optional overallRating / status
            Number srOverallRating = (Number) screeningReviewsMap.getOrDefault("overallRating", 0);
            String srStatus = (String) screeningReviewsMap.getOrDefault("status", "");
            String srOverallComments = (String) screeningReviewsMap.getOrDefault("overallComments", "");

            // 4a) CandidateGeneralAssessment array
            List<Map<String, Object>> candidateGeneralAssessment = (List<Map<String, Object>>) screeningReviewsMap.get("CandidateGeneralAssessment");
            if (candidateGeneralAssessment != null) {
                for (Map<String, Object> assessment : candidateGeneralAssessment) {
                    ScreeningReview sr = new ScreeningReview();
                    // combine the top-level overallRating, status, overallComments w/ each sub-block if needed
                    sr.setOverallRating(srOverallRating.intValue());
                    sr.setStatus(srStatus);
                    sr.setOverallComments(srOverallComments);
                    sr.setCandidateEvaluation(evaluation);

                    // e.g. competencyType = Pre-Screening / Behavioral
                    String competencyType = (String) assessment.getOrDefault("competencyType", "");
                    sr.setReviewType(competencyType);

                    // questionReviews
                    List<Map<String, Object>> questionReviews = (List<Map<String, Object>>) assessment.get("questionReviews");
                    if (questionReviews != null) {
                        List<QuestionReview> questionReviewEntities = questionReviews.stream()
                                                                                     .map(qrMap -> {
                                                                                         QuestionReview qr = new QuestionReview();
                                                                                         qr.setScreeningReview(sr);

                                                                                         // rating
                                                                                         Number ratingVal = (Number) qrMap.getOrDefault("rating", 0);
                                                                                         qr.setRating(ratingVal.intValue());

                                                                                         // comments
                                                                                         String comments = (String) qrMap.getOrDefault("comments", "");
                                                                                         qr.setComments(comments);

                                                                                         // questionBankTemplateId
                                                                                         Number templateId = (Number) qrMap.get("questionBankTemplateId");
                                                                                         if (templateId == null) {
                                                                                             throw new IllegalArgumentException("questionBankTemplateId is required and cannot be null.");
                                                                                         }
                                                                                         // fetch from DB
                                                                                         QuestionBankTemplate qbt = questionBankTemplateRepository.findById(templateId.longValue())
                                                                                                                                                  .orElseThrow(() -> new IllegalArgumentException("QuestionBankTemplate not found: " + templateId));
                                                                                         qr.setQuestionBankTemplate(qbt);

                                                                                         return qr;
                                                                                     }).collect(Collectors.toList());
                        sr.setQuestionReviews(questionReviewEntities);
                    }

                    finalScreeningList.add(sr);
                }
            }
        }

        evaluation.setScreeningReviews(finalScreeningList);

        // 5) Save the evaluation
        CandidateEvaluation saved = candidateEvaluationRepository.save(evaluation);
        return ResponseEntity.ok(saved);
    }
    /**
     * 2) Get all evaluations
     */
    @GetMapping
    public ResponseEntity<List<CandidateEvaluation>> getAllEvaluations() {
        log.info("Fetching all candidate evaluations...");
        List<CandidateEvaluation> evaluations = candidateEvaluationService.getAllEvaluations();
        log.info("Found {} evaluations.", evaluations.size());
        return ResponseEntity.ok(evaluations);
    }

    /**
     * 3) Get a single evaluation by its ID
     */
    @GetMapping("/by-id/{evaluationId}")
    public ResponseEntity<CandidateEvaluation> getEvaluationById(@PathVariable Long evaluationId) {
        log.info("Fetching evaluation ID: {}", evaluationId);
        CandidateEvaluation evaluation = candidateEvaluationService.getEvaluationById(evaluationId);
        return ResponseEntity.ok(evaluation);
    }

    /**
     * 4) Get an evaluation by candidate
     */
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<CandidateEvaluation> getEvaluationByCandidate(@PathVariable Long candidateId) {
        log.info("Fetching evaluation for candidate ID: {}", candidateId);
        CandidateEvaluation evaluation = candidateEvaluationService.getEvaluationByCandidate(candidateId);
        return ResponseEntity.ok(evaluation);
    }

    /**
     * 5) Delete an evaluation by ID
     */
    @DeleteMapping("/{evaluationId}")
    public ResponseEntity<String> deleteEvaluation(@PathVariable Long evaluationId) {
        log.info("Deleting evaluation ID: {}", evaluationId);
        candidateEvaluationService.deleteEvaluation(evaluationId);
        return ResponseEntity.ok("Evaluation deleted successfully.");
    }
}
