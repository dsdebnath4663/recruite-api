package com.recruitment.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recruitment.dto.Permissions;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ensuring that the permissions field is properly initialized when creating or updating profiles.
 * Suggested Changes:
 * <p>
 * Provide methods to set and retrieve permissions conveniently. (using @Data here)
 * Add a default constructor to initialize permissions.(using NoArgsConstructor here)
 */
@Entity
@Data
@NoArgsConstructor// a default constructor to initialize permissions.
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Column(columnDefinition = "TEXT")
    private String permissionsJson; // Store JSON string in the database

    @Transient
    private Permissions permissions = new Permissions(); // Initialize permissions by default
    // Runtime object representation of permissions

    // Automatically convert Permissions object to JSON before persisting
    @PrePersist
    @PreUpdate
    public void prePersist () {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.permissionsJson = objectMapper.writeValueAsString(this.permissions);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error serializing permissions to JSON" , e);
        }
    }

    // Automatically convert JSON to Permissions object after fetching from DB
    @PostLoad
    public void postLoad () {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.permissions = objectMapper.readValue(this.permissionsJson , Permissions.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deserializing JSON to permissions" , e);
        }
    }
    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public Permissions getPermissions() {
        return permissions;
    }
}
