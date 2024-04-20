package com.example.api.Data;

import com.example.api.Entity.Category;
import com.example.api.Entity.Product;
import com.example.api.Entity.User;
import com.example.api.Reponsitory.Category_Reponsitory;
import com.example.api.Reponsitory.Product_Reponsitory;
import com.example.api.Service.Serviceimpl.Category_impl;
import com.example.api.Service.Serviceimpl.User_impl;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class insert {
    @Autowired
    private User_impl userImpl;
    @Autowired
    Category_impl categoryImpl;
    @Autowired
    EntityManager entityManager;
    @Autowired
    Category_Reponsitory category_reponsitory;
    @Autowired
    Product_Reponsitory productReponsitory;

//@Bean
//void saveCategoryWithProducts() {
//        Category category = new Category();
//        category.setNameCategory("Category Name");
//
//
//        Set<Product> products = new HashSet<>();
//
//        Product product1 = new Product();
//        product1.setTitle("Product 1");
//    product1.setCategory(category); // Đặt Category cho Product
//
//
//    Product product2 = new Product();
//        product2.setTitle("Product 2");
//    product2.setCategory(category); // Đặt Category cho Product
//
//
//    //category.setProducts(products);
//    category.getProducts().add(product1);
//    category.getProducts().add(product2);
//        category_reponsitory.save(category); // Lưu danh mục với sản phẩm
//
////    for (Product product : products) {
////        product.setCategory(category);
////        productReponsitory.save(product);
////    }
//}
//    @PostConstruct
//    public void insertuser(){
//        User user = new User();
//        user.setUsername("User4");
//        user.setPassword("1");
//        user.setAddress("1");
//        userImpl.save(user);
//    }
}

