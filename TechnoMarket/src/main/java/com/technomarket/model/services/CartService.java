package com.technomarket.model.services;

import com.technomarket.exceptions.BadRequestException;
import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.cart.CartResponseDTO;
import com.technomarket.model.dtos.order.ProductWithQuantityDTO;
import com.technomarket.model.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    public CartResponseDTO addProductToCart(ProductWithQuantityDTO dto, HttpSession session) {
        if (!productRepository.existsById(dto.getProductId())) {
            throw new BadRequestException("Product doesnt exist");
        }


        List<ProductWithQuantityDTO> cart;
        if (session.getAttribute("cart") == null) {
            cart = new ArrayList<>();
            cart.add(dto);
            session.setAttribute("cart", cart);
            return new CartResponseDTO(cart);
        }

        cart = (List<ProductWithQuantityDTO>) session.getAttribute("cart");
        for (ProductWithQuantityDTO it : cart) {
            if (it.getProductId() == dto.getProductId()) {
                it.setQuantity(it.getQuantity() + dto.getQuantity());
                session.setAttribute("cart", cart);
                return new CartResponseDTO(cart);
            }
        }

        cart.add(dto);
        session.setAttribute("cart", cart);
        return new CartResponseDTO(cart);
    }

    public MessageDTO removeProductFromCart(int productId, HttpSession session) {
        if (productId <= 0) {
            throw new BadRequestException("Invalid product id");
        }

        if (session.getAttribute("cart") == null) {
            return new MessageDTO("Cart is empty already", LocalDateTime.now());
        }

        List<ProductWithQuantityDTO> cart = (List<ProductWithQuantityDTO>) session.getAttribute("cart");
        for (ProductWithQuantityDTO it : cart) {
            if (it.getProductId() == productId) {
                cart.remove(it);
                session.setAttribute("cart", cart);
                return new MessageDTO("Removed product from cart", LocalDateTime.now());
            }
        }
        return new MessageDTO("No such product in cart", LocalDateTime.now());
    }


    public CartResponseDTO showCart(HttpSession session) {
        if (session.getAttribute("cart") == null) {
            List<ProductWithQuantityDTO> cart = new ArrayList<>();
            session.setAttribute("cart", cart);
            return mapper.map(cart, CartResponseDTO.class);
        }

        List<ProductWithQuantityDTO> cart = (List<ProductWithQuantityDTO>) session.getAttribute("cart");
        return new CartResponseDTO(cart);
    }
}
