package com.example.api.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int idCart;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "cartId", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User idUser;

    @Override
    public String toString() {
        return "Cart{" +
                "idCart=" + idCart +
                ", cartItems=" + cartItems +
                ", idUser=" + idUser +
                '}';
    }


}
