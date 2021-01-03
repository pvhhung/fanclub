package com.project.fanclub.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project.fanclub.entity.User;
import com.project.fanclub.entity.UserModel;
import com.project.fanclub.security.UserPrincipal;

public interface UserService {
	ResponseEntity<?> register(UserModel userModel);

	ResponseEntity<?> login(UserModel userModel);

	UserPrincipal findByUsername(String username);

	List<User> getAll();

	ResponseEntity<?> findById(int userId);

	ResponseEntity<?> delete(int userId);

	ResponseEntity<?> update(int userId, String password);
}
