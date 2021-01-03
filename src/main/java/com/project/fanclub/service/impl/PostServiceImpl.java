package com.project.fanclub.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.fanclub.entity.Category;
import com.project.fanclub.entity.Post;
import com.project.fanclub.entity.PostModel;
import com.project.fanclub.entity.User;
import com.project.fanclub.extension.StringExtension;
import com.project.fanclub.repository.CategoryRepository;
import com.project.fanclub.repository.PostRepository;
import com.project.fanclub.repository.UserRepository;
import com.project.fanclub.security.SecurityAuditorAware;
import com.project.fanclub.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SecurityAuditorAware securityAuditorAware;

	@Override
	public List<Post> getAll() {
		return postRepository.findAll();
	}

	@Override
	public ResponseEntity<?> findByCategoryId(int categoryId) {
		if (StringExtension.isNullOrEmpty(Integer.toString(categoryId))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi");
		}
		if (!categoryRepository.findById(categoryId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy danh mục");
		} 
		if ((categoryRepository.findById(categoryId).get().getPosts().size() == 0)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh mục này chưa có bài viết");
		}
		Set<Post> posts = new HashSet<Post>();
		posts = categoryRepository.findById(categoryId).get().getPosts();
		return ResponseEntity.ok(posts);
	}

	@Override
	public ResponseEntity<?> findByUserId(int userId) {
		if (StringExtension.isNullOrEmpty(Integer.toString(userId))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi");
		}
		if (!userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
		}
		if (postRepository.findByUserId(userId).size() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài viết");
		}
		List<Post> posts = new ArrayList<Post>();
		posts = postRepository.findByUserId(userId);
		return ResponseEntity.ok(posts);
	}

	@Override
	public ResponseEntity<?> save(PostModel postModel) {
		Integer userId = securityAuditorAware.getCurrentAuditor().get();
		if (!userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
		}
		User user = userRepository.findById(userId).get();
		Set<Category> categories = new HashSet<>();
		postModel.getCategoryKeys().forEach(r -> {
			Category category = categoryRepository.findByCategoryKey(r);
			categories.add(category);
		});
		Post post = new Post();
		post.setContent(postModel.getContent());
		post.setImage(postModel.getImage());
		post.setTitle(postModel.getTitle());
		post.setUser(user);
		post.setCategories(categories);
		postRepository.saveAndFlush(post);
		return ResponseEntity.status(HttpStatus.CREATED).body(post);
	}

	@Override
	public ResponseEntity<?> delete(int postId) {
		Integer userId = securityAuditorAware.getCurrentAuditor().get();
		User user = userRepository.findById(userId).get();
		if ("admin".equals(user.getRole().getRoleKey())) {
			if (null == postRepository.findById(postId)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài viết");
			}
			postRepository.deleteById(postId);
			return ResponseEntity.ok("Xóa thành công");
		}
		if (userId != postRepository.findById(postId).get().getUser().getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không có quyền xóa");
		}
		if (!postRepository.findById(postId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài viết");
		}
		postRepository.deleteById(postId);
		return ResponseEntity.ok("Xóa thành công");
	}

	@Override
	public ResponseEntity<?> update(int postId, PostModel postModel) {
		Integer userId = securityAuditorAware.getCurrentAuditor().get();
		if (userId != postRepository.findById(postId).get().getUser().getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không có quyền sửa");
		}
		if (userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
		}
		if (postRepository.findById(postId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài viết");
		}
		Post post = postRepository.findById(postId).get();
		Set<Category> categories = new HashSet<>();
		postModel.getCategoryKeys().forEach(r -> {
			Category category = categoryRepository.findByCategoryKey(r);
			categories.add(category);
		});
		post.setTitle(postModel.getTitle());
		post.setContent(postModel.getContent());
		post.setImage(postModel.getImage());
		post.setCategories(categories);
		postRepository.saveAndFlush(post);
		return ResponseEntity.ok(post);
	}

}
