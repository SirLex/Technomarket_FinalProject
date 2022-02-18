package com.technomarket.model.dtos.order;
import com.sun.istack.NotNull;
import com.technomarket.model.dtos.product.ProductResponseDTO;
import com.technomarket.model.pojos.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @NotNull
    private int id;
    @NotNull
    private double totalPrice;
  //  private List<ProductResponseDTO> productList;
    @NotNull
    private LocalDate date;

    public OrderDTO(Order order){

        this.id= order.getId();
        this.totalPrice= order.getPrice();
        //this.productList=order.getProductList();
        this.date=order.getCreatedAt();



    }


}
