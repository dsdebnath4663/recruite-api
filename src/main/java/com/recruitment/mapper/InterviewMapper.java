package com.recruitment.mapper;

import com.recruitment.dto.InterviewDTO;
import com.recruitment.dto.UserDTO;
import com.recruitment.model.Attachment;
import com.recruitment.model.Interview;
import com.recruitment.model.User;
import com.recruitment.repository.CandidateRepository;
import com.recruitment.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InterviewMapper {

    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;

    private  final AttachmentMapper attachmentMapper;

    public InterviewMapper( CandidateRepository candidateRepository, UserRepository userRepository , AttachmentMapper attachmentMapper ) {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.attachmentMapper = attachmentMapper;
    }

    public InterviewDTO toDTO(Interview interview) {
        InterviewDTO dto = new InterviewDTO();
        dto.setId(interview.getId());
        dto.setInterviewName(interview.getInterviewName());
        dto.setDepartmentName(interview.getDepartmentName());
        dto.setFromDateTime(interview.getFromDateTime());
        dto.setToDateTime(interview.getToDateTime());
        dto.setLocation(interview.getLocation());
        dto.setPostingTitle(interview.getPostingTitle());

        // Map Candidate
        if (interview.getCandidate() != null) {
            dto.setCandidateId(interview.getCandidate().getId());
        }

        // Map Interview Owner
        if (interview.getInterviewOwner() != null) {
            dto.setInterviewOwnerId(interview.getInterviewOwner().getId());
        }

        // Map Attachments
        if (interview.getAttachments() != null) {
            dto.setAttachments(interview.getAttachments().stream()
                                        .map(attachmentMapper::toDTO)
                                        .toList()); // Use Stream.toList() directly
        }

        dto.setInterviewOwner(toUserDTO(interview.getInterviewOwner()));
        dto.setCandidateOwner(toUserDTO(interview.getCandidate().getCandidateOwner()));
        dto.setInterviewers(interview.getInterviewers().stream()
                                     .map(this::toUserDTO)
                                     .toList());

        dto.setScheduleComments(interview.getScheduleComments());
        dto.setAssessmentName(interview.getAssessmentName());
        dto.setReminder(interview.getReminder());
        return dto;
    }

    public Interview toEntity(InterviewDTO dto) {
        Interview interview = new Interview();
        interview.setInterviewName(dto.getInterviewName());
        interview.setDepartmentName(dto.getDepartmentName());
        interview.setFromDateTime(dto.getFromDateTime());
        interview.setToDateTime(dto.getToDateTime());
        interview.setLocation(dto.getLocation());
        interview.setPostingTitle(dto.getPostingTitle());

        // Set Candidate
        interview.setCandidate(candidateRepository.findById(dto.getCandidateId())
                                                  .orElseThrow(() -> new IllegalArgumentException("Candidate not found")));

        // Set Interviewers
        List<User> interviewers = userRepository.findAllById(dto.getInterviewerIds());
        interview.setInterviewers(interviewers);

        // Set Interview Owner
        interview.setInterviewOwner(userRepository.findById(dto.getInterviewOwnerId())
                                                  .orElseThrow(() -> new IllegalArgumentException("Interview owner not found")));


        // Set Attachments
        if (dto.getAttachments() != null) {
            List<Attachment> attachments = dto.getAttachments().stream()
                                              .map(attachmentMapper::toEntity)
                                              .toList();
            interview.setAttachments(attachments);
        }

        interview.setScheduleComments(dto.getScheduleComments());
        interview.setAssessmentName(dto.getAssessmentName());
        interview.setReminder(dto.getReminder());
        return interview;
    }

    private UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setTerritory(user.getTerritory());
        return dto;
    }
}
