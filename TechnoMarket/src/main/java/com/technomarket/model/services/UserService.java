package com.technomarket.model.services;

import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.exceptions.NotFoundException;
import com.technomarket.model.dtos.UserChangePasswordDTO;
import com.technomarket.model.dtos.UserEditInformationDTO;
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

import javax.transaction.Transactional;
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
        if (!userRepository.existsByEmail(email)) {
            throw new BadRequestException("No account with that email");
        }
        User u = userRepository.findByEmail(email);
        if (!passwordEncoder.matches(password, u.getPassword())) {
            throw new BadRequestException("Wrong credentials");
        }
        return mapper.map(u, UserResponseDTO.class);

    }

    public UserResponseDTO getById(int id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with this id not found");
        }
        User u = userRepository.getById(id);
        return mapper.map(u, UserResponseDTO.class);

    }

    @Transactional
    public UserResponseDTO edit(int userID, UserEditInformationDTO dto) {
        if (!userRepository.existsById(userID)) {
            throw new NotFoundException("User with this id doesnt exist");
        }
        User user = userRepository.findById(userID).orElseThrow(() -> {
            throw new NotFoundException("User not found");
        });
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAddress(dto.getAddress());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setMale(dto.isMale());
        userRepository.save(user);
        return mapper.map(user, UserResponseDTO.class);
    }

    @Transactional
    public UserResponseDTO changePassword(int userID, UserChangePasswordDTO dto) {
        if (!userRepository.existsById(userID)) {
            throw new NotFoundException("User with this id doesnt exist");
        }
        User user = userRepository.findById(userID).orElseThrow(() -> {
            throw new NotFoundException("User not found");
        });

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("New passwords miss match");
        }

        String encodedPassword = user.getPassword();
        if (!passwordEncoder.matches(dto.getPassword(), encodedPassword)) {
            throw new AuthorizationException("Wrong old password");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        return mapper.map(user, UserResponseDTO.class);
    }
}
