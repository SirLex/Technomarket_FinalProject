package com.technomarket.model.services;


import com.technomarket.exceptions.BadRequestException;
import com.technomarket.model.compositekeys.OrderProductKey;
import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.order.OrderCreateDTO;
import com.technomarket.model.dtos.order.OrderDTO;
import com.technomarket.model.dtos.order.ProductWithQuantityDTO;
import com.technomarket.model.pojos.Order;
import com.technomarket.model.pojos.Product;
import com.technomarket.model.pojos.User;
import com.technomarket.model.relationentity.OrderProduct;
import com.technomarket.model.repositories.OrderProductRepository;
import com.technomarket.model.repositories.OrderRepository;
import com.technomarket.model.repositories.ProductRepository;
import com.technomarket.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public Order getById(int id) {
        if (!orderRepository.existsById(id)) {
            throw new BadRequestException("Order with this id doesn't exist");
        }
        return orderRepository.getById(id);
    }


    @Transactional
    public MessageDTO createOrder(OrderCreateDTO dto, int userId) {
        if (!userRepository.existsById(userId)) {
            throw new BadRequestException("Not valid user");
        }
        Map<Product, Integer> products = getProductIntegerMap(dto);

        if (products.isEmpty()) {
            throw new BadRequestException("Cannot order when you have a empty cart");
        }

        double totalPrice = getTotalPrice(products);


        User user = userRepository.getById(userId);

        Order order = new Order();
        order.setUser(user);
        order.setPrice(totalPrice);
        order.setAddress(user.getAddress());
        order.setCreatedAt(LocalDate.now());

        orderRepository.save(order);

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            OrderProductKey key = new OrderProductKey();
            key.setOrderId(order.getId());
            key.setProductId(product.getId());

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setId(key);
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(quantity);

            order.getOrderProducts().add(orderProduct);
            product.getOrderProducts().add(orderProduct);

            orderProductRepository.save(orderProduct);
        }
        orderRepository.save(order);
        user.getOrderList().add(order);
        userRepository.save(user);
        return new MessageDTO("Order created", LocalDateTime.now());
    }

    private double getTotalPrice(Map<Product, Integer> products) {
        double totalPrice = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            double price = product.getPrice();
            if (product.getDiscount() != null) {
                price = price - (price * (product.getDiscount().getDiscountPercentage() / 100.0));
            }
            totalPrice += price * quantity;
        }
        return totalPrice;
    }

    private Map<Product, Integer> getProductIntegerMap(OrderCreateDTO dto) {
        Map<Product, Integer> products = new HashMap<>();
        for (ProductWithQuantityDTO miniDTO : dto.getListOfProductsIdAndQuantity()) {
            int id = miniDTO.getProductId();
            int quantity = miniDTO.getQuantity();
            if (productRepository.existsById(id)) {
                Product product = productRepository.getById(id);
                if (!products.containsKey(product)) {
                    products.put(product, 0);
                }
                products.put(product, products.get(product) + quantity);
            }
        }
        return products;
    }

}
