package com.example.api.Reponsitory;

import com.example.api.Entity.Category;
import com.example.api.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface Product_Reponsitory extends JpaRepository<Product,Integer> {

    Page<Product> findAllByCategory(Category category,Pageable pageable);
    ArrayList<Product> findAllByCategory(Category category);

    ArrayList<Product> findFirst6By();
}
