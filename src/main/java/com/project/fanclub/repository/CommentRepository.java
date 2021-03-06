package com.project.fanclub.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fanclub.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	Comment findByUserId (Integer userId);
	Set<Comment> findByPostId (Integer postId);
}
