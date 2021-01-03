package com.project.fanclub.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project.fanclub.entity.Category;

public interface CategoryService {

	List<Category> getAll();

	ResponseEntity<?> findById(Integer categoryId);

	ResponseEntity<?> save(Category category);

	ResponseEntity<?> delete(Integer categoryId);

	ResponseEntity<?> update(Integer categoryId, Category category);
}
