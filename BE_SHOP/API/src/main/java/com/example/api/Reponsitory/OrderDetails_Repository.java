package com.example.api.Reponsitory;

import com.example.api.Entity.Order;
import com.example.api.Entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetails_Repository extends JpaRepository<OrderDetail,Integer> {
    @Query("select od from OrderDetail od where od.orderId=?1")
    List<OrderDetail> findByIdOrderDetails(int id);
    void deleteByOrderId(Order order);
    List<OrderDetail> findAllByOrderId(Order order);
}
