package com.recruitment.repository;


import com.recruitment.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<UserGroup, Long> {
}
