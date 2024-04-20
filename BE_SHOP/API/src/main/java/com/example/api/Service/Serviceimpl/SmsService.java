package com.example.api.Service.Serviceimpl;

import com.example.api.Config.TwilioConfig;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    @Autowired
    TwilioConfig twilioConfig;
    String sid;
    public Verification sendSMS(String phone){

        String convert = convertTophone(phone);
        //đây chỉ là khởi tạo sms message
        //        Message.creator(
        //                new PhoneNumber("+84979753098"),
        //                new PhoneNumber(twilioConfig.getTwilioPhoneNumber()), // Số điện thoại nguồn
        //                "Hello from Twilio 📞"
        //        ).create();

        //khời tạo phương thức nhập mã otp
        Verification verification= Verification.creator(
                        twilioConfig.getServiceSid(), convert,"sms"
                )
                .create( );


        sid=verification.getSid();
        System.out.println("day la "+sid);
        System.out.println(verification.getStatus());
        return verification;
    }
    public String convertTophone(String phoneNumber) {

        if (!phoneNumber.startsWith("+") && !phoneNumber.startsWith("84")) {
            // Kiểm tra xem số điện thoại có đầu số "0" không
            if (phoneNumber.startsWith("0")) {
                // Thay thế "0" đầu tiên bằng "84"
                phoneNumber = "+84" + phoneNumber.substring(1);
            } else {
                // Nếu không có đầu số "0", thêm "84" vào đầu số
                phoneNumber = "+84" + phoneNumber;
            }
        }
        return phoneNumber;
    }
    public boolean CkeckingOTp(String code) {
        // Kiểm tra sid


        // Tiếp tục kiểm tra OTP
        VerificationCheck check = VerificationCheck.creator(
                        twilioConfig.getServiceSid())
                .setVerificationSid(sid)
                .setCode(code)
                .create();
        System.out.println(check.getStatus());
        return check.getStatus().equals("approved");

    }
}

