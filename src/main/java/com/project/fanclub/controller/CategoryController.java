package com.project.fanclub.controller;

import java.util.List;

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

import com.project.fanclub.entity.Category;
import com.project.fanclub.service.CategoryService;
import com.project.fanclub.service.CommentService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public List<Category> getAll() {
		return categoryService.getAll();
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<?> categoryById(@PathVariable("categoryId") int categoryId) {
		return categoryService.findById(categoryId);
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('admin')")
	public ResponseEntity<?> save(@RequestBody Category category) {
		return categoryService.save(category);
	}

	@DeleteMapping("/{categoryId}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public ResponseEntity<?> delete(@PathVariable("categoryId") int categoryId) {
		return categoryService.delete(categoryId);
	}

	@PutMapping("/{categoryId}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public ResponseEntity<?> update(@PathVariable("categoryId") int categoryId, @RequestBody Category category) {
		return categoryService.update(categoryId, category);
	}
}
