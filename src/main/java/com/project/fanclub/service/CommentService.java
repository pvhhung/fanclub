package com.project.fanclub.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.project.fanclub.entity.Comment;

public interface CommentService {

//	ResponseEntity<?> findByPostId(int postId);

	ResponseEntity<?> findById(int commentId);

	ResponseEntity<?> save(int postId, String content);

	ResponseEntity<?> delete(int commentId);

	ResponseEntity<?> update(int commentId, String content);

}
