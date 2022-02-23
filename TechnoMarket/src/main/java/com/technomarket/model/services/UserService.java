package com.technomarket.model.services;

import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.exceptions.BadRequestException;
import com.technomarket.exceptions.NotFoundException;
import com.technomarket.exceptions.VerificationException;
import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.order.OrderResponseDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.dtos.user.*;
import com.technomarket.model.event.OnRegistrationCompleteEvent;
import com.technomarket.model.pojos.Order;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.pojos.User;
import com.technomarket.model.pojos.VerificationToken;
import com.technomarket.model.repositories.OrderRepository;
import com.technomarket.model.repositories.ProductRepository;
import com.technomarket.model.repositories.UserRepository;
import com.technomarket.model.repositories.VerificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private VerificationRepository verificationRepo;

    @Autowired
    private ModelMapper mapper;

    public UserResponseDTO registerUser(UserRegisterDTO dto, HttpServletRequest request) {
        User user = plainRegister(dto);

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
        return mapper.map(user, UserResponseDTO.class);
    }

    private User plainRegister(UserRegisterDTO dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("User with that email already exists");
        }

        if (!password.equals(confirmPassword)) {
            throw new BadRequestException("Passwords does not match");
        }

        User u = new User();
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(password));
        u.setAddress(dto.getAddress());
        u.setPhone(dto.getPhone());
        u.setDateOfBirth(dto.getDateOfBirth());
        u.setAdmin(false);
        u.setSubscribed(dto.isSubscribed());
        u.setMale(dto.isMale());
        u.setVerified(false);
        userRepository.save(u);
        return u;
    }

    public UserResponseDTO login(String email, String password, HttpServletRequest request) {
        if (!userRepository.existsByEmail(email)) {
            throw new BadRequestException("Wrong credentials");
        }
        User u = userRepository.findByEmail(email);
        if(u.isDeleted()){
            throw new BadRequestException("Wrong credentials");
        }
        if (!passwordEncoder.matches(password, u.getPassword())) {
            throw new BadRequestException("Wrong credentials");
        }
        if(!u.isVerified()){
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(u, request.getLocale(), request.getContextPath()));
            throw  new VerificationException("You have to verify your email");
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

    public MessageDTO deleteUser(int userId, PasswordRequestDTO dto) {

        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("User not found");
        });

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Passwords miss match");
        }
        user.setEmail(LocalDateTime.now().toString());
        userRepository.save(user);
        userRepository.delete(user);
        return new MessageDTO("Delete successful", LocalDateTime.now());
    }

    public void adminValidation(int userId) {
        if (!checkAdminRights(userId)) {
            throw new AuthorizationException("You dont have the rights for this operation");
        }
    }

    private boolean checkAdminRights(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("User not found");
        });
        return user.isAdmin();
    }

    public List<ProductResponseDTO> getFavouriteProducts(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new BadRequestException("No such user exists");
        }

        List<Product> products = productRepository.findUserFavouriteProducts(userId);
        if (products.isEmpty()) {
            throw new NotFoundException("No favourite products found!");
        }
        List<ProductResponseDTO> productDto = new ArrayList<>();
        for (Product product : products) {

            productDto.add(new ProductResponseDTO(product));
        }
        return productDto;
    }


    public UserResponseDTO addFavourite(int productId, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("User not found");
        });
        if (!productRepository.existsById(productId)) {
            throw new BadRequestException("Product with this id doesn't exist");
        }
        Product product = productRepository.getById(productId);
        user.getFavoriteProducts().add(product);
        userRepository.save(user);

        return mapper.map(user, UserResponseDTO.class);
    }

    public List<OrderResponseDTO> getAllOrdersFromUser(int userId) {
        List<Order> orders = orderRepository.findAllByUser_Id(userId);
        if (orders.isEmpty()) {
            throw new NotFoundException("No orders found!");
        }
        List<OrderResponseDTO> orderDtoList = new ArrayList<>();
        for (Order order : orders) {
            OrderResponseDTO orderDto = new OrderResponseDTO(order);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token,user);
        verificationRepo.save(verificationToken);
    }

    public void confirmRegistration(HttpServletRequest request, String token) {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = verificationRepo.getByToken(token);
        if (verificationToken == null) {
            throw new VerificationException("Token not found");
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpireDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new VerificationException("Token has expired");
        }

        user.setVerified(true);
        userRepository.save(user);
    }
}
