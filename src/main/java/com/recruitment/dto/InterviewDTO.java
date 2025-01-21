package com.recruitment.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InterviewDTO {
    private Long id; // Add this field for responses and updates

    private String interviewName;
    private String departmentName;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
    private List<Long> interviewerIds; // List of User IDs for interviewers
    private String location;
    private Long candidateId; // ID of the Candidate
    private String postingTitle;
    private Long interviewOwnerId; // ID of the Interview Owner
    private String scheduleComments;
    private String assessmentName;
    private String reminder;
    private List<AttachmentDTO> attachments; // List of attachments


    private UserDTO candidateOwner; // Use UserDTO instead of the full User entity
    private UserDTO interviewOwner; // Use UserDTO
    private List<UserDTO> interviewers; // Use List of UserDTOs


}
