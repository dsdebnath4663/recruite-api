package com.recruitment.controller;

import com.recruitment.model.JobOpening;
import com.recruitment.service.JobOpeningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Job Openings Management", description = "APIs for managing job openings")
@RestController
@RequestMapping("/api/job-openings")
public class JobOpeningController {

    private final JobOpeningService jobOpeningService;

    public JobOpeningController(JobOpeningService jobOpeningService) {
        this.jobOpeningService = jobOpeningService;
    }

    @Operation(summary = "Create a new job opening", description = "Add a new job opening with detailed information.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job opening created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid job opening details provided")
    })
    @PostMapping
    public ResponseEntity<JobOpening> createJobOpening(
            @Valid @RequestBody JobOpening jobOpening) {
        return ResponseEntity.ok(jobOpeningService.createJobOpening(jobOpening));
    }

    @Operation(summary = "Get all job openings", description = "Retrieve a list of all job openings.")
    @ApiResponse(responseCode = "200", description = "List of job openings retrieved successfully")
    @GetMapping
    public ResponseEntity<List<JobOpening>> getAllJobOpenings() {
        return ResponseEntity.ok(jobOpeningService.getAllJobOpenings());
    }

    @Operation(summary = "Get job opening by ID", description = "Retrieve a specific job opening by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job opening retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Job opening not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<JobOpening> getJobOpeningById(
            @Parameter(description = "ID of the job opening to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(jobOpeningService.getJobOpeningById(id));
    }

    @Operation(summary = "Update a job opening", description = "Update the details of an existing job opening.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job opening updated successfully"),
            @ApiResponse(responseCode = "404", description = "Job opening not found"),
            @ApiResponse(responseCode = "400", description = "Invalid job opening details provided")
    })
    @PutMapping("/{id}")
    public ResponseEntity<JobOpening> updateJobOpening(
            @Parameter(description = "ID of the job opening to update") @PathVariable Long id,
            @Valid @RequestBody JobOpening jobOpening) {
        return ResponseEntity.ok(jobOpeningService.updateJobOpening(id, jobOpening));
    }

    @Operation(summary = "Delete a job opening", description = "Delete a specific job opening by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job opening deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Job opening not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobOpening(
            @Parameter(description = "ID of the job opening to delete") @PathVariable Long id) {
        jobOpeningService.deleteJobOpening(id);
        return ResponseEntity.ok("Job Opening with ID " + id + " deleted successfully.");
    }
}
