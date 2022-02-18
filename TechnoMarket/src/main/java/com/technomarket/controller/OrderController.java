package com.technomarket.controller;
import com.technomarket.model.dtos.order.OrderDTO;
import com.technomarket.model.pojos.Order;
import com.technomarket.model.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/user/order")
    public ResponseEntity<List<OrderDTO>> getOrders(HttpServletRequest request) {
        UserController.validateLogin(request);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(UserController.USER_ID);
        List<OrderDTO> orderDTOList = orderService.getAllOrdersFromUser(userId);
        return new ResponseEntity<>(orderDTOList, HttpStatus.OK);

    }

    @GetMapping("/order/{id}")
    public Order getById(@PathVariable int id) {
        return orderService.getById(id);
    }

}
