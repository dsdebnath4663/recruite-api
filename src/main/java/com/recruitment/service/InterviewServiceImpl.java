package com.recruitment.service;


import com.recruitment.dto.InterviewDTO;
import com.recruitment.mapper.InterviewMapper;
import com.recruitment.model.Interview;
import com.recruitment.model.User;
import com.recruitment.repository.CandidateRepository;
import com.recruitment.repository.InterviewRepository;
import com.recruitment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final InterviewMapper interviewMapper; // Added mapper for conversion
    private static final String INTERVIEW_NOT_FOUND = "Interview not found";  // Compliant

    public InterviewServiceImpl(
            InterviewRepository interviewRepository,
            UserRepository userRepository,
            CandidateRepository candidateRepository,
            InterviewMapper interviewMapper
    ) {
        this.interviewRepository = interviewRepository;
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
        this.interviewMapper = interviewMapper;
    }

    @Override
    public InterviewDTO createInterview(InterviewDTO interviewDTO) {
        // Convert DTO to Entity
        Interview interview = interviewMapper.toEntity(interviewDTO);

        // Set Interviewers
        List<User> interviewers = userRepository.findAllById(interviewDTO.getInterviewerIds());
        interview.setInterviewers(interviewers);

        // Set Candidate
        interview.setCandidate(candidateRepository.findById(interviewDTO.getCandidateId())
                                                  .orElseThrow(() -> new IllegalArgumentException("Candidate not found")));

        // Set Interview Owner
        interview.setInterviewOwner(userRepository.findById(interviewDTO.getInterviewOwnerId())
                                                  .orElseThrow(() -> new IllegalArgumentException("Interview owner not found")));

        // Save Interview and Convert to DTO
        Interview savedInterview = interviewRepository.save(interview);
        return interviewMapper.toDTO(savedInterview);
    }

    @Override
    public List<InterviewDTO> getAllInterviews() {
        // Fetch all interviews and convert to DTOs
        return interviewRepository.findAll()
                                  .stream()
                                  .map(interviewMapper::toDTO)
                                  .toList(); // Use Stream.toList() directly
    }

    @Override
    public InterviewDTO getInterviewById(Long id) {
        // Fetch Interview by ID and convert to DTO
        Interview interview = interviewRepository.findById(id)
                                                 .orElseThrow(() -> new IllegalArgumentException(INTERVIEW_NOT_FOUND));
        return interviewMapper.toDTO(interview);
    }

    @Override
    public InterviewDTO updateInterview(Long id, InterviewDTO interviewDTO) {
        // Fetch the existing Interview
        Interview interview = interviewRepository.findById(id)
                                                 .orElseThrow(() -> new IllegalArgumentException(INTERVIEW_NOT_FOUND));

        // Update fields using DTO
        interview.setInterviewName(interviewDTO.getInterviewName());
        interview.setDepartmentName(interviewDTO.getDepartmentName());
        interview.setFromDateTime(interviewDTO.getFromDateTime());
        interview.setToDateTime(interviewDTO.getToDateTime());
        interview.setLocation(interviewDTO.getLocation());
        interview.setPostingTitle(interviewDTO.getPostingTitle());
        interview.setScheduleComments(interviewDTO.getScheduleComments());
        interview.setAssessmentName(interviewDTO.getAssessmentName());
        interview.setReminder(interviewDTO.getReminder());

        // Update Interviewers
        List<User> interviewers = userRepository.findAllById(interviewDTO.getInterviewerIds());
        interview.setInterviewers(interviewers);

        // Update Interview Owner
        interview.setInterviewOwner(userRepository.findById(interviewDTO.getInterviewOwnerId())
                                                  .orElseThrow(() -> new IllegalArgumentException("Interview owner not found")));

        // Save updated Interview and Convert to DTO
        Interview updatedInterview = interviewRepository.save(interview);
        return interviewMapper.toDTO(updatedInterview);
    }

    @Override
    public void deleteInterview(Long id) {
        // Fetch and delete Interview by ID
        Interview interview = interviewRepository.findById(id)
                                                 .orElseThrow(() -> new IllegalArgumentException(INTERVIEW_NOT_FOUND));
        interviewRepository.delete(interview);
    }
}
