package com.technomarket.model.pojos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private LocalDate dateOfBirth;
    @Column
    private boolean isAdmin;
    @Column
    private boolean isSubscribed;
    @Column
    private boolean isMale;
}
