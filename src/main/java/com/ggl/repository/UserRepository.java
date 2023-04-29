package com.ggl.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ggl.entity.User;


public interface UserRepository extends JpaRepository<User, String> {
  

  
  
}
