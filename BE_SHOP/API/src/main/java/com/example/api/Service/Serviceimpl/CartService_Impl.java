package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.Cart;
import com.example.api.Entity.CartItem;
import com.example.api.Entity.Product;
import com.example.api.Entity.User;
import com.example.api.Reponsitory.CartItem_Repository;
import com.example.api.Reponsitory.Cart_Repository;
import com.example.api.Reponsitory.Product_Reponsitory;
import com.example.api.Reponsitory.User_Reponsitory;
import com.example.api.Service.Cart_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
public class CartService_Impl implements Cart_Service {


    @Autowired
    private Product_Reponsitory productRepository;

    @Autowired
    private Cart_Repository cartRepository;

    @Autowired
    private CartItem_Repository cartItemRepository;
    @Autowired
    private User_Reponsitory userReponsitory;
    public List<CartItem> getCartByUser( User user) {

        Cart cart=cartRepository.findCartByIdUser(user);
        if (cart != null) {
            List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart);
            if (!cartItems.isEmpty()) {
                return cartItems;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public CartItem addCart(int idProduct,User user,String size){
        Cart cart=cartRepository.findCartByIdUser(user);
        Optional<Product> product=productRepository.findById(idProduct);
        Product pr=product.get();
        if(product!=null){
            CartItem cartItem=cartItemRepository.findCartItemByProductIdAndCartIdAndSize(pr,cart,size);

            if (cartItem==null){
                CartItem cartItem1=new CartItem();
                cartItem1.setQuantity(1);
                cartItem1.setProductId(pr);
                cartItem1.setCartId(cart);
                cartItem1.setSize(size);
                return cartItemRepository.save(cartItem1);
            }
            else {cartItem.setQuantity(cartItem.getQuantity()+1);
                return cartItemRepository.saveAndFlush(cartItem);}
        }else
            return null;
    }

    @Override
    public CartItem increaseQuantity(int idProduct, User user,String size) {
        Cart cart=cartRepository.findCartByIdUser(user);
        Optional<Product> product=productRepository.findById(idProduct);
        Product pr=product.get();
        if(product!=null){
            CartItem cartItem=cartItemRepository.findCartItemByProductIdAndCartIdAndSize(pr,cart,size);

            if (cartItem==null){

                return null;
            }
            else {
                cartItem.setQuantity(cartItem.getQuantity()+1);
                return cartItemRepository.saveAndFlush(cartItem);
            }
        }else
            return null;
    }

    @Override
    public CartItem decreaseQuantity(int idProduct, User user,String size) {
        Cart cart=cartRepository.findCartByIdUser(user);
        Optional<Product> product=productRepository.findById(idProduct);
//        cart.setIdUser(user);
        Product pr=product.get();
        if(product!=null){
            CartItem cartItem=cartItemRepository.findCartItemByProductIdAndCartIdAndSize(pr,cart,size);
            if (cartItem==null){
                return null;
            }
            else {
                if(cartItem.getQuantity()>0){
                    cartItem.setQuantity(cartItem.getQuantity()-1);
                    return cartItemRepository.saveAndFlush(cartItem);
                }
                else{
                    cartItem.setQuantity(cartItem.getQuantity()+0);
                    return cartItemRepository.saveAndFlush(cartItem);}
            }
        }else
            return null;
    }

    @Override
    public void removeCart(int idCartitem) {
        cartItemRepository.deleteById(idCartitem);
    }

    @Override
    public double totalMoney(User user) {
        Cart cart=cartRepository.findCartByIdUser(user);
        double totalPrice = 0.0;
        List<CartItem> cartItems=cartItemRepository.findAllByCartId(cart);
        // Duyệt qua từng mặt hàng trong giỏ hàng và tính tổng giá
        for (CartItem item : cartItems) {
            totalPrice += item.getProductId().getPrice() * item.getQuantity(); // Tính tổng giá của mặt hàng
        }

        return totalPrice;
    }


}
