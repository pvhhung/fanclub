package com.project.fanclub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.fanclub.entity.Role;
import com.project.fanclub.entity.Token;
import com.project.fanclub.entity.User;
import com.project.fanclub.entity.UserModel;
import com.project.fanclub.extension.StringExtension;
import com.project.fanclub.repository.RoleRepository;
import com.project.fanclub.repository.UserRepository;
import com.project.fanclub.security.JwtUtil;
import com.project.fanclub.security.SecurityAuditorAware;
import com.project.fanclub.security.UserPrincipal;
import com.project.fanclub.service.TokenService;
import com.project.fanclub.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private SecurityAuditorAware securityAuditorAware;

	@Override
	public ResponseEntity<?> register(UserModel userModel) {
		if (StringExtension.isNullOrEmpty(userModel.getUsername())
				|| StringExtension.isNullOrEmpty(userModel.getPassword())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nhập thiếu thông tin");
		}
		if (null != userRepository.findByUsername(userModel.getUsername())) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Tên tài khoản bị trùng");
		}
		User user = new User();
		user.setUsername(userModel.getUsername());
		user.setPassword(new BCryptPasswordEncoder().encode(userModel.getPassword()));
		Role role = roleRepository.findByRoleKey("user"); // set role mặc định là user
		user.setRole(role);
		userRepository.saveAndFlush(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@Override
	public ResponseEntity<?> login(UserModel userModel) {
		if (StringExtension.isNullOrEmpty(userModel.getUsername())
				|| StringExtension.isNullOrEmpty(userModel.getPassword())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nhập thiếu thông tin");
		}
		UserPrincipal userPrincipal = findByUsername(userModel.getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (null == userModel || !encoder.matches(userModel.getPassword(), userPrincipal.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tài khoản hoặc mật khẩu không chính xác");
		}
		Token token = new Token();
		token.setToken(jwtUtil.generateToken(userPrincipal));
		token.setTokenExpDate(jwtUtil.generateExpirationDate());
		tokenService.save(token);

		return ResponseEntity.ok(token.getToken());
	}

	@Override
	public ResponseEntity<?> findById(int userId) {
		if (StringExtension.isNullOrEmpty(Integer.toString(userId))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi");
		}
		if (!userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
		}
		User user = userRepository.findById(userId).orElse(null);
		return ResponseEntity.ok(user);
	}

	@Override
	public UserPrincipal findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		UserPrincipal userPrincipal = new UserPrincipal();
		if (null != user) {
			Set<String> authorities = new HashSet<>();
			if (null != user.getRole()) {
				authorities.add(user.getRole().getRoleKey());
			}
			userPrincipal.setUserId(user.getId());
			userPrincipal.setUsername(user.getUsername());
			userPrincipal.setPassword(user.getPassword());
			userPrincipal.setAuthorities(authorities);
		}
		return userPrincipal;
	}

	@Override
	public ResponseEntity<?> delete(int userId) {
		if (StringExtension.isNullOrEmpty(Integer.toString(userId))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi");
		}
		if (!userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
		}
		userRepository.deleteById(userId);
		return ResponseEntity.ok("Xóa thành công");
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public ResponseEntity<?> update(int userId, String password) {
		Integer currentUserId = securityAuditorAware.getCurrentAuditor().get();
		if (currentUserId != userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không có quyền đổi");
		}
		if (StringExtension.isNullOrEmpty(Integer.toString(userId))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi");
		}
		if (!userRepository.findById(userId).isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
		}
		User user = userRepository.findById(userId).get();
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		userRepository.saveAndFlush(user);
		return ResponseEntity.ok(user);
	}

}