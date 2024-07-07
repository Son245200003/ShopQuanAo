package com.example.api.RestController;

import com.example.api.Entity.Cart;
import com.example.api.Entity.CartItem;
import com.example.api.Entity.Product;
import com.example.api.Entity.User;
import com.example.api.Reponsitory.CartItem_Repository;
import com.example.api.Reponsitory.Cart_Repository;
import com.example.api.Reponsitory.Product_Reponsitory;
import com.example.api.Reponsitory.User_Reponsitory;

import com.example.api.Service.Cart_Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-carts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class CartController {
    private final Cart_Service cartService;
    @GetMapping("/getCart")
    public ResponseEntity<List<CartItem>> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getCartByUser(user));
    }
    @GetMapping("/total-price")
    public double getTotalMoney(@AuthenticationPrincipal User user){
        return cartService.totalMoney(user);
    }
    @PostMapping("/addCart/{idProduct}")
    public ResponseEntity<CartItem> addCart(@PathVariable int idProduct,@RequestParam("size") String size,@AuthenticationPrincipal User user){

        return ResponseEntity.ok(cartService.addCart(idProduct,user,size));
    }
    @PutMapping("/increase/{idProduct}")
    public ResponseEntity<CartItem> increaseQuantity(@PathVariable int idProduct,@AuthenticationPrincipal User user,@RequestParam("size") String size){
        return ResponseEntity.ok(cartService.increaseQuantity(idProduct,user,size));
    }
    @PutMapping("/decrease/{idProduct}")
    public ResponseEntity<CartItem> decreaseQuantity(@PathVariable int idProduct,@AuthenticationPrincipal User user,@RequestParam("size")String size){
        return ResponseEntity.ok(cartService.decreaseQuantity(idProduct,user,size));
    }
    @DeleteMapping("/remove/{idCartItem}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable int idCartItem){
        cartService.removeCart(idCartItem);
        return ResponseEntity.ok().build();
    }
}
