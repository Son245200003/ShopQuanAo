package com.example.api.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;
//    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User userId;
    @Column(name = "note")
    private String note;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "total_money")
    private double totalMoney;
    @Column(name = "status")
    private String status;
    @JsonIgnore
    @OneToMany(mappedBy = "orderId",cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail;

}
