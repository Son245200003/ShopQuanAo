package com.example.api.Model;

import com.example.api.Entity.Role;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Set;

@Data
@ToString
public class LoginReponse {
    private String Token;
    private  long Expired;
    private Set<Role> roles;
    private int id;
    public LoginReponse(String token, long expired, Set<Role> roles,int id) {
        Token = token;
        Expired = expired;
        this.roles = roles;
        this.id = id;
    }
}
