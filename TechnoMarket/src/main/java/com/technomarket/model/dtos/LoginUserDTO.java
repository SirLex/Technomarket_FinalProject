package com.technomarket.model.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class LoginUserDTO {

    @NotBlank(message = "Invalid email.")
    private String email;

    @NotBlank(message = "Invalid password.")
    private String password;

}
