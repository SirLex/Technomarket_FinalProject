package com.technomarket.model.dtos.user;

import com.technomarket.model.pojos.User;
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

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.dateOfBirth = user.getDateOfBirth();
        this.isAdmin = user.isAdmin();
        this.isSubscribed = user.isSubscribed();
        this.isMale = user.isMale();
    }
}
