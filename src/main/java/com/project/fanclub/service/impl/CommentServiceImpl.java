package com.project.fanclub.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.fanclub.entity.Comment;
import com.project.fanclub.entity.Post;
import com.project.fanclub.entity.User;
import com.project.fanclub.extension.StringExtension;
import com.project.fanclub.repository.CommentRepository;
import com.project.fanclub.repository.PostRepository;
import com.project.fanclub.repository.UserRepository;
import com.project.fanclub.security.SecurityAuditorAware;
import com.project.fanclub.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SecurityAuditorAware securityAuditorAware;

	@Override
	public ResponseEntity<?> findById(int commentId) {
		if (commentRepository.findById(commentId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bình luận");
		}
		Comment comment = commentRepository.findById(commentId).get();
		return ResponseEntity.ok(comment);
	}

	@Override
	public ResponseEntity<?> save(int postId, String content) {
		Integer userId = securityAuditorAware.getCurrentAuditor().get();
		if (postRepository.findById(postId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài viết");
		}
		if (StringExtension.isNullOrEmpty(content)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bình luận không hợp lệ");
		}
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setPost(postRepository.findById(postId).get());
		comment.setUser(userRepository.findById(userId).get());
		commentRepository.saveAndFlush(comment);
		return ResponseEntity.status(HttpStatus.CREATED).body(comment);
	}

	@Override
	public ResponseEntity<?> delete(int commentId) {
		Integer userId = securityAuditorAware.getCurrentAuditor().get();
		User user = userRepository.findById(userId).get();
		if ("admin".equals(user.getRole().getRoleKey())) {
			if (commentRepository.findById(commentId).isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bình luận");
			}
			commentRepository.deleteById(commentId);
			return ResponseEntity.ok("Xóa thành công");
		}
		if (userId != commentRepository.findById(commentId).get().getUser().getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không có quyền xóa");
		}
		if (commentRepository.findById(commentId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bình luận");
		}
		commentRepository.deleteById(commentId);
		return ResponseEntity.ok("Xóa thành công");
	}

	@Override
	public ResponseEntity<?> update(int commentId, String content) {
		Integer userId = securityAuditorAware.getCurrentAuditor().get();
		User user = userRepository.findById(userId).get();
		if ("admin".equals(user.getRole().getRoleKey())) {
			if (commentRepository.findById(commentId).isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bình luận");
			}
			Comment comment = commentRepository.findById(commentId).get();
			comment.setContent(content);
			commentRepository.saveAndFlush(comment);
			return ResponseEntity.ok(comment);
		}
		if (userId != commentRepository.findById(commentId).get().getUser().getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không có quyền sửa");
		}
		if (commentRepository.findById(commentId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bình luận");
		}
		if (StringExtension.isNullOrEmpty(content)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bình luận không hợp lệ");
		}
		Comment comment = commentRepository.findById(commentId).get();
		comment.setContent(content);
		commentRepository.saveAndFlush(comment);
		return ResponseEntity.ok(comment);
	}

//	@Override
//	public ResponseEntity<?> findByPostId(int postId) {
//		if (postRepository.findById(postId).isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài viết");
//		}
//		if (commentRepository.findByPostId(postId).isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bình luận");
//		}
//		Set<Comment> comments = new HashSet<>();
//		comments = commentRepository.findByPostId(postId);
//		return ResponseEntity.ok(comments);
//	}
}
