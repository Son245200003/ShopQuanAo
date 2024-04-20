package com.example.api.Service;

import com.example.api.Entity.Order;
import com.example.api.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface Order_Serivce {
    List<Order> findAll();
    List<Order> findByUserId(User user);
    Order findByIdAndUserId(int id,User user);
    Order addOrder(Order order, User user);
    Order updateOrder(int id);
    void removeOrder(int id);
    Page<Order> findallPage(Pageable pageable);
    public List<Order> findAllByDay(LocalDateTime begin, LocalDateTime end);
    List<Order> search(String keyword);
    //
}
