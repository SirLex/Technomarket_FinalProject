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
    public  ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserLoginDTO dto, HttpSession session, HttpServletRequest request) {
        UserResponseDTO userResponseDTO = userService.login(dto.getEmail(),dto.getPassword());
        session.setAttribute(LOGGED, true);
        session.setAttribute("logged_from", request.getRemoteAddr());
        session.setAttribute(USER_ID, userResponseDTO.getId());
        return new ResponseEntity<>(userResponseDTO,HttpStatus.OK);
    }

    @PostMapping("/user/registration")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterDTO dto){
        User u = userService.registerUser(dto);
        UserResponseDTO returnDto = modelMapper.map(u,UserResponseDTO.class);
        return new ResponseEntity<>(returnDto,HttpStatus.ACCEPTED);
    }

    @PostMapping("/user/logout")
    public ResponseEntity<MessageDTO> logout(HttpSession session, HttpServletRequest request){
        session.invalidate();
        return new ResponseEntity<>(new MessageDTO("You have been loged out.", LocalDateTime.now()),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user")
    public MessageDTO delete(@Valid @RequestBody PasswordRequestDTO dto , HttpSession session){
        int userId = (int)session.getAttribute(USER_ID);
        MessageDTO responseDTO = userService.deleteUser(userId,dto);
        session.invalidate();
        return responseDTO;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id){
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    @PutMapping("/user/info")
    public ResponseEntity<UserResponseDTO> editUserInformation(@Valid @RequestBody UserEditInformationDTO dto, HttpSession session, HttpServletRequest request){
        validateLogin(session,request);
        int userID = (int)session.getAttribute(USER_ID);
        UserResponseDTO responseDTO = userService.edit(userID,dto);
        return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
    }

    @PutMapping("/user/info/changepassword")
    public ResponseEntity<UserResponseDTO> changePassword(@Valid @RequestBody UserChangePasswordDTO dto, HttpSession session, HttpServletRequest request){
        validateLogin(session,request);
        int userID = (int)session.getAttribute(USER_ID);
        UserResponseDTO responseDTO = userService.changePassword(userID,dto);
        return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
    }

    private void validateLogin(HttpSession session, HttpServletRequest request) {
        if(session.isNew() || (session.getAttribute(LOGGED)==null)||
                (!(Boolean)session.getAttribute(LOGGED)) ||
                (!request.getRemoteAddr().equals(session.getAttribute(LOGGED_FROM)))){
            throw new AuthorizationException("You have to login!");
        }
    }


}
