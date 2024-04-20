package com.example.api.RestController;

import com.example.api.Service.Serviceimpl.JwtService;
import com.example.api.Service.Serviceimpl.UserServiceimp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
//sử dụng class này để linh hoạt trong việc kiểm tra jwt bên phía client
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VerifyJwt {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceimp userServiceimp;

    @PostMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Kiểm tra xem tiêu đề "Authorization" có chứa JWT không
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Trích xuất JWT từ tiêu đề "Authorization"
            String jwtToken = authorizationHeader.substring(7);

            // Kiểm tra tính hợp lệ của JWT
            if (jwtService.validateJwtToken(jwtToken)) {
                // Giải mã JWT và lấy username
                String username = jwtService.extractUsernamefromToken(jwtToken);
                // Lấy thông tin người dùng từ username
                UserDetails userDetails = userServiceimp.loadUserByUsername(username);

                // Trả về thông tin người dùng nếu JWT hợp lệ
                return ResponseEntity.ok(userDetails);
            }
        }

        // Trả về lỗi nếu JWT không hợp lệ hoặc không được cung cấp
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT");
    }
    @PostMapping("/verifyToken1")
    public String oke(){
        return "oke";
    }
}
