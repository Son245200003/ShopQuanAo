package com.example.api.RestController;

import com.example.api.Entity.Order;
import com.example.api.Entity.OrderDetail;
import com.example.api.Service.OrderDetails_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-orders-details")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class OrderDetailsController {
    private final OrderDetails_Service orderDetailsService;
    @GetMapping("/{idOrder}")
    public List<OrderDetail> findById(@PathVariable("idOrder") Order idOrder){
        return orderDetailsService.findById(idOrder);
    }
}
