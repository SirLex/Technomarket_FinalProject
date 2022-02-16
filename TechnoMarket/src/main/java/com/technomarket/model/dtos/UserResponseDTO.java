package com.technomarket.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    @NotBlank
    private int id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String address;
    @NotBlank
    private String phone;
    @NotBlank
    private LocalDate dateOfBirth;
    @NotBlank
    private boolean isAdmin;
    @NotBlank
    private boolean isSubscribed;
    @NotBlank
    private boolean isMale;
}
