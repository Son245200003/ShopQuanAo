package com.example.api.Service;

import com.example.api.Entity.New;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface NewService {
    public New addNew(New n);
    public New updateNew(New n);
    public void deleteNew(int id);
    public List<New> findAllNew();
    public Optional<New> findById(int id);
    public New getById(int id);
    public ArrayList<New> search(String title);
}
