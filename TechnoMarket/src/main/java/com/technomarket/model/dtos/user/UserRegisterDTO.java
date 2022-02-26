package com.technomarket.model.dtos.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Validated
public class UserRegisterDTO {

    @NotBlank(message = "Name is mandatory!")
    private String firstName;

    @NotBlank(message = "Name is mandatory!")
    private String lastName;

    @NotBlank(message = "Email is mandatory!")
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Wrong email pattern")
    private String email;

    @NotBlank(message = "Password is mandatory!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$", message = "Password doesnt match pattern")
    private String password;

    @NotBlank(message = "Confirming password is mandatory")
    private String confirmPassword;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;
    private boolean isSubscribed;
    private boolean isMale;
}
