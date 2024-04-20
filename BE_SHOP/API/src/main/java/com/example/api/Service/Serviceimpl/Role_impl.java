package com.example.api.Service.Serviceimpl;

import com.example.api.Reponsitory.Role_Reponsitory;
import com.example.api.Service.Role_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Role_impl implements Role_Service {
    @Autowired
    Role_Reponsitory roleReponsitory;

}
