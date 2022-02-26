package com.technomarket.model.dtos.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserEditInformationDTO {

    @NotBlank(message = "Name is mandatory!")
    @Length(min = 2,max = 50,message = "Name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Name is mandatory!")
    @Length(min = 2,max = 50,message = "Name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Address is mandatory!")
    @Length(min = 2,max = 50,message = "Address must be between 2 and 200 characters")
    private String address;

    @NotBlank(message = "Password is mandatory!")
    @Pattern(regexp = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$", message = "Invalid phone")
    private String phone;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;

    @NotNull(message = "Subscription is mandatory")
    private boolean isSubscribed;

    @NotNull(message = "Gender is mandatory")
    private boolean isMale;
}