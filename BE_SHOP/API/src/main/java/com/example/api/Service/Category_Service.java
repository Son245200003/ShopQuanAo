package com.example.api.Service;


import com.example.api.Entity.Category;
import com.example.api.Entity.User;

import java.util.ArrayList;
import java.util.Optional;

public interface Category_Service {
  ArrayList<Category> getALL();
  Category findById(int id);
  Category save(Category category);
  void delete(int id);
  Category findByName(String name);
  ArrayList<Category> searchbyName(String name);
}
