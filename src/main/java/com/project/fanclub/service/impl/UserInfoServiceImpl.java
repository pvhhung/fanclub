package com.project.fanclub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.fanclub.entity.UserInfo;
import com.project.fanclub.entity.UserInfoModel;
import com.project.fanclub.extension.StringExtension;
import com.project.fanclub.repository.UserInfoRepository;
import com.project.fanclub.repository.UserRepository;
import com.project.fanclub.security.SecurityAuditorAware;
import com.project.fanclub.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SecurityAuditorAware securityAuditorAware;

	@Override
	public List<UserInfo> getAll() {
		return userInfoRepository.findAll();
	}

	@Override
	public ResponseEntity<?> findByUserId(int userId) {
		Integer currentUserId = securityAuditorAware.getCurrentAuditor().get();
		if (currentUserId != userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn không có quyền");
		}
		if (StringExtension.isNullOrEmpty(Integer.toString(userId))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi");
		}
		if (userRepository.findById(userId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
		}
		if (null == userInfoRepository.findByUserId(userId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng chưa có thông tin");
		}
		UserInfo userInfo = userInfoRepository.findByUserId(userId);
		return ResponseEntity.ok(userInfo);
	}

	@Override
	public ResponseEntity<?> save(int userId, UserInfoModel userInfoModel) {
		Integer currentUserId = securityAuditorAware.getCurrentAuditor().get();
		if (currentUserId != userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn không có quyền");
		}
		if (StringExtension.isNullOrEmpty(Integer.toString(userId))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi");
		}
		if (userRepository.findById(userId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
		}
		if (null != userInfoRepository.findByUserId(userId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin đã tồn tại");
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setAddress(userInfoModel.getAddress());
		userInfo.setEmail(userInfoModel.getEmail());
		userInfo.setFullname(userInfoModel.getFullname());
		userInfo.setPhone(userInfoModel.getPhone());
		userInfo.setUser(userRepository.findById(userId).get());
		userInfoRepository.saveAndFlush(userInfo);
		return ResponseEntity.status(HttpStatus.CREATED).body(userInfo);
	}

	@Override
	public ResponseEntity<?> delete(int userId) {
		if (StringExtension.isNullOrEmpty(Integer.toString(userId))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi");
		}
		if (userRepository.findById(userId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
		}
		UserInfo userInfo = userInfoRepository.findByUserId(userId);
		userInfoRepository.delete(userInfo);
		return ResponseEntity.ok("Xóa thành công");
	}

	@Override
	public ResponseEntity<?> update(int userId, UserInfoModel userInfoModel) {
		Integer currentUserId = securityAuditorAware.getCurrentAuditor().get();
		if (currentUserId != userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn không có quyền");
		}
		if (StringExtension.isNullOrEmpty(Integer.toString(userId))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi");
		}
		if (userRepository.findById(userId).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng");
		}
		if (null == userInfoRepository.findByUserId(userId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng chưa có thông tin");
		}
		UserInfo userInfo = userInfoRepository.findByUserId(userId);
		userInfo.setAddress(userInfoModel.getAddress());
		userInfo.setEmail(userInfoModel.getEmail());
		userInfo.setFullname(userInfoModel.getFullname());
		userInfo.setPhone(userInfoModel.getPhone());
		userInfoRepository.saveAndFlush(userInfo);
		return ResponseEntity.ok(userInfo);
	}

}
