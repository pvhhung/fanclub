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
import org.springframework.web.bind.annotation.RestController;

import com.project.fanclub.entity.UserInfo;
import com.project.fanclub.entity.UserInfoModel;
import com.project.fanclub.service.UserInfoService;

@RestController
@RequestMapping("/userinfos")
public class UserInfoController {
	@Autowired
	private UserInfoService userInfoService;

	@GetMapping
	@PreAuthorize("hasAnyAuthority('admin')")
	public List<UserInfo> getAll() {
		return userInfoService.getAll();
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasAnyAuthority('user')")
	public ResponseEntity<?> userInfoById(@PathVariable("userId") int userId) {
		return userInfoService.findByUserId(userId);
	}

	@PostMapping("/{userId}")
	@PreAuthorize("hasAnyAuthority('user','admin')")
	public ResponseEntity<?> save(@PathVariable("userId") int userId, @RequestBody UserInfoModel userInfo) {
		return userInfoService.save(userId, userInfo);
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public ResponseEntity<?> delete(@PathVariable("userId") int userId) {
		return userInfoService.delete(userId);
	}

	@PutMapping("/{userId}")
	@PreAuthorize("hasAnyAuthority('user')")
	public ResponseEntity<?> update(@PathVariable("userId") int userId, @RequestBody UserInfoModel userInfo) {
		return userInfoService.update(userId, userInfo);
	}
}
