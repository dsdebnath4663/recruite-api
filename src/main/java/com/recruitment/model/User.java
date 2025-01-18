package com.recruitment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor // Optional: specify table name explicitly
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true) // Ensure email is unique
    private String email; // Used for authentication

//    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; // Password should not be exposed in API responses
    private boolean enabled = true; // Indicates if the user account is active


    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private UserProfile profile;

    private String phone;
    private String address;
    private String territory;




}
