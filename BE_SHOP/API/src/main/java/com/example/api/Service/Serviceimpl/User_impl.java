package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.Cart;
import com.example.api.Entity.Role;
import com.example.api.Entity.User;
import com.example.api.Reponsitory.Cart_Repository;
import com.example.api.Reponsitory.User_Reponsitory;
import com.example.api.Service.User_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class User_impl implements User_Service {
    User_Reponsitory userReponsitory;
    Cart_Repository cartRepository;
@Autowired
    public User_impl(User_Reponsitory userReponsitory, Cart_Repository cartRepository) {
        this.userReponsitory = userReponsitory;
        this.cartRepository = cartRepository;
    }

    @Override
    public User findbyUsername(String name) {
        return userReponsitory.findByUsername(name);

    }

    @Override
    public User save(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        System.out.println(encodedPassword);

        Cart cart=new Cart();
        cart.setIdUser(user);
        cartRepository.save(cart);
        Set<Role> rr = new HashSet<>();
        rr.add(new Role("ROLE_USER"));
        user.setRoles(rr);
        return userReponsitory.save(user);

    }


    @Override
    public boolean findbyPhone(String phone) {
        return userReponsitory.existsByNumberphone(phone);
    }

    @Override
    public Page<User> getallUser(Pageable pageable) {
        return userReponsitory.findAllUsersWithRoleUser(pageable);
    }

    @Override
    public User update(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userReponsitory.save(user);
    }


    @Override
    public User findbyId(int id) {
        return userReponsitory.findById(id);
    }

    @Override
    public void deleteuserbyID(int id) {
         userReponsitory.deleteById(id);
    }

    @Override
    public ArrayList<User> searchbyName(String name) {
        return userReponsitory.findByUsernameContaining(name);
    }


}
