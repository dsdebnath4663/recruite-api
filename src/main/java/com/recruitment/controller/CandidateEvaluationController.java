package com.recruitment.controller;


import com.recruitment.dto.CandidateEvaluationDTO;
import com.recruitment.service.CandidateEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate-evaluations")
public class CandidateEvaluationController {

    private final CandidateEvaluationService candidateEvaluationService;

    public CandidateEvaluationController(CandidateEvaluationService candidateEvaluationService) {
        this.candidateEvaluationService = candidateEvaluationService;
    }

    /**
     * Create or update a candidate evaluation.
     */
    @PostMapping("/{candidateId}")
    @Operation(
            summary = "Create or update candidate evaluation",
            description = "Creates or updates an evaluation for a specific candidate based on the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluation created/updated successfully"),
            @ApiResponse(responseCode = "404", description = "Candidate not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<CandidateEvaluationDTO> evaluateCandidate(
            @PathVariable Long candidateId,
            @RequestBody CandidateEvaluationDTO evaluationDTO
    ) {
        CandidateEvaluationDTO evaluation = candidateEvaluationService.evaluateCandidate(candidateId, evaluationDTO);
        return ResponseEntity.ok(evaluation);
    }
//    getEvaluationsByCandidate
    /**
     * Get evaluation for a specific candidate.
     */
    @GetMapping("/{candidateId}")
    @Operation(summary = "Get candidate evaluations",
            description = "Fetches all evaluations for a specific candidate.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluations fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No evaluations found for the candidate")
    })
    public ResponseEntity<List<CandidateEvaluationDTO>> getEvaluationByCandidate(@PathVariable Long candidateId) {
        List<CandidateEvaluationDTO> evaluation = candidateEvaluationService.getEvaluationByCandidate(candidateId);
        return ResponseEntity.ok(evaluation);
    }

    /**
     * Delete a candidate evaluation.
     */
    @DeleteMapping("/{evaluationId}")
    @Operation(
            summary = "Delete candidate evaluation",
            description = "Deletes an evaluation for a specific candidate."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Evaluation not found")
    })
    public ResponseEntity<String> deleteEvaluation(@PathVariable Long evaluationId) {
        candidateEvaluationService.deleteEvaluation(evaluationId);
        return ResponseEntity.ok("Candidate evaluation deleted successfully.");
    }
}
