package com.project.fanclub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.project.fanclub.entity.Post;
import com.project.fanclub.entity.PostModel;

public interface PostService {
	List<Post> getAll();

	ResponseEntity<?> findByCategoryId(int categoryId);

	ResponseEntity<?> findByUserId(int userId);

	ResponseEntity<?> save(PostModel postModel);

	ResponseEntity<?> delete(int postId);

	ResponseEntity<?> update(int postId, PostModel postModel);
}
