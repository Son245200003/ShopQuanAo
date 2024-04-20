package com.example.api.RestController;

import com.example.api.Service.Serviceimpl.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExcelController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/excel")
    public void generateExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey="Content-Disposition";
        String headerValue="attachment;filename=thongke.xls";

        response.setHeader(headerKey,headerValue);
        reportService.generateExcel(response);
    }
}
