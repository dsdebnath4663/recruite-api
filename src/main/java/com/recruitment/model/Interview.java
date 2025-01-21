package com.recruitment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String interviewName;
    private String departmentName;

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    @ManyToMany
    @JoinTable(
            name = "interview_interviewers",
            joinColumns = @JoinColumn(name = "interview_id"),
            inverseJoinColumns = @JoinColumn(name = "interviewer_id")
    )
    private List<User> interviewers;

    private String location;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    private String postingTitle;

    @ManyToOne
    @JoinColumn(name = "interview_owner_id")
    private User interviewOwner;

    private String scheduleComments;
    private String assessmentName;
    private String reminder;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;
}
