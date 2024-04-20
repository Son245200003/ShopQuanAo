package com.example.api.RestController.Admin;

import com.example.api.Entity.Role;
import com.example.api.Entity.User;
import com.example.api.Service.Serviceimpl.User_impl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@RequestMapping("/admin/users")
public class quanlyuserController {
    @Autowired
    User_impl userservice;
    //hien thi phan trang
    @GetMapping("/listusers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "7") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userservice.getallUser(pageable);
        return ResponseEntity.ok(userPage);
    }

    // da test thanh cong
    @PostMapping("/adduser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestBody User user){
        User isxistUser = userservice.findbyUsername(user.getUsername());
        // neu ton tai thong tin trung thi tra ra status 409
        //da test thanh cong
        if(isxistUser!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("đã tồn tại Username");

        }

            return ResponseEntity.status(HttpStatus.OK).body(userservice.save(user));

    }

    //da test thanh cong
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id) {
        User user = userservice.findbyId(id);
        if(user!=null){
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("khong tim thay user");
        }
    }
    //update da test thanh cong
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        User existingUser = userservice.findbyId(id);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
        }

        // Kiểm tra xem username mới đã tồn tại hay chưa
        User userByUsername = userservice.findbyUsername(user.getUsername());
        if (userByUsername != null && userByUsername.getId() != existingUser.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên người dùng đã tồn tại");
        }
        if(user.getPassword().isEmpty()){
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setNumberphone(user.getNumberphone());
            existingUser.setAddress(user.getAddress());
            User updatedUser = userservice.update(existingUser);
            return ResponseEntity.ok(updatedUser);
        }
        else {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setNumberphone(user.getNumberphone());
            existingUser.setAddress(user.getAddress());
            existingUser.setPassword((user.getPassword()));
            User updatedUser = userservice.update(existingUser);
            return ResponseEntity.ok(updatedUser);
        }

        // Cập nhật thông tin người dùng

    }
    //da test thanh cong
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") int id){

        User isxistUser = userservice.findbyId(id);
        if(isxistUser==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("khong ton tai User");

        }else {
            userservice.deleteuserbyID(id);
            return ResponseEntity.ok().body("Xoa thanh cong ");
        }
    }
    //serch
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> search(@RequestParam("keyword") String key){
        ArrayList<User> users = userservice.searchbyName(key);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/test")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> find(@AuthenticationPrincipal User user){
        if(user!=null){
            return ResponseEntity.ok(userservice.findbyId(user.getId()));
        }
        return ResponseEntity.notFound().build();
    }
}
