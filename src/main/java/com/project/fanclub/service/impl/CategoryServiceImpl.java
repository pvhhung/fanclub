package com.project.fanclub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.fanclub.entity.Category;
import com.project.fanclub.extension.StringExtension;
import com.project.fanclub.repository.CategoryRepository;
import com.project.fanclub.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ResponseEntity<?> save(Category category) {
		if (StringExtension.isNullOrEmpty(category.getCategoryKey())
				|| StringExtension.isNullOrEmpty(category.getCategoryName())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin không hợp lệ");
		}
		categoryRepository.saveAndFlush(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(category);
	}

	@Override
	public ResponseEntity<?> delete(Integer categoryId) {
		if (StringExtension.isNullOrEmpty(categoryId.toString())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin không hợp lệ");
		}
		if (categoryRepository.findById(categoryId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy danh mục");
		}
		categoryRepository.deleteById(categoryId);
		return ResponseEntity.ok("Xoá thành công");
	}

	@Override
	public ResponseEntity<?> update(Integer categoryId, Category cat) {
		if (StringExtension.isNullOrEmpty(categoryId.toString())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin không hợp lệ");
		}
		if (StringExtension.isNullOrEmpty(cat.getCategoryKey())
				|| StringExtension.isNullOrEmpty(cat.getCategoryName())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin không hợp lệ");
		}
		if (categoryRepository.findById(categoryId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy danh mục");
		}
		Category category = categoryRepository.findById(categoryId).get();
		category.setCategoryKey(cat.getCategoryKey());
		category.setCategoryName(cat.getCategoryName());
		categoryRepository.saveAndFlush(category);
		return ResponseEntity.ok(category);
	}

	@Override
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	@Override
	public ResponseEntity<?> findById(Integer categoryId) {
		if (StringExtension.isNullOrEmpty(categoryId.toString())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin không hợp lệ");
		}
		if (categoryRepository.findById(categoryId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy danh mục");
		}
		Category category = categoryRepository.findById(categoryId).get();
		return ResponseEntity.ok(category);
	}

}
