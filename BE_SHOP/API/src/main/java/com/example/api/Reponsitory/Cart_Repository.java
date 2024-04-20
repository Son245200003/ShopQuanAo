package com.example.api.Reponsitory;

import com.example.api.Entity.Cart;
import com.example.api.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Cart_Repository extends JpaRepository<Cart,Integer> {
    Cart findCartByIdUser(User user);
}
