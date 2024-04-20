package com.example.api.Reponsitory;

import com.example.api.Entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface FeedBack_Repository extends JpaRepository<FeedBack,Integer> {
    FeedBack getFeedBackByIdFeedBack(int id);
    ArrayList<FeedBack>  findByFirstNameContaining(String firtname);
}
