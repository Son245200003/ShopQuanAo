package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.User;
import com.example.api.Reponsitory.Role_Reponsitory;
import com.example.api.Reponsitory.User_Reponsitory;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceimp implements UserDetailsService {
    Role_Reponsitory roleReponsitory;
    User_Reponsitory userReponsitory;
    @Autowired
    public UserServiceimp(Role_Reponsitory roleReponsitory, User_Reponsitory userReponsitory) {
        this.roleReponsitory = roleReponsitory;
        this.userReponsitory = userReponsitory;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userReponsitory.findByUsername(username);

        System.out.println(user);

        if(user==null){
          throw  new UsernameNotFoundException("User not found ");

        }
        return  user;
    }
}
