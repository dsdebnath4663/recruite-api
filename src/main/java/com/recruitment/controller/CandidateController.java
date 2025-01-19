package com.recruitment.controller;

import com.recruitment.dto.CandidateDTO;
import com.recruitment.enums.Source;
import com.recruitment.model.Candidate;
import com.recruitment.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/candidates")
@Tag(name = "Candidate Management", description = "APIs for managing candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController( CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new candidate",
            description = "Adds a new candidate to the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<Candidate> createCandidate(
            @RequestBody
            @Parameter(description = "Candidate details for creating a new candidate", required = true)
            CandidateDTO candidateDTO
    ) {
        return ResponseEntity.ok(candidateService.createCandidate(candidateDTO));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get candidate by ID",
            description = "Fetches a candidate by their unique ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    public ResponseEntity<Candidate> getCandidateById(
            @PathVariable
            @Parameter(description = "Unique ID of the candidate", required = true)
            Long id
    ) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get all candidates",
            description = "Fetches a list of all candidates."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of candidates fetched successfully")
    })
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        return ResponseEntity.ok(candidateService.getAllCandidates());
    }



    @GetMapping("/source/{source}")
    @Operation(
            summary = "Get candidates by source",
            description = "Fetches a list of candidates filtered by the specified source (e.g., LinkedIn, Referral, etc.)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidates successfully fetched"),
            @ApiResponse(responseCode = "400", description = "Invalid source value"),
            @ApiResponse(responseCode = "404", description = "No candidates found for the given source")
    })
    public ResponseEntity<List<Candidate>> getCandidatesBySource(
            @PathVariable
            @Parameter(description = "The source from which candidates were acquired (e.g., LinkedIn, Referral)", required = true)
            String source
    ) {
        Source sourceEnum = Source.fromValue(source);
        return ResponseEntity.ok(candidateService.getCandidatesBySource(sourceEnum));
    }

    @GetMapping("/email/{email}")
    @Operation(
            summary = "Get candidate by email",
            description = "Fetches a candidate's details using their unique email address."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate successfully fetched"),
            @ApiResponse(responseCode = "400", description = "Invalid email format"),
            @ApiResponse(responseCode = "404", description = "No candidate found with the given email")
    })
    public ResponseEntity<Candidate> getCandidateByEmail(
            @PathVariable
            @Parameter(description = "The email address of the candidate", required = true, example = "candidate@example.com")
            String email
    ) {
        return ResponseEntity.ok(candidateService.getCandidateByEmail(email));
    }

    @GetMapping("/sources")
    public ResponseEntity<List<String>> getAllSources() {
        List<String> sources = Arrays.stream(Source.values())
                                     .map(Source::toSentenceCase)
                                     .collect(Collectors.toList());
        return ResponseEntity.ok(sources);
    }
}
