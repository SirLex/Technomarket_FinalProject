package com.technomarket.model.services;

import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.model.dtos.UserRegisterDTO;
import com.technomarket.model.dtos.UserResponseDTO;
import com.technomarket.model.pojos.User;
import com.technomarket.model.repositories.UserRepository;
import com.technomarket.exceptions.BadRequestException;
import com.technomarket.exceptions.EmptyFieldException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    public User registerUser(UserRegisterDTO dto) {
        String firstName = dto.getFirstName();
        String lastName = dto.getLastName();
        String email = dto.getEmail();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        String address = dto.getAddress();
        String phone = dto.getPhone();
        LocalDate dateOfBirth = dto.getDateOfBirth();
        boolean isAdmin = false;
        boolean isSubscribed = dto.isSubscribed();
        boolean isMale = dto.isMale();

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("User with that email already exists");
        }

        if (!password.equals(confirmPassword)) {
            throw new BadRequestException("Passwords does not match");
        }

        User u = new User();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(password));
        u.setAddress(address);
        u.setPhone(phone);
        u.setDateOfBirth(dateOfBirth);
        u.setAdmin(false);
        u.setSubscribed(isSubscribed);
        u.setMale(isMale);
        userRepository.save(u);
        return u;
    }

    public UserResponseDTO login(String email, String password) {
        User u = userRepository.findByEmailAndPassword(email, passwordEncoder.encode(password));
        if(u == null){
            throw new AuthorizationException("Wrong credentials");
        }
        return mapper.map(u,UserResponseDTO.class);

    }
}
