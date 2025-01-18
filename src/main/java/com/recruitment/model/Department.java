package com.recruitment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Department name is required") // Ensure department name is validated
    @Column(nullable = false, unique = true)
    private String departmentName; // Department name must be unique

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for parent department
    @JoinColumn(name = "parent_department_id")
//    @JsonIgnore // Ignore during serialization to prevent proxy issues
    private Department parentDepartment; // Reference to the parent department

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for department lead
    @JoinColumn(name = "department_lead_id")
//    @JsonIgnore // Ignore during serialization to prevent proxy issues
    private User departmentLead; // Reference to the department lead (User table)

    @Column(nullable = true)
    private String attachmentPath; // Store path for attachments
}
