package com.technomarket.model.services;

import com.technomarket.model.dtos.UserRegisterDTO;
import com.technomarket.model.pojos.User;
import com.technomarket.model.repositories.UserRepository;
import com.technomarket.exceptions.BadRequestException;
import com.technomarket.exceptions.EmptyFieldException;
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

        //Verifying if any of the fields are blank
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new EmptyFieldException("Both names are mandatory for registration");
        }
        if (email == null || email.isBlank()) {
            throw new EmptyFieldException("Password is mandatory for registration");
        }
        if (password == null || password.isBlank() || confirmPassword == null || confirmPassword.isBlank()) {
            throw new EmptyFieldException("Password is mandatory for registration");
        }
        if (address == null || email.isBlank()) {
            throw new EmptyFieldException("Address is mandatory for registration");
        }
        if (dateOfBirth == null) {
            throw new EmptyFieldException("Date of birth is mandatory for registration");
        }
        if (phone == null || phone.isBlank()) {
            throw new EmptyFieldException("Phone number is mandatory for registration");
        }

        //Validation for requirements of fields
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(email).matches()) {
            throw new BadRequestException("Email is not valid");
        }

        if (userRepository.findByEmail(email) != null) {
            throw new BadRequestException("User with that email already exists");
        }

        if (!password.equals(confirmPassword)) {
            throw new BadRequestException("Passwords does not match");
        }

        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+$).{8,20}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        if (!passwordPattern.matcher(passwordRegex).matches()) {
            throw new BadRequestException("Password does not match requirements");
        }

        User u = new User();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(password));
        u.setAddress(address);
        u.setDateOfBirth(dateOfBirth);
        u.setAdmin(false);
        u.setSubscribed(isSubscribed);
        u.setMale(isMale);
        userRepository.save(u);
        return u;
    }
}
