package com.recruitment.controller;

import com.recruitment.model.UserProfile;
import com.recruitment.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Profile Management", description = "Endpoints for managing User Profiles")
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "Create a new User Profile",
            description = "Creates a new profile and returns the created UserProfile object.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created the profile") ,
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<UserProfile> createProfile ( @RequestBody UserProfile profile ) {
        return ResponseEntity.ok(profileService.createProfile(profile));
    }

    @Operation(summary = "Retrieve all User Profiles",
            description = "Returns a list of all user profiles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of profiles")
    })
    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllProfiles () {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @Operation(summary = "Update an existing User Profile",
            description = "Updates a user profile based on the provided ID and profile object.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the profile") ,
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateProfile ( @PathVariable Long id , @RequestBody UserProfile updatedProfile ) {
        return ResponseEntity.ok(profileService.updateProfile(id , updatedProfile));
    }

    @Operation(summary = "Delete a User Profile",
            description = "Deletes an existing user profile by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted the profile") ,
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile ( @PathVariable Long id ) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}
