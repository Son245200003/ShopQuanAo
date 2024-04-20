package com.example.api.Service;

import com.example.api.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface User_Service {
  User findbyUsername(String name);
  User save(User user);
  boolean findbyPhone(String phone);
  Page<User> getallUser(Pageable pageable);

User update(User user);

  User findbyId(int id);
  void deleteuserbyID(int id);
  ArrayList<User> searchbyName(String name);
}
