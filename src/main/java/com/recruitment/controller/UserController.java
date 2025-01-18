package com.recruitment.controller;

import com.recruitment.model.User;
import com.recruitment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "User Management", description = "Endpoints for managing Users")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new User",
            description = "Creates a new user and returns the created User object.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created the user") ,
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<User> createUser ( @RequestBody User user ) {
        return ResponseEntity.ok(userService.createUser(user));
    }
    @PostMapping("/password")
    public ResponseEntity<String> setPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("password");
        userService.setPassword(email, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }


    @Operation(summary = "Retrieve all Users",
            description = "Returns a list of all users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users")
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers () {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Delete a User",
            description = "Deletes an existing user by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted the user") ,
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser ( @PathVariable Long id ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
