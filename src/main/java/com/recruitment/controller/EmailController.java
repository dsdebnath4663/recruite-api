package com.recruitment.controller;

import com.recruitment.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Email Management", description = "Endpoints for sending emails")
@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController ( EmailService emailService ) {
        this.emailService = emailService;
    }

    @Operation(
            summary = "Send an email",
            description = "Allows sending an email to a recipient with a subject and body.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Email details including recipient, subject, and body",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(example = """
                            {
                              "to": "recipient@example.com",
                              "subject": "Welcome",
                              "body": "Hello, welcome to our platform!"
                            }
                            """))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email sent successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) ,
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json")) ,
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail ( @RequestBody Map<String, String> request ) {
        try {
            String to = request.get("to");
            String userName = request.get("userName");
            String userId = request.get("userId");
            String password = request.get("password");
            String loginUrl = request.get("loginUrl");

            emailService.sendAccountCreatedEmail(to , userName , userId , password , loginUrl);
            return ResponseEntity.ok("Account creation email sent successfully to " + to);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }
}
