package com.technomarket.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserEditInformationDTO {

    @NotBlank(message = "Name is mandatory!")
    private String firstName;

    @NotBlank(message = "Name is mandatory!")
    private String lastName;


    @NotBlank(message = "Password is mandatory!")
    private String address;

    @NotBlank(message = "Password is mandatory!")
    private String phone;

    @NotBlank(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is mandatory")
    private boolean isMale;
}