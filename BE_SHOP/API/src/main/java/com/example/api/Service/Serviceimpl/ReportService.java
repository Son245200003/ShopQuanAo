package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.Order;
import com.example.api.Reponsitory.Order_Repository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private Order_Repository orderRepository;
    public void generateExcel(HttpServletResponse response) throws IOException {
        List<Order> orders=orderRepository.findAll();
        HSSFWorkbook hssfWorkBook=new HSSFWorkbook();
        HSSFSheet sheet=hssfWorkBook.createSheet("Thống kê");
        HSSFRow row=sheet.createRow(0);

        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("SDT");
        row.createCell(2).setCellValue("Tên người đặt");
        row.createCell(3).setCellValue("Tổng tien");
        row.createCell(4).setCellValue("Trạng thái");

        int dataRowIndex=1;
        double total=0;
        int stt=0;
        for (Order order:orders){
            stt++;
            total+=order.getTotalMoney();
            HSSFRow dataRow=sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(stt);
            dataRow.createCell(1).setCellValue(order.getUserId().getNumberphone());
            dataRow.createCell(2).setCellValue(order.getUserId().getUsername());
            dataRow.createCell(3).setCellValue(order.getTotalMoney());
            dataRow.createCell(4).setCellValue(order.getStatus());
            dataRowIndex++;
        }
        HSSFRow dataRow=sheet.createRow(dataRowIndex+2);
        dataRow.createCell(0).setCellValue("Tổng đơn: "+stt);
        dataRow.createCell(1).setCellValue(stt);
        dataRow.createCell(2).setCellValue("Tổng doanh thu của "+stt+" đơn");
        dataRow.createCell(3).setCellValue(+total+"$");
        sheet.setColumnWidth(dataRowIndex+2,15*256);

        ServletOutputStream ops=response.getOutputStream();
        hssfWorkBook.write(ops);
        hssfWorkBook.close();
        ops.close();

    }
}
