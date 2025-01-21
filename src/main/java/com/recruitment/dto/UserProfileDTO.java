package com.recruitment.dto;

import lombok.Data;

@Data
public class UserProfileDTO {
    private Long id;
    private String name;
    private String description;

    // Exclude permissionsJson and other unnecessary fields
}
