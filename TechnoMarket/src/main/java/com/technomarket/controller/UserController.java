package com.technomarket.controller;

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

@RestController
public class UserController {

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


}
