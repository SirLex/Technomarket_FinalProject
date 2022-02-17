package com.technomarket.controller;

import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.model.dtos.*;
import com.technomarket.model.dtos.user.*;
import com.technomarket.model.pojos.User;
import com.technomarket.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@Validated
public class UserController {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/user/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserLoginDTO dto, HttpServletRequest request) {
        UserResponseDTO userResponseDTO = userService.login(dto.getEmail(), dto.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(LOGGED, true);
        session.setAttribute("logged_from", request.getRemoteAddr());
        session.setAttribute(USER_ID, userResponseDTO.getId());
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/user/registration")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterDTO dto) {
        UserResponseDTO returnDto = userService.registerUser(dto);
        return new ResponseEntity<>(returnDto, HttpStatus.ACCEPTED);
    }

    @PostMapping("/user/logout")
    public ResponseEntity<MessageDTO> logout(HttpServletRequest request) {
        validateLogin(request);
        HttpSession session = request.getSession();
        session.invalidate();
        return new ResponseEntity<>(new MessageDTO("You have been loged out.", LocalDateTime.now()), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user")
    public MessageDTO delete(@Valid @RequestBody PasswordRequestDTO dto, HttpServletRequest request) {
        validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(USER_ID);
        MessageDTO responseDTO = userService.deleteUser(userId, dto);
        session.invalidate();
        return responseDTO;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/user/info")
    public ResponseEntity<UserResponseDTO> editUserInformation(@Valid @RequestBody UserEditInformationDTO dto, HttpServletRequest request) {
        validateLogin(request);
        HttpSession session = request.getSession();
        int userID = (int) session.getAttribute(USER_ID);
        UserResponseDTO responseDTO = userService.edit(userID, dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/user/info/changepassword")
    public ResponseEntity<UserResponseDTO> changePassword(@Valid @RequestBody UserChangePasswordDTO dto, HttpServletRequest request) {
        validateLogin(request);
        HttpSession session = request.getSession();
        int userID = (int) session.getAttribute(USER_ID);
        UserResponseDTO responseDTO = userService.changePassword(userID, dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    public static void validateLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean notLogged = (session.getAttribute(LOGGED) == null) || (!(Boolean) session.getAttribute(LOGGED));
        boolean notSameAddress = (session.getAttribute(LOGGED_FROM) == null) || request.getRemoteAddr().equals(session.getAttribute(LOGGED_FROM));
        if (session.isNew() || notLogged || notSameAddress) {
            throw new AuthorizationException("You have to login!");
        }
    }


}
