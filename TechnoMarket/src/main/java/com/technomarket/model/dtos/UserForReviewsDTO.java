package com.technomarket.model.dtos;


import com.technomarket.model.pojos.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserForReviewsDTO {
    private long id;

    private String firstName;

    private String lastName;

    public UserForReviewsDTO(User user) {
        setId(user.getId());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        
    }

}
