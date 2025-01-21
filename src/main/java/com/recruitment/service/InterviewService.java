package com.recruitment.service;


import com.recruitment.dto.InterviewDTO;

import java.util.List;

public interface InterviewService {

    InterviewDTO createInterview(InterviewDTO interviewDTO);

    List<InterviewDTO> getAllInterviews();

    InterviewDTO getInterviewById(Long id);

    InterviewDTO updateInterview(Long id, InterviewDTO interviewDTO);

    void deleteInterview(Long id);
}
