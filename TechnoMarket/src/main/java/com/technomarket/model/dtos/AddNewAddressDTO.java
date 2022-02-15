package com.technomarket.model.dtos;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class AddNewAddressDTO {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "^[0-9\\-\\+]{8,15}$", message = "Invalid PHONE NUMBER")
    private String phoneNumber;


    @NotNull
    private String city;

    @NotNull
    private String district;
    
    @NotBlank
    @Pattern(regexp = "^[0-9\\-\\+]{4,4}$", message = "Invalid ZIP CODE")
    private String zip;

    @NotNull
    private String street;

    @NotNull
    private String numberOfStreet;

    private String block;
    private String blockEntrance;
    private String floor;
    private String apartment;


}
