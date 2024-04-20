package com.example.api.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "role")
public class Role {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Column(name = "name")
    private  String name;

    public Role(String name) {
        this.name = name;
    }
//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;

}
