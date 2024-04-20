package com.example.api.Service;

import com.example.api.Entity.FeedBack;

import java.util.ArrayList;
import java.util.List;

public interface FeedBackService {
    FeedBack addFeedBack(FeedBack feedBack);
    FeedBack updateFeedBack(FeedBack feedBack);
    List<FeedBack> findAllList();
    void deleteByIdFeedBack(int id);
    FeedBack getFeedbackById(int id);
    ArrayList<FeedBack> search(String name);
}
