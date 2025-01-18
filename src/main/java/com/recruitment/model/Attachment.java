package com.recruitment.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Attachment name is required")
    @Column(nullable = false, unique = true)
    private String departmentName; // Department name must be unique

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for department lead
    @JoinColumn(name = "department_lead_id")
    private User departmentLead; // Reference to the department lead (User table)

    @Column(nullable = true)
    private String attachmentPath; // Store path for attachments

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading for parent department
    @JoinColumn(name = "parent_department_id")
    private Department parentDepartment; // Reference to the parent department

}
