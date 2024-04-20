package com.example.api.Reponsitory;

import com.example.api.Entity.Category;
import com.example.api.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public  interface Category_Reponsitory  extends JpaRepository<Category,Integer> {
    Category findById(int id);

    Category findByNameCategory(String name);
    ArrayList<Category> findByNameCategoryContaining(String username);
}

