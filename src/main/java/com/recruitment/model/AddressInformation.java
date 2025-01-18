package com.recruitment.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Embeddable
public class AddressInformation {

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
