package com.example.api.Service;

import com.example.api.Entity.CartItem;
import com.example.api.Entity.User;

import java.util.List;

public interface Cart_Service {
    public List<CartItem> getCartByUser(User user);
    public CartItem addCart(int idProduct,User user,String size);
    public CartItem increaseQuantity(int idProduct,User user,String size);
    public CartItem decreaseQuantity(int idProduct,User user,String size);
    public void removeCart(int idCartitem);
    public double totalMoney(User user);
}
