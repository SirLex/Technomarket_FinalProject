package com.technomarket.model.dtos.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Validated
public class UserRegisterDTO {

    @NotBlank(message = "Name is mandatory!")
    @Length(min = 2,max = 50,message = "Name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Name is mandatory!")
    @Length(min = 2,max = 50,message = "Name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is mandatory!")
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Wrong email pattern")
    private String email;

    @NotBlank(message = "Password is mandatory!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,50}$", message = "Password doesnt match pattern")
    private String password;

    @NotBlank(message = "Confirming password is mandatory")
    private String confirmPassword;

    @NotBlank(message = "Address is mandatory")
    @Length(min = 2,max = 50,message = "Address must be between 2 and 200 characters")
    private String address;

    @NotBlank(message = "Phone is mandatory")
    @Pattern(regexp = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$", message = "Invalid phone")
    private String phone;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;

    @NotNull(message = "Subscription is mandatory")
    private boolean isSubscribed;

    @NotNull(message = "Gender is mandatory")
    private boolean isMale;

}
