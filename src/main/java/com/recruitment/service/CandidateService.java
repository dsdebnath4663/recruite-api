package com.recruitment.service;

import com.recruitment.dto.CandidateDTO;
import com.recruitment.enums.Source;
import com.recruitment.model.Candidate;
import com.recruitment.model.User;
import com.recruitment.repository.CandidateRepository;
import com.recruitment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;

    public CandidateService(CandidateRepository candidateRepository, UserRepository userRepository) {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
    }
    public Candidate createCandidate( CandidateDTO candidateDTO) {
        Candidate candidate = new Candidate();
        candidate.setFirstName(candidateDTO.getFirstName());
        candidate.setLastName(candidateDTO.getLastName());
        candidate.setEmail(candidateDTO.getEmail());
        candidate.setSecondaryEmail(candidateDTO.getSecondaryEmail());
        candidate.setPhone(candidateDTO.getPhone());
        candidate.setMobile(candidateDTO.getMobile());
        candidate.setFax(candidateDTO.getFax());
        candidate.setWebsite(candidateDTO.getWebsite());
        candidate.setAddressInformation(candidateDTO.getAddressInformation());
        candidate.setExperienceInYears(candidateDTO.getExperienceInYears());
        candidate.setCurrentJobTitle(candidateDTO.getCurrentJobTitle());
        candidate.setExpectedSalary(candidateDTO.getExpectedSalary());
        candidate.setCurrentSalary(candidateDTO.getCurrentSalary());
        candidate.setCurrentEmployer(candidateDTO.getCurrentEmployer());
        candidate.setHighestQualificationHeld(candidateDTO.getHighestQualificationHeld());
        candidate.setAdditionalInfo(candidateDTO.getAdditionalInfo());
        candidate.setSkypeId(candidateDTO.getSkypeId());
        candidate.setSkillSet(candidateDTO.getSkillSet());
        candidate.setLinkedIn(candidateDTO.getLinkedIn());
        candidate.setTwitter(candidateDTO.getTwitter());
        candidate.setFacebook(candidateDTO.getFacebook());
        candidate.setCandidateStatus(candidateDTO.getCandidateStatus());

        // Map candidateOwner
        User candidateOwner = userRepository.findById(candidateDTO.getCandidateOwnerId())
                                            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + candidateDTO.getCandidateOwnerId()));
        candidate.setCandidateOwner(candidateOwner);
//        candidate.setCandidateOwner(candidateDTO.getCandidateOwner());

        // Convert source string to enum
        Source source = Source.fromValue(candidateDTO.getSource());
        candidate.setSource(source);
//        candidate.setSource(candidateDTO.getSource());
        candidate.setOptOut(candidateDTO.isOptOut());
        candidate.setEducationalDetails(candidateDTO.getEducationalDetails());
        candidate.setExperienceDetails(candidateDTO.getExperienceDetails());
        candidate.setAttachments(candidateDTO.getAttachments());
        return candidateRepository.save(candidate);
    }

    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).orElseThrow(() -> new RuntimeException("Candidate not found"));
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public List<Candidate> getCandidatesBySource(Source source) {
        return candidateRepository.findBySource(source);
    }

    public Candidate getCandidateByEmail(String email) {
        return candidateRepository.findByEmail(email);
    }

    public List<Candidate> getCandidatesByName(String firstName, String lastName) {
        return candidateRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
    }

}
