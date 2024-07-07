package com.example.api.Email;

import com.example.api.Entity.Order;
import com.example.api.Service.Order_Serivce;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final Order_Serivce orderService;
@Autowired
    public EmailService(JavaMailSender javaMailSender, Order_Serivce orderService) {
        this.javaMailSender = javaMailSender;
        this.orderService = orderService;
    }

    public void sendMail(Email email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("danhson24520003@gmail.com");
            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());

            // Lấy danh sách đơn hàng từ orderService
            List<Order> orders = orderService.findAll();
            int totalOrders = orders.size();
            double totalMoney = 0;
            for (Order order : orders) {
                totalMoney += order.getTotalMoney();
            }
            // Tạo một chuỗi HTML từ danh sách đơn hàng
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<!DOCTYPE html><html><head><style>");
            htmlContent.append("table { border-collapse: collapse; width: 100%; }");
            htmlContent.append("th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }");
            htmlContent.append("th { background-color: #f2f2f2; }");
            htmlContent.append("</style></head><body>");
            htmlContent.append("<h2 style=\"text-align: center;\">Báo Cáo Doanh Thu Và Đơn Hàng Chi Tiết</h2>");
            htmlContent.append("<div><p><strong>Tổng số đơn hàng:</strong> ").append(totalOrders).append("</p>");
            htmlContent.append("<p><strong>Tổng số tiền:</strong> ").append(totalMoney).append("</p></div>");
            htmlContent.append("<table><tr><th>STT</th><th>Tên người dùng</th><th>SĐT</th><th>Địa chỉ</th><th>Note</th><th>Tổng tiền</th><th>Trạng thái</th></tr>");
            int stt = 1;
            for (Order order : orders) {
                htmlContent.append("<tr>");
                htmlContent.append("<td>").append(stt++).append("</td>");
                htmlContent.append("<td>").append(order.getUserId().getUsername()).append("</td>");
                htmlContent.append("<td>").append(order.getUserId().getNumberphone()).append("</td>");
                htmlContent.append("<td>").append(order.getUserId().getAddress()).append("</td>");
                htmlContent.append("<td>").append(order.getNote()).append("</td>");
                htmlContent.append("<td>").append(order.getTotalMoney()).append("</td>");
                htmlContent.append("<td>").append(order.getStatus()).append("</td>");
                htmlContent.append("</tr>");
            }
            htmlContent.append("</table>");
            htmlContent.append("</body></html>");

            helper.setText(htmlContent.toString(), true); // Sử dụng true để bật chế độ HTML

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
