package com.recruitment.controller;

import com.recruitment.dto.RoleDTO;
import com.recruitment.model.UserRole;
import com.recruitment.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Role Management", description = "Endpoints for managing User Roles")
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Create a new Role",
            description = "Creates a new role and returns the created UserRole object.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created the role") ,
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<UserRole> createRole ( @RequestBody UserRole role ) {
        return ResponseEntity.ok(roleService.createRole(role));
    }

    @Operation(summary = "Retrieve all Roles",
            description = "Returns a list of all user roles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of roles")
    })
    @GetMapping
    public ResponseEntity<List<UserRole>> getAllRoles () {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @Operation(summary = "Update an existing Role",
            description = "Updates a user role based on the provided ID and role details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the role") ,
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserRole> updateRole ( @PathVariable Long id , @RequestBody UserRole roleDetails ) {
        return ResponseEntity.ok(roleService.updateRole(id , roleDetails));
    }

    @Operation(summary = "Delete a Role",
            description = "Deletes an existing user role by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted the role") ,
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole ( @PathVariable Long id ) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Get Role Hierarchy",
            description = "Fetch the complete hierarchy of roles in a tree-like structure."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the role hierarchy",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRole.class)
                    )
            ) ,
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/hierarchy")
    public ResponseEntity<List<RoleDTO>> getRoleHierarchy () {
        return ResponseEntity.ok(roleService.getRoleHierarchy());
    }
}
