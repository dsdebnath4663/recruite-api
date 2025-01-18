package com.recruitment.dto;


import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
    private List<RoleDTO> subordinates; // Recursive relationship
}

