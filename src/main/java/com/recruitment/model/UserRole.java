package com.recruitment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString(exclude = "subordinates")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reports_to_id")
//    @JsonIgnore
    private UserRole reportsTo;

    private boolean shareDataWithPeers;

    @OneToMany(mappedBy = "reportsTo")
    @JsonIgnore
    private List<UserRole> subordinates; // Ignore subordinates for serialization

}
