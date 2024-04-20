    package com.example.api.Entity;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.RequiredArgsConstructor;

    import java.util.Arrays;
    import java.util.List;
    import java.util.Objects;
    import java.util.Set;

    @Entity
    @Data
    @Table(name = "Product")
    @AllArgsConstructor
    @RequiredArgsConstructor
    public class Product {
        @Id
        @GeneratedValue(strategy =  GenerationType.IDENTITY)
        private Integer id;
        @Column(name = "title")
        private String title;
        @Column(name = "price")
        private int price;
        @Lob
        private byte[] img;
        @Column(name = "description")
        private String description;
        @ManyToMany
        @JoinTable(
                name = "product_size",
                joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "size_id"))
        private List<Size> sizes;
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
        @JoinColumn(name = "category_id")
        private Category category;
        @JsonIgnore
        @OneToMany(fetch = FetchType.LAZY,mappedBy = "productId", cascade = CascadeType.ALL)
        private List<CartItem> cartItems;
        @JsonIgnore
        @OneToMany(mappedBy = "productId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        private List<OrderDetail> orderDetails;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }
    }
