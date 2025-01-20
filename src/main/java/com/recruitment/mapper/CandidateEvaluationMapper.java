package com.recruitment.mapper;

import com.recruitment.dto.CandidateEvaluationDTO;
import com.recruitment.dto.GeneralReviewDTO;
import com.recruitment.dto.QuestionReviewDTO;
import com.recruitment.dto.ScreeningReviewDTO;
import com.recruitment.model.CandidateEvaluation;
import com.recruitment.model.GeneralReview;
import com.recruitment.model.QuestionReview;
import com.recruitment.model.ScreeningReview;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CandidateEvaluationMapper {

    public CandidateEvaluationDTO toDTO( CandidateEvaluation evaluation) {
        CandidateEvaluationDTO dto = new CandidateEvaluationDTO();
        dto.setId(evaluation.getId());
        dto.setCandidateId(evaluation.getCandidate().getId());
        dto.setGeneralReview(toGeneralReviewDTO(evaluation.getGeneralReview()));
        dto.setScreeningReviews(evaluation.getScreeningReviews().stream()
                                          .map(this::toScreeningReviewDTO)
                                          .collect(Collectors.toList()));
        return dto;
    }

    private GeneralReviewDTO toGeneralReviewDTO( GeneralReview review) {
        GeneralReviewDTO dto = new GeneralReviewDTO();
        dto.setRating(review.getRating());
        dto.setCandidateStatus(review.getCandidateStatus());
        dto.setOverallComments(review.getOverallComments());
        return dto;
    }

    private ScreeningReviewDTO toScreeningReviewDTO( ScreeningReview review) {
        ScreeningReviewDTO dto = new ScreeningReviewDTO();
        dto.setId(review.getId());
        dto.setReviewType(review.getReviewType());
        dto.setOverallRating(review.getOverallRating());
        dto.setStatus(review.getStatus());
        dto.setOverallComments(review.getOverallComments());
        dto.setQuestionReviews(review.getQuestionReviews().stream()
                                     .map(this::toQuestionReviewDTO)
                                     .collect(Collectors.toList()));
        return dto;
    }

    private QuestionReviewDTO toQuestionReviewDTO( QuestionReview review) {
        QuestionReviewDTO dto = new QuestionReviewDTO();
        dto.setId(review.getId());
        dto.setQuestion(review.getQuestion());
        dto.setRating(review.getRating());
        dto.setComments(review.getComments());
        return dto;
    }
}
