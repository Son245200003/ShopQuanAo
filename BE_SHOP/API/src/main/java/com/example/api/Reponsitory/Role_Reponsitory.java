package com.example.api.Reponsitory;

import com.example.api.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Role_Reponsitory extends JpaRepository<Role,Integer> {

}
