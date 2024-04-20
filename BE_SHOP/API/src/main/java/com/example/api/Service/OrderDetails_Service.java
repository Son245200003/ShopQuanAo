package com.example.api.Service;

import com.example.api.Entity.Order;
import com.example.api.Entity.OrderDetail;

import java.util.List;

public interface OrderDetails_Service {
    public List<OrderDetail> findById(Order order);
}
