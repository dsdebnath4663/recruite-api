package com.recruitment.controller;


import com.recruitment.dto.InterviewDTO;
import com.recruitment.mapper.InterviewMapper;
import com.recruitment.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@Tag(name = "Interview Management", description = "APIs for managing interviews, including CRUD operations")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService, InterviewMapper interviewMapper) {
        this.interviewService = interviewService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new interview",
            description = "Schedules a new interview with the provided details. Accepts an InterviewDTO object and returns the created InterviewDTO.",
            tags = {"Interview Management"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interview created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<InterviewDTO> createInterview(@RequestBody InterviewDTO interviewDTO) {
        InterviewDTO interview = interviewService.createInterview(interviewDTO);
        return ResponseEntity.ok(interview);
    }

    @GetMapping
    @Operation(
            summary = "Get all interviews",
            description = "Retrieves a list of all scheduled interviews in the system.",
            tags = {"Interview Management"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of interviews retrieved successfully")
    })
    public ResponseEntity<List<InterviewDTO>> getAllInterviews() {
        List<InterviewDTO> interviews = interviewService.getAllInterviews();
        return ResponseEntity.ok(interviews);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get interview by ID",
            description = "Fetches the details of a specific interview by its unique ID.",
            tags = {"Interview Management"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interview details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Interview not found")
    })
    public ResponseEntity<InterviewDTO> getInterviewById(@PathVariable Long id) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        return ResponseEntity.ok(interview);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an interview",
            description = "Updates the details of an existing interview identified by its unique ID. Accepts an InterviewDTO with updated details.",
            tags = {"Interview Management"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interview updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Interview not found")
    })
    public ResponseEntity<InterviewDTO> updateInterview(@PathVariable Long id, @RequestBody InterviewDTO interviewDTO) {
        InterviewDTO updatedInterview = interviewService.updateInterview(id, interviewDTO);
        return ResponseEntity.ok(updatedInterview);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an interview",
            description = "Deletes a specific interview identified by its unique ID from the system.",
            tags = {"Interview Management"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interview deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Interview not found")
    })
    public ResponseEntity<String> deleteInterview(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.ok("Interview deleted successfully.");
    }
}

