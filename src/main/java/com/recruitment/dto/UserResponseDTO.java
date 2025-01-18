package com.recruitment.dto;


import com.recruitment.model.UserProfile;
import com.recruitment.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String territory;
    private String role;
    private String profile;
    private boolean enabled;
    private String token; // JWT token
}
