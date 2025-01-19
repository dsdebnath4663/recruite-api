package com.recruitment.repository;

import com.recruitment.model.Candidate;
import com.recruitment.enums.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    // Find candidates by source
    List<Candidate> findBySource(Source source);

    // Search for candidates by first name (case-insensitive)
    List<Candidate> findByFirstNameIgnoreCase(String firstName);

    // Search for candidates by last name (case-insensitive)
    List<Candidate> findByLastNameIgnoreCase(String lastName);

    // Search for candidates by email
    Candidate findByEmail(String email);

    // Search for candidates by phone
    Candidate findByPhone(String phone);

    // Find candidates by full name
    List<Candidate> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
