package com.recruitment.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Embeddable
public class DescriptionInformation {

    @NotBlank(message = "Job description is required")
    @Lob
    private String jobDescription;

    @NotBlank(message = "Requirements are required")
    @Lob
    private String requirements;

    @NotBlank(message = "Benefits are required")
    @Lob
    private String benefits;

}
