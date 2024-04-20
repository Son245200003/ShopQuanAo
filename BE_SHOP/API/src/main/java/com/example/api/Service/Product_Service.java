package com.example.api.Service;

import com.example.api.Entity.Category;
import com.example.api.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

public interface Product_Service {
    Product createProduct(Product product);
    Optional<Product> getProductbyId(int id);
    ArrayList<Product> getAllProducts();
    Product updateProduct(int id, Product newProduct);

    void deleteProduct(int id);
    Optional<Product> findbyId(int id);
    Page<Product> getAllbyCategory(Category category,Pageable pageable);
    ArrayList<Product> getAllProductByCategory(Category category);
    ArrayList<Product> getAllProductlimit();
}
