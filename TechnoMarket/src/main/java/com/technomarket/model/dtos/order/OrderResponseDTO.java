package com.technomarket.model.dtos.order;

import com.sun.istack.NotNull;
import com.technomarket.model.dtos.user.UserResponseDTO;
import com.technomarket.model.pojos.Order;
import com.technomarket.model.relationentity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    @NotNull
    private int id;
    @NotNull
    private UserResponseDTO user;
    @NotNull
    private double totalPrice;
    @NotNull
    private LocalDate createDate;
    @NotNull
    private List<ProductsInOrderResponseDTO> products;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.user = new UserResponseDTO(order.getUser());
        this.totalPrice = order.getPrice();
        this.createDate = order.getCreatedAt();
        this.products = new ArrayList<>();

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            products.add(new ProductsInOrderResponseDTO(orderProduct.getProduct(), orderProduct.getQuantity()));
        }


    }


}
