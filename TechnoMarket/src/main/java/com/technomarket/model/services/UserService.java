package com.technomarket.model.services;

import com.technomarket.controller.UserController;
import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.exceptions.NotFoundException;
import com.technomarket.model.dtos.*;
import com.technomarket.model.dtos.order.OrderDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.dtos.user.*;
import com.technomarket.model.pojos.Order;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.pojos.User;
import com.technomarket.model.repositories.OrderRepository;
import com.technomarket.model.repositories.ProductRepository;
import com.technomarket.model.repositories.UserRepository;
import com.technomarket.exceptions.BadRequestException;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private ModelMapper mapper;

    public UserResponseDTO registerUser(UserRegisterDTO dto) {
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
        return mapper.map(u, UserResponseDTO.class);
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

    public MessageDTO deleteUser(int userId, PasswordRequestDTO dto) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with this id doesnt exist");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("User not found");
        });

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Passwords miss match");
        }

        userRepository.delete(user);
        return new MessageDTO("Delete successful", LocalDateTime.now());
    }

    public void adminValidation(int userId) {
        if (!checkAdminRights(userId)) {
            throw new AuthorizationException("You dont have the rights for this operation");
        }
    }

    private boolean checkAdminRights(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with this id doesnt exist");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("User not found");
        });
        return user.isAdmin();
    }

    public List<ProductResponseDTO> getFavouriteProducts(int userId) {
        if(!userRepository.existsById(userId)){
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


    public UserResponseDTO addFavourite(int productId, int userID) {
        if(!productRepository.existsById(productId)){
            throw new BadRequestException("Product with this id doesn't exist");
        }
        Product product = productRepository.getById(productId);
        User user = userRepository.getById(userID);
        user.getFavoriteProducts().add(product);
        userRepository.save(user);

        return mapper.map(user,UserResponseDTO.class);
    }

    public List<OrderDTO> getAllOrdersFromUser(int userId) {
        List<Order> orders = orderRepository.findAllByUser_Id(userId);
        if (orders.isEmpty()) {
            throw new NotFoundException("No orders found!");
        }
        List<OrderDTO> orderDtoList = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDto = new OrderDTO(order.getId(),order.getPrice(),order.getCreatedAt());
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

}
