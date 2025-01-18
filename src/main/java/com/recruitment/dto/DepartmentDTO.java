package com.recruitment.dto;


import com.recruitment.model.Department;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentDTO {
    /**
     * Summary of Options:
     *
     *     DTOs: Preferred for clean and flexible API responses.
     *     Force Initialization: Works but can lead to performance issues if overused.
     *     Jackson Hibernate Module: Simplifies handling of lazy-loaded fields.
     *     Ignore Lazy-Loaded Fields: Good for quick fixes but loses the field data in responses.
     *
     * For a scalable application, use DTOs to ensure clear separation of concerns and avoid exposing your internal entity structures directly.
     */
    private Long id;

    @NotBlank(message = "Department name is required")
    private String departmentName;

    private Long parentDepartmentId; // Parent department ID

    private Long departmentLeadId; // Department lead ID

    private String departmentLeadName;

    private String attachmentPath; // Path for attachments


}
