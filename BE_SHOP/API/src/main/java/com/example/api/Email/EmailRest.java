package com.example.api.Email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.api.Email.EmailService;
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class EmailRest {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public void sendEmail(@RequestBody Email email) {
        emailService.sendMail(email);
    }
}
