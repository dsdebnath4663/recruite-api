package com.recruitment.repository;

import com.recruitment.model.UserProfile;
import com.recruitment.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<UserProfile, Long> {
//    Optional<UserRole> findByName ( String recruiter_admin );
    Optional<UserProfile> findByName ( String recruiter_admin );
}
