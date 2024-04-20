package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.*;
import com.example.api.Reponsitory.*;
import com.example.api.Service.Cart_Service;
import com.example.api.Service.Order_Serivce;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderService_Impl implements Order_Serivce {
    @Autowired
    Order_Repository orderRepository;
    @Autowired
    CartItem_Repository cartItemRepository;
    @Autowired
    Cart_Service cartService;
    @Autowired
    User_Reponsitory userReponsitory;
    @Autowired
    OrderDetails_Repository orderDetailsRepository;
    @Autowired
    Cart_Repository cartRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findByUserId(User user) {
        return orderRepository.findAllByUserId(user);
    }

    @Override
    public Order findByIdAndUserId(int id,User user) {
        return orderRepository.findByOrderIdAndUserId(id,user);
    }

    @Override
//    @Transactional
    public Order addOrder(Order order, User user) {
//        lấy thông tin user
        User user1=userReponsitory.findById(user.getId());
        System.out.println(user1);
        order.setUserId(user1);
        Date date=new Date();
        double total=cartService.totalMoney(user);
        order.setTotalMoney(total);
        order.setOrderDate(date);
        order.setStatus("Chưa xác nhận");
        order.setNote(order.getNote());
        Cart cart=cartRepository.findCartByIdUser(user1);

// Thêm chi tiết đơn hàng (order details)
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart);
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetails = new OrderDetail();
            orderDetails.setOrderId(order);
            orderDetails.setProductId(cartItem.getProductId());
            orderDetails.setQuantity(cartItem.getQuantity());
            orderDetails.setPrice(cartItem.getProductId().getPrice());
            orderDetails.setTotalMoney(cartItem.getProductId().getPrice() * cartItem.getQuantity());
            orderDetails.setSize(cartItem.getSize());
            orderDetailsRepository.save(orderDetails);
        }

        // Xoá các mục trong cart
        System.out.println(cart.getIdCart());
        cartItemRepository.deleteAllByCartId(cart);

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(int id
                             //User user
    ) {
//        User user1=userReponsitory.findById(user.getId());
//        Order orderExit=orderRepository.findByOrderIdAndUserId(id,user1);
        Order orderExit=orderRepository.findByOrderId(id);
        orderExit.setStatus("Đã xác nhận");
        return orderRepository.saveAndFlush(orderExit);
    }


    @Override
    @Transactional
    public void removeOrder(int id) {
//        List<OrderDetail> orderDetails = orderDetailsRepository.findAllByOrderId(order);
        Order order = orderRepository.findByOrderId(id);

        if (order != null) {
            // Xóa tất cả các chi tiết đơn hàng liên quan
            orderDetailsRepository.deleteByOrderId(order);
            // Xóa đơn hàng
            orderRepository.deleteById(id);
        }
    }

    @Override
    public Page<Order> findallPage(Pageable pageable) {
        return orderRepository.findAllByPage(pageable);
    }
    @Override
    public List<Order> findAllByDay(LocalDateTime begin, LocalDateTime end){
        return orderRepository.findByOrderDateBetween(begin,end);
    }

    @Override
    public List<Order> search(String keyword) {
        return orderRepository.findAllByUserIdContaining(keyword);
    }
}
