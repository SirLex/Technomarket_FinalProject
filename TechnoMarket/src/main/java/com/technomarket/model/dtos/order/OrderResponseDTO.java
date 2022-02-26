package com.technomarket.model.dtos.order;
import com.sun.istack.NotNull;
import com.technomarket.model.dtos.user.UserResponseDTO;
import com.technomarket.model.pojos.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    public OrderResponseDTO(Order order){

        this.id= order.getId();
        this.user = new UserResponseDTO(order.getUser());
        this.totalPrice= order.getPrice();
        this.createDate=order.getCreatedAt();



    }


}
