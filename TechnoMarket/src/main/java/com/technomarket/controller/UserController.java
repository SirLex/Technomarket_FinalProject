package com.technomarket.controller;

import com.technomarket.model.dtos.UserLoginDTO;
import com.technomarket.model.dtos.UserRegisterDTO;
import com.technomarket.model.dtos.UserResponseDTO;
import com.technomarket.model.pojos.User;
import com.technomarket.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/user/registration")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO dto){
        User u = userService.registerUser(dto);
        UserResponseDTO returnDto = modelMapper.map(u,UserResponseDTO.class);
        return new ResponseEntity<>(returnDto,HttpStatus.ACCEPTED);
    }

    @PostMapping("/users/login")
    public  ResponseEntity<UserResponseDTO> login(@RequestBody UserLoginDTO dto, HttpSession session, HttpServletRequest request) {
        UserResponseDTO userResponseDTO = userService.login(dto.getEmail(),dto.getPassword());
        session.setAttribute(LOGGED, true);
        session.setAttribute("logged_from", request.getRemoteAddr());
        session.setAttribute(USER_ID, userResponseDTO.getId());
        return new ResponseEntity<>(userResponseDTO,HttpStatus.OK);
    }




}
