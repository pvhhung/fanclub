package com.project.fanclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fanclub.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByUserId(int userId);

//	List<Post> findByCategoryId(int categoryId);
}