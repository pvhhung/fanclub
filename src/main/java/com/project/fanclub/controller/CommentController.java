package com.project.fanclub.controller;

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

import com.project.fanclub.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@GetMapping("/{commentId}")
	public ResponseEntity<?> commentById(@PathVariable("commentId") int commentId) {
		return commentService.findById(commentId);
	}
	
//	@GetMapping("/post")
//	public ResponseEntity<?> commentByPostId(@RequestParam int postId) {
//		return commentService.findByPostId(postId);
//	}

	@PostMapping("/post")
	@PreAuthorize("hasAnyAuthority('user')")
	public ResponseEntity<?> save(@RequestParam int postId, @RequestParam String content) {
		return commentService.save(postId, content);
	}

	@DeleteMapping("/{commentId}")
	@PreAuthorize("hasAnyAuthority('user','admin')")
	public ResponseEntity<?> delete(@PathVariable("commentId") int commentId) {
		return commentService.delete(commentId);
	}

	@PutMapping("/{commentId}")
	@PreAuthorize("hasAnyAuthority('user','admin')")
	public ResponseEntity<?> update(@PathVariable("commentId") int commentId, @RequestParam String content) {
		return commentService.update(commentId, content);
	}
}
