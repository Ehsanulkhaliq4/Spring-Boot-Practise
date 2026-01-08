package com.udemy.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.user.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
