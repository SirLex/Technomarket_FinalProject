package com.technomarket.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String address;
    private String phone;
    private LocalDate dateOfBirth;
    private boolean isSubscribed;
    private boolean isMale;
}
