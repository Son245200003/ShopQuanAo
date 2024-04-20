package com.example.api.Reponsitory;

import com.example.api.Entity.Cart;
import com.example.api.Entity.CartItem;
import com.example.api.Entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItem_Repository extends JpaRepository<CartItem,Integer> {
    @Transactional
    @Modifying
    @Query("delete from CartItem ci where ci.id=?1")
    void deleteById(int id);
    List<CartItem> findAllByCartId(Cart cart);
    CartItem findCartItemByProductId(Product product);
    CartItem findCartItemByProductIdAndCartIdAndSize(Product product,Cart cart,String size);
    CartItem findCartItemByProductIdAndCartId(Product product,Cart cart);
    @Transactional
    @Modifying
    @Query("delete from CartItem ci where ci.cartId=?1")
    void deleteAllByCartId(Cart cart);
}
