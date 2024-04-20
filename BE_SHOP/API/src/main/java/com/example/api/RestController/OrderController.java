package com.example.api.RestController;

import com.example.api.Entity.Order;
import com.example.api.Entity.User;
import com.example.api.Service.Order_Serivce;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api-orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    @Autowired
    private Order_Serivce orderSerivce;
    @GetMapping("/admin")
    public List<Order> getAllAdmin(){
        return orderSerivce.findAll();
    }
    @GetMapping("/user")
    public List<Order> getAllUser(@AuthenticationPrincipal User user){
        return orderSerivce.findByUserId(user);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAllByPage(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Order> ordersPage = orderSerivce.findallPage(pageable);
        return ResponseEntity.ok(ordersPage);
    }
    @GetMapping("/satistic")
    public ResponseEntity<?> getAllByDay(@RequestParam("begin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate begin,
                                         @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
        List<Order> orders=orderSerivce.findAllByDay(begin.atStartOfDay(),end.atTime(23, 59, 59));
        if(orders.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ko có dữ liệu");
        }
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/search")
        public ResponseEntity<List<Order>> search(@RequestParam("keyword")String keyword){
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Order> searchResult = orderSerivce.search(keyword);
        return ResponseEntity.ok(searchResult);
    }

    @PostMapping("/addOrder")
    public Order addOrder(@RequestBody Order order,@AuthenticationPrincipal User user){

        return orderSerivce.addOrder(order,user);
    }
    @PutMapping("/admin/update/{id}")
    public Order update(@PathVariable int id
//                        @AuthenticationPrincipal User user
    ){
        return orderSerivce.updateOrder(id);
    }
    @DeleteMapping("/admin/delete/{id}")
    public void removeOrder(@PathVariable int id){
        orderSerivce.removeOrder(id);
    }
}
