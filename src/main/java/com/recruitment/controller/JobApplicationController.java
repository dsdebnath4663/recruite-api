package com.recruitment.controller;


import com.recruitment.dto.ApplicationDTO;
import com.recruitment.enums.ApplicationStatus;
import com.recruitment.model.JobApplication;
import com.recruitment.service.JobApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
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

    /**
     * Where and When to Use

    Where:
    Controller methods that involve large object retrieval (e.g., file downloads or large text data).
    Batch processing systems where large data is streamed for transformation or export.

            When:
    When dealing with LOB fields that are too large to load into memory all at once.
    When streaming data to a client or another service is required to avoid memory overhead.
     */
    @Operation(
            summary = "Stream content of a job application",
            description = "Streams the content of a job application as either an attachment (PDF) or plain text comments, based on the type specified.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The ID of the job application",
                            required = true,
                            example = "123"
                    ),
                    @Parameter(
                            name = "type",
                            description = "The type of content to stream. Valid values are 'attachment' or 'comments'.",
                            required = true,
                            example = "attachment"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Content streamed successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/octet-stream",
                                            schema = @Schema(type = "string", format = "binary")
                                    ),
                                    @Content(
                                            mediaType = "text/plain",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid type specified",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error streaming content",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/job-applications/{id}/stream")
    public void streamContent(
            @PathVariable Long id,
            @RequestParam String type,
            HttpServletResponse response
    ) {
        try (OutputStream outputStream = response.getOutputStream()) {
            if ("attachment".equalsIgnoreCase(type)) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"attachment.pdf\"");
            } else if ("comments".equalsIgnoreCase(type)) {
                response.setContentType("text/plain");
            } else {
                throw new IllegalArgumentException("Invalid type specified. Use 'attachment' or 'comments'.");
            }

            applicationService.streamLargeObject(id, type, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error streaming content", e);
        }
    }



}
