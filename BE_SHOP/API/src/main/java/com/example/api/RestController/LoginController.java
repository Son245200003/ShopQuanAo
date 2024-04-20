package com.example.api.RestController;

import com.example.api.Entity.User;
import com.example.api.Model.LoginReponse;
import com.example.api.Model.LoginRequest;
import com.example.api.Service.Serviceimpl.JwtService;
import com.example.api.Service.Serviceimpl.SmsService;
import com.example.api.Service.Serviceimpl.User_impl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    User_impl userImpl;
    @Autowired
    SmsService smsService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest ) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User u = (User) authentication.getPrincipal();

            System.out.println(u);
            String jwt = jwtService.generationtoken((User) authentication.getPrincipal());

            return ResponseEntity.ok(new LoginReponse(jwt, jwtService.getExpirationTime(),u.getRoles(),u.getId()));
        } catch (Exception e) {
            // Xác thực không thành công, trả về mã trạng thái 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("sai thông tin");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok("logout thành công");
    }
    //doi mat khau
    @PutMapping("/changepass/{id}")
    public ResponseEntity<?> changepass(@RequestParam("otp") String otp,@PathVariable("id") int id, @RequestBody User user){
        User check = userImpl.findbyId(id);
        if(check==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong tim thay user voi id"+id);

        }
        // Xác minh mã OTP
        boolean otpVerified = smsService.CkeckingOTp(otp);
        if (!otpVerified) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mã OTP không hợp lệ hoặc đã hết hạn");
        }
        check.setPassword(user.getPassword());
        return ResponseEntity.ok(userImpl.update(check));


    }


}
