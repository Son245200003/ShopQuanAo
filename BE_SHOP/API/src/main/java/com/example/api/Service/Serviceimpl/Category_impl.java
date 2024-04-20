package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.Category;
import com.example.api.Reponsitory.Category_Reponsitory;
import com.example.api.Service.Category_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class Category_impl implements Category_Service {
    @Autowired
    Category_Reponsitory category_reponsitory;

    @Override
    public ArrayList<Category> getALL() {
        return (ArrayList<Category>) category_reponsitory.findAll();
    }

    @Override
    public Category findById(int id) {
        return category_reponsitory.findById(id);
    }

    @Override
    public Category save(Category category) {
        return category_reponsitory.save(category);
    }

    @Override
    public void delete(int id) {
        category_reponsitory.deleteById(id);
    }

    @Override
    public Category findByName(String name) {
        return category_reponsitory.findByNameCategory(name);
    }

    @Override
    public ArrayList<Category> searchbyName(String name) {
        return category_reponsitory.findByNameCategoryContaining(name);
    }

}
