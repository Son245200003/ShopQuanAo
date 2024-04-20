package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.New;
import com.example.api.Reponsitory.New_Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewService_Impl implements com.example.api.Service.NewService {
    @Autowired
    private New_Repository newRepository;
    @Override
    public New addNew(New n) {
        return newRepository.save(n);
    }

    @Override
    public New updateNew(New n) {
        return newRepository.saveAndFlush(n);
    }

    @Override
    public void deleteNew(int id) {
        newRepository.deleteById(id);
    }

    @Override
    public List<New> findAllNew() {
        return newRepository.findAll();
    }

    @Override
    public Optional<New> findById(int id) {
        return newRepository.findById(id);
    }

    @Override
    public New getById(int id) {
        return newRepository.getByIdNew(id);
    }

    @Override
    public ArrayList<New> search(String title) {
        return newRepository.findByTitleContaining(title);
    }
}
