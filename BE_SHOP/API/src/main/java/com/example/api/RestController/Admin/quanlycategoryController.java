package com.example.api.RestController.Admin;

import com.example.api.Entity.Category;
import com.example.api.Entity.User;
import com.example.api.Service.Serviceimpl.Category_impl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class quanlycategoryController {
    private final Category_impl categoryImpl;
    //list ALL
    @GetMapping("")
    public ResponseEntity<ArrayList<Category>> getALl(){
        return ResponseEntity.ok(categoryImpl.getALL());

    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") int id){
        Category category = categoryImpl.findById(id);
        if(category==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{

            return ResponseEntity.ok(category);
        }

    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Category category) {
      Category isexist= categoryImpl.findByName(category.getNameCategory());
        if(isexist!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Đã tồn tại "+isexist.getNameCategory());

        }else{
            categoryImpl.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        }


    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Category updatedCategory){
        Category existingCategory = categoryImpl.findById(id);
        if(existingCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy category với id: " + id);
        } else {
            existingCategory.setNameCategory(updatedCategory.getNameCategory());

            categoryImpl.save(existingCategory);
            return ResponseEntity.ok(existingCategory);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
       Category category= categoryImpl.findById(id);
        if(category==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("không tìm thấy với id:"+id);

        }else{
            categoryImpl.delete(id);
            return ResponseEntity.ok().body("xóa thành công");
        }

    }
    //search
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("keyword") String key){
        ArrayList<Category> categories = categoryImpl.searchbyName(key);
        return ResponseEntity.ok(categories);
    }

}
