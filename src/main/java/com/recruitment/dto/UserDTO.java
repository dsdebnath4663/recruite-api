package com.recruitment.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private String phone;
    private String address;
    private String territory;

    // Exclude permissionsJson and other unnecessary fields
}
