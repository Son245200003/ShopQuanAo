package com.example.api.RestController;

import com.example.api.Entity.User;
import com.example.api.Service.Serviceimpl.SmsService;
import com.example.api.Service.Serviceimpl.User_impl;
import com.twilio.rest.verify.v2.service.Verification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class RegisterController {
    private final SmsService smsService;
    private final User_impl userImpl;
    String phone;
    @PostMapping("/api/sendOtp")
    public String send(@RequestBody String sdt){
        smsService.sendSMS(sdt);
        phone=sdt;
        System.out.println(sdt);
        return "Đã gửi phone:"+sdt;


    }

    @PostMapping("/register/verify")
    public ResponseEntity<?> verifyOTP(@RequestParam("otp") String otp,@RequestBody User user) {
        User isxistUser = userImpl.findbyUsername(user.getUsername());
        if(isxistUser!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("đã tồn tại Username");
        }
        // Xác minh mã OTP
        boolean otpVerified = smsService.CkeckingOTp(otp);
        if (otpVerified) {
            log.info(String.valueOf(user));
            user.setNumberphone(phone);
            user.setVerify_phone(true);
            // Mã OTP hợp lệ, đăng kí
            User savedUser = userImpl.save(user);

            return ResponseEntity.ok().body(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mã OTP không hợp lệ hoặc đã hết hạn");
        }
    }
//    @PostMapping("/register")
//    public ResponseEntity<?> verifyOTP(@RequestBody User user) {
//        User isxistUser = userImpl.findbyUsername(user.getUsername());
//        if(isxistUser!=null){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("đã tồn tại Username");
//        }
//        // Xác minh mã OTP
//            log.info(String.valueOf(user));
//            user.setNumberphone(phone);
//            user.setVerify_phone(true);
//            // Mã OTP hợp lệ, đăng kí
//            User savedUser = userImpl.save(user);
//
//            return ResponseEntity.ok().body(savedUser);
//
//    }
}
