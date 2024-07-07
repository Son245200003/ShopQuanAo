package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.Category;
import com.example.api.Entity.Product;
import com.example.api.Reponsitory.Product_Reponsitory;
import com.example.api.Service.Product_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class Product_impl implements Product_Service {
    private final
    Product_Reponsitory productReponsitory;
    @Override
    public Product createProduct(Product product)   {

        return productReponsitory.save(product);
    }

    @Override
    public Optional<Product> getProductbyId(int id) {
        return productReponsitory.findById(id);
    }


    @Override
    public ArrayList<Product> getAllProducts() {
        return (ArrayList<Product>) productReponsitory.findAll();
    }

    @Override
    public Product updateProduct(int id, Product newProduct) {
        Optional<Product> optionalProduct = productReponsitory.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setTitle(newProduct.getTitle());
            existingProduct.setPrice(newProduct.getPrice());
            existingProduct.setImg(newProduct.getImg()); // Cập nhật ảnh
            existingProduct.setDescription(newProduct.getDescription());
            existingProduct.setCategory(newProduct.getCategory());
            return productReponsitory.save(existingProduct);
        } else {
            return null;
        }
    }

    @Override
    public void deleteProduct(int id) {
        productReponsitory.deleteById(id);
    }

    @Override
    public Optional<Product> findbyId(int id) {
        return productReponsitory.findById(id);
    }

    @Override
    public Page<Product> getAllbyCategory(Category category,Pageable pageable) {
        return  productReponsitory.findAllByCategory(category,pageable);
    }

    @Override
    public ArrayList<Product> getAllProductByCategory(Category category) {

        return  productReponsitory.findAllByCategory(category);
    }

    @Override
    public ArrayList<Product> getAllProductlimit() {
        return productReponsitory.findFirst6By();
    }
}
