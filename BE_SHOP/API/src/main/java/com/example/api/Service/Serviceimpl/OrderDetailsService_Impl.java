package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.Order;
import com.example.api.Entity.OrderDetail;
import com.example.api.Reponsitory.OrderDetails_Repository;
import com.example.api.Service.OrderDetails_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService_Impl implements OrderDetails_Service {
    final
    OrderDetails_Repository orderDetailsRepository;
    @Autowired

    public OrderDetailsService_Impl(OrderDetails_Repository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public List<OrderDetail> findById(Order order) {
        return orderDetailsRepository.findAllByOrderId(order);
    }
}
