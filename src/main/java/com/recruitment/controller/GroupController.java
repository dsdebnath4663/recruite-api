package com.recruitment.controller;

import com.recruitment.model.UserGroup;
import com.recruitment.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Group Management", description = "Endpoints for managing Groups")
@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "Create a new Group",
            description = "Creates a new Group and returns the created Group object.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created Group") ,
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<UserGroup> createGroup ( @RequestBody UserGroup group ) {
        return ResponseEntity.ok(groupService.createGroup(group));
    }

    @Operation(summary = "Retrieve all Groups",
            description = "Returns a list of all Groups.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of Groups")
    })
    @GetMapping
    public ResponseEntity<List<UserGroup>> getAllGroups () {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @Operation(summary = "Update an existing Group / Add Users to a Group",
            description = "Updates a Group based on the provided ID and Group object.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated Group") ,
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserGroup> updateGroup ( @PathVariable Long id , @RequestBody UserGroup updatedGroup ) {
        return ResponseEntity.ok(groupService.updateGroup(id , updatedGroup));
    }

    @Operation(summary = "Delete a Group",
            description = "Deletes an existing Group by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted Group") ,
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup ( @PathVariable Long id ) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
