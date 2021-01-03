package com.project.fanclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fanclub.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{
	Category findByCategoryKey(String categoryKey);
}
