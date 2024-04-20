package com.example.api.Reponsitory;

import com.example.api.Entity.New;
import com.example.api.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface New_Repository extends JpaRepository<New,Integer> {
    New findByIdNew(int id);
    New getByIdNew(int id);
    ArrayList<New> findByTitleContaining(String title);
}
