package com.technomarket.model.dtos.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDTO {

    @NotBlank(message = "Name is mandatory!")
    private String firstName;

    @NotBlank(message = "Name is mandatory!")
    private String lastName;

    @NotBlank(message = "Email is mandatory!")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank(message = "Password is mandatory!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+$).{8,20}$")
    private String password;

    @NotBlank(message = "Confirming password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+$).{8,20}$")
    private String confirmPassword;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @NotBlank(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;
    private boolean isSubscribed;
    private boolean isMale;
}
