package com.project.fanclub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.fanclub.entity.Post;
import com.project.fanclub.entity.PostModel;
import com.project.fanclub.security.SecurityAuditorAware;
import com.project.fanclub.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping
	public List<Post> getAll() {
		return postService.getAll();
	}

	@GetMapping("/category")
	public ResponseEntity<?> postByCategoryId(@RequestParam int categoryId) {
		return postService.findByCategoryId(categoryId);
	}

	@GetMapping("/user")
	public ResponseEntity<?> postByUserId(@RequestParam int userId) {
		return postService.findByUserId(userId);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('user','admin')")
	public ResponseEntity<?> save(@RequestBody PostModel postModel) {
		return postService.save(postModel);
	}

	@DeleteMapping("/{postId}")
	@PreAuthorize("hasAnyAuthority('user','admin')")
	public ResponseEntity<?> delete(@PathVariable("postId") int postId) {
		return postService.delete(postId);
	}

	@PutMapping("/{postId}")
	@PreAuthorize("hasAnyAuthority('user')")
	public ResponseEntity<?> update(@PathVariable("postId") int postId, @RequestBody PostModel postModel) {
		return postService.update(postId, postModel);
	}
}
