package com.example.api.Reponsitory;

import com.example.api.Entity.Order;
import com.example.api.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface Order_Repository extends JpaRepository<Order,Integer> {
    @Query("select o from Order o where o.orderId=?1 and o.userId=?2")
    Order findByOrderIdAndUserId(int id,User user);

    @Query("select o from Order o where o.userId=?1")
    List<Order> findAllByUserId(User user);

    Order findByOrderId(int id);
    void deleteByOrderIdAndUserId(int id,User user);
    @Transactional
    @Modifying
    @Query("delete from Order o where o.orderId=?1 ")
    void deleteByOrderId(int id);
    @Query("SELECT o from Order o")
    Page<Order> findAllByPage(Pageable pageable);

    List<Order> findByOrderDateBetween(LocalDateTime begin, LocalDateTime end);
    @Query("select c from Order c where c.userId.username like %?1%")
    List<Order> findAllByUserIdContaining(String keyword);

}
