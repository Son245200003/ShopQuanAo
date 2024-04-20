package com.example.api.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "news")
public class New {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private int idNew;
    @Column(name = "title",length = 255)
    private String title;
//    @Lob
//    @Column(name = "img")
//    @Basic(fetch = FetchType.EAGER)
//    private byte[] img;
    @Lob
    @Column(name = "img")
    @Basic(fetch = FetchType.EAGER)
    private byte[] img;
    @Column(name = "discribe")
    private String discribe;
    @Column(name = "content")
    private String content;

}
