package com.bezkoder.spring.jpa.h2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.h2.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findBystatus(String status);

  List<User> findByusername(String username);
}
