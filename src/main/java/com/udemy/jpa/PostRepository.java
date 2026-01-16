package com.udemy.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.user.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
