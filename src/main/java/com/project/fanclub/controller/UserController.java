package com.project.fanclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.fanclub.entity.Token;
import com.project.fanclub.entity.User;
import com.project.fanclub.entity.UserModel;
import com.project.fanclub.security.JwtUtil;
import com.project.fanclub.security.SecurityAuditorAware;
import com.project.fanclub.security.UserPrincipal;
import com.project.fanclub.service.TokenService;
import com.project.fanclub.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserModel userModel) {
		return userService.register(userModel);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserModel userModel) {
		return userService.login(userModel);
	}

	@GetMapping("/users")
	@PreAuthorize("hasAnyAuthority('admin')")
	public List<User> getAll() {
		return userService.getAll();
	}

	@GetMapping("/users/{userId}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public ResponseEntity<?> findById(@PathVariable("userId") int userId) {
		return userService.findById(userId);
	}

	@DeleteMapping("/users/{userId}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public ResponseEntity<?> delete(@PathVariable("userId") int userId) {
		return userService.delete(userId);
	}

	@PutMapping("/users/{userId}")
	@PreAuthorize("hasAnyAuthority('user')")
	public ResponseEntity<?> update(@PathVariable("userId") int userId, @RequestParam String password) {
		return userService.update(userId, password);
	}

}