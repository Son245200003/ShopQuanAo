package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.FeedBack;
import com.example.api.Reponsitory.FeedBack_Repository;
import com.example.api.Service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FeedBackService_Impl implements FeedBackService {
    @Autowired
    FeedBack_Repository feedBackRepository;
    @Override
    public FeedBack addFeedBack(FeedBack feedBack) {
        return feedBackRepository.save(feedBack);
    }

    @Override
    public FeedBack updateFeedBack(FeedBack feedBack) {
        return feedBackRepository.saveAndFlush(feedBack);
    }

    @Override
    public List<FeedBack> findAllList() {
        return feedBackRepository.findAll();
    }

    @Override
    public void deleteByIdFeedBack(int id) {
        feedBackRepository.deleteById(id);
    }

    @Override
    public FeedBack getFeedbackById(int id) {
        return feedBackRepository.getFeedBackByIdFeedBack(id);
    }

    @Override
    public ArrayList<FeedBack> search(String name) {
        return feedBackRepository.findByFirstNameContaining(name);
    }
}
