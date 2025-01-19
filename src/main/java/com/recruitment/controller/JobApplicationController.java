package com.recruitment.controller;


import com.recruitment.dto.ApplicationDTO;
import com.recruitment.enums.ApplicationStatus;
import com.recruitment.model.JobApplication;
import com.recruitment.service.JobApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService applicationService;

    public JobApplicationController(JobApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new job application",
            description = "Creates a new job application based on the provided details in the request body."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job application created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid application details provided")
    })
    public ResponseEntity<JobApplication> createApplication(@RequestBody ApplicationDTO applicationDTO) {
        return ResponseEntity.ok(applicationService.createApplication(applicationDTO));
    }

    @GetMapping("/job/{jobOpeningId}")
    @Operation(
            summary = "Get applications by job opening",
            description = "Fetches all job applications associated with a specific job opening ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of applications fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Job opening not found")
    })
    public ResponseEntity<List<JobApplication>> getApplicationsByJob(@PathVariable Long jobOpeningId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJob(jobOpeningId));
    }

    @GetMapping("/candidate/{candidateId}")
    @Operation(
            summary = "Get applications by candidate",
            description = "Fetches all job applications submitted by a specific candidate ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of applications fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Candidate not found")
    })
    public ResponseEntity<List<JobApplication>> getApplicationsByCandidate(@PathVariable Long candidateId) {
        return ResponseEntity.ok(applicationService.getApplicationsByCandidate(candidateId));
    }

    @GetMapping("/statuses")
    @Operation(
            summary = "Get all application statuses",
            description = "Fetches all application statuses and their respective sub-statuses for dropdown use."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application statuses fetched successfully")
    })
    public ResponseEntity<Map<String, List<String>>> getAllApplicationStatuses() {
        // Convert ApplicationStatus enums to a map of status -> sub-statuses
        Map<String, List<String>> statuses = Arrays.stream(ApplicationStatus.values())
                                                   .collect(Collectors.toMap(
                                                           ApplicationStatus::toSentenceCase, // Convert enum name to sentence case
                                                           status -> Arrays.asList(status.getSubStatuses()) // Get sub-statuses as a list
                                                   ));
        return ResponseEntity.ok(statuses);
    }
}
