package com.technomarket.model.services;


import com.technomarket.exceptions.AuthorizationException;
import com.technomarket.exceptions.BadRequestException;
import com.technomarket.model.compositekeys.OrderProductKey;
import com.technomarket.model.dtos.MessageDTO;
import com.technomarket.model.dtos.order.OrderCreateDTO;
import com.technomarket.model.dtos.order.OrderResponseDTO;
import com.technomarket.model.dtos.order.ProductWithQuantityDTO;
import com.technomarket.model.dtos.product.ProductFullWithQuantityDTO;
import com.technomarket.model.dtos.product.ProductResponseDTO;
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

import javax.servlet.http.HttpSession;
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

    public OrderResponseDTO getById(int id, int userId) {
        if (!orderRepository.existsById(id)) {
            throw new BadRequestException("Order with this id doesn't exist");
        }
        Order order = orderRepository.getById(id);
        User user = userRepository.getById(userId);
        if (userId != order.getUser().getId() && !user.isAdmin()) {
            throw new AuthorizationException("You dont have the rights to view this");
        }
        return new OrderResponseDTO(orderRepository.getById(id));
    }


    @Transactional
    public MessageDTO createOrder(HttpSession session, int userId) {
        if (!userRepository.existsById(userId)) {
            throw new BadRequestException("Not valid user");
        }
        if (session.getAttribute("cart") == null && ((List<ProductWithQuantityDTO>) session.getAttribute("cart")).isEmpty()) {
            throw new BadRequestException("You cannot order empty cart");
        }
        List<ProductWithQuantityDTO> cart = (List<ProductWithQuantityDTO>) session.getAttribute("cart");
        Map<Product, Integer> products = getProductsWithQuantityForOrder(cart);
        cart.clear();
        session.setAttribute("cart",cart);

        if (products.isEmpty()) {
            throw new BadRequestException("Cannot order when you have a empty cart");
        }

        double totalPrice = getTotalPrice(products);


        User user = userRepository.getById(userId);
        System.out.println(user.getId());

        Order order = new Order();
        order.setUser(user);
        order.setPrice(totalPrice);
        order.setAddress(user.getAddress());
        order.setCreatedAt(LocalDate.now());

        orderRepository.save(order);

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            saveOrderProductRelation(order, entry);
        }
        orderRepository.save(order);
        user.getOrderList().add(order);
        userRepository.save(user);
        return new MessageDTO("Order created", LocalDateTime.now());
    }

    private void saveOrderProductRelation(Order order, Map.Entry<Product, Integer> entry) {
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

    private Map<Product, Integer> getProductsWithQuantityForOrder(List<ProductWithQuantityDTO> productsWithQuantity) {
        Map<Product, Integer> products = new HashMap<>();
        for (ProductWithQuantityDTO miniDTO : productsWithQuantity) {
            int id = miniDTO.getProductId();
            int quantity = miniDTO.getQuantity();
            if (quantity < 1) {
                continue;
            }
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

    public List<ProductFullWithQuantityDTO> getProducts(int orderId, int userId) {

        if (!orderRepository.existsById(orderId)) {
            throw new BadRequestException("Order with this id doesn't exist");
        }
        Order order = orderRepository.getById(orderId);
        User user = userRepository.getById(userId);
        if (userId != order.getUser().getId() && !user.isAdmin()) {
            throw new AuthorizationException("You dont have the rights to view this");
        }

        List<OrderProduct> productsWithQuantity = orderProductRepository.findAllByOrder_Id(orderId);

        List<ProductFullWithQuantityDTO> responseDTOS = new ArrayList<>();

        for (OrderProduct orderProduct : productsWithQuantity) {
            ProductFullWithQuantityDTO toAdd = new ProductFullWithQuantityDTO();
            toAdd.setProductInfo(new ProductResponseDTO(orderProduct.getProduct()));
            toAdd.setQuantity(orderProduct.getQuantity());
            responseDTOS.add(toAdd);
        }

        return responseDTOS;
    }
}
