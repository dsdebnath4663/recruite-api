package com.recruitment.controller;

import com.recruitment.dto.UserResponseDTO;
import com.recruitment.model.User;
import com.recruitment.service.UserService;
import com.recruitment.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Endpoints for user authentication and password management")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Authenticate user and generate JWT token",
            description = "Takes username and password as input, authenticates the user, and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                    content = @Content(schema = @Schema(example = "{\"token\": \"eyJhbGci...\"}"))),
            @ApiResponse(responseCode = "401", description = "Authentication failed",
                    content = @Content(schema = @Schema(example = "{\"error\": \"Invalid credentials\"}")))
    })
    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        // Authenticate the user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Generate a JWT token
        String token = jwtUtil.generateToken(username, Map.of());

        // Fetch the user by email (username in this case)
        User user = userService.getUserDetailsByEmail(username);

        // Map User entity to UserResponseDTO
        UserResponseDTO userResponse = UserResponseDTO.builder()
                                                      .id(user.getId())
                                                      .firstName(user.getFirstName())
                                                      .lastName(user.getLastName())
                                                      .email(user.getEmail())
                                                      .phone(user.getPhone())
                                                      .address(user.getAddress())
                                                      .territory(user.getTerritory())
                                                      .role(user.getRole() != null ? user.getRole().getName() : null) // Assuming UserRole has a getName() method
                                                      .profile(user.getProfile() != null ? user.getProfile().getName() : null) // Assuming UserProfile has a getName() method
                                                      .enabled(user.isEnabled())
                                                      .token(token) // Add the token to the DTO
                                                      .build();

        return userResponse;
    }



    @Operation(summary = "Update user password",
            description = "Allows a user to set or update their password based on their email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully",
                    content = @Content(schema = @Schema(example = "Password updated successfully for email: user@example.com"))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(example = "{\"error\": \"Invalid email or password\"}"))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(example = "{\"error\": \"User not found\"}")))
    })
    @PostMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestBody Map<String, String> request) {
        String email = request.get("username");
        String newPassword = request.get("password");
        log.info("Received request to update password for email: {}", email);
        userService.setPassword(email, newPassword);
        return ResponseEntity.ok("Password updated successfully for email: " + email);
    }
}
