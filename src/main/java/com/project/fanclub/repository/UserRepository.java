package com.project.fanclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fanclub.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	User findByUsername(String username);
}
