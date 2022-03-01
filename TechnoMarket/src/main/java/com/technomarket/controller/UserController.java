package com.technomarket.controller;

import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.model.dtos.*;
import com.technomarket.model.dtos.order.OrderResponseDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.dtos.user.*;
import com.technomarket.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UserController {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";

    @Autowired
    private UserService userService;


    @PostMapping("/user/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserLoginDTO dto, HttpServletRequest request) {
        validateNotLoggedIn(request);
        UserResponseDTO userResponseDTO = userService.login(dto.getEmail(), dto.getPassword(), request);
        HttpSession session = request.getSession();
        session.setAttribute(LOGGED, true);
        session.setAttribute("logged_from", request.getRemoteAddr());
        session.setAttribute(USER_ID, userResponseDTO.getId());
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/user/registration")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterDTO dto, HttpServletRequest request) {
        validateNotLoggedIn(request);
        UserResponseDTO returnDto = userService.registerUser(dto, request);
        return new ResponseEntity<>(returnDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/registration/confirm/{token}")
    public ResponseEntity<MessageDTO> confirmRegistration(HttpServletRequest request, @PathVariable String token) {
        userService.confirmRegistration(request, token);
        return new ResponseEntity<>(new MessageDTO("Verified!", LocalDateTime.now()), HttpStatus.OK);
    }

    @PostMapping("/user/logout")
    public ResponseEntity<MessageDTO> logout(HttpServletRequest request) {
        validateLogin(request);
        HttpSession session = request.getSession();

        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>(new MessageDTO("You have been logged out.", LocalDateTime.now()), HttpStatus.ACCEPTED);
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
    public ResponseEntity<UserResponseDTO> getById(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        if (userId != id && !userService.adminValidation(userId)) {
            throw new AuthorizationException("You dont have the rights for this operation");
        }

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

    @PostMapping("/user/favourites/{id}")
    public ResponseEntity<MessageDTO> addFavouriteProduct(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        HttpSession session = request.getSession();
        int userID = (int) session.getAttribute(USER_ID);

        UserResponseDTO responseDTOS = userService.addFavourite(id, userID);
        return new ResponseEntity<>(new MessageDTO("Product was added to favourites",LocalDateTime.now()), HttpStatus.OK);
    }

    @DeleteMapping("/user/favourites/{id}")
    public ResponseEntity<MessageDTO> removeFavouriteProduct(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        HttpSession session = request.getSession();
        int userID = (int) session.getAttribute(USER_ID);

        UserResponseDTO responseDTOS = userService.removeFavourite(id, userID);
        return new ResponseEntity<>(new MessageDTO("Product was removed from favourites",LocalDateTime.now()), HttpStatus.OK);
    }


    @GetMapping("/user/favourites")
    public ResponseEntity<List<ProductResponseDTO>> getFavouriteProducts(HttpServletRequest request) {
        validateLogin(request);
        HttpSession session = request.getSession();
        int userID = (int) session.getAttribute(USER_ID);

        List<ProductResponseDTO> responseDTOS = userService.getFavouriteProducts(userID);
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }


    @GetMapping("/user/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrders(HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);

        List<OrderResponseDTO> orderDTOList = userService.getAllOrdersFromUser(userId);
        return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
    }

    public static void validateLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean notLogged = (session.getAttribute(LOGGED) == null) || (!(Boolean) session.getAttribute(LOGGED));
        boolean notSameAddress = (session.getAttribute(LOGGED_FROM) == null) || !request.getRemoteAddr().equals(session.getAttribute(LOGGED_FROM));
        if (session.isNew() || notLogged || notSameAddress) {
            throw new AuthorizationException("You have to login!");
        }
    }

    public static void validateNotLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean logged = (session.getAttribute(LOGGED) != null) && ((Boolean) session.getAttribute(LOGGED));
        if (logged) {
            throw new AuthorizationException("You have to logout!");
        }
    }

}
