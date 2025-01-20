package com.recruitment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Embeddable
public class AddressInformation {

    @JsonProperty("isRemoteJob")
    private boolean isRemoteJob; // Checkbox to indicate if the job is remote

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Province is required")
    private String province;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Postal code is required")
    private String postalCode;


}
