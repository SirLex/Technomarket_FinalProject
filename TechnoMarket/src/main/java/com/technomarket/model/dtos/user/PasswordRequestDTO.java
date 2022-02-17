package com.technomarket.model.dtos.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class PasswordRequestDTO {


    @NotBlank(message = "Password is mandatory!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+$).{8,20}$")
    private String password;

    @NotBlank(message = "Confirm password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+$).{8,20}$")
    private String confirmPassword;
}