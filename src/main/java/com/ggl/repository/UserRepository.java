package com.ggl.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ggl.entity.User;


public interface UserRepository extends JpaRepository<User, String> {
    @Query(nativeQuery=true,value = "select * from t_user as u where u.username=?1")
  List<User> findAllByUsername(String username);
}
