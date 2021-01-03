package com.project.fanclub.service;

import org.springframework.http.ResponseEntity;


public interface CommentService {

//	ResponseEntity<?> findByPostId(int postId);

	ResponseEntity<?> findById(int commentId);

	ResponseEntity<?> save(int postId, String content);

	ResponseEntity<?> delete(int commentId);

	ResponseEntity<?> update(int commentId, String content);

}
