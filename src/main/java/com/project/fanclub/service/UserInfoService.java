package com.project.fanclub.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project.fanclub.entity.UserInfo;
import com.project.fanclub.entity.UserInfoModel;

public interface UserInfoService {

	List<UserInfo> getAll();

	ResponseEntity<?> findByUserId(int userId);

	ResponseEntity<?> save(int userId, UserInfoModel userInfo);

	ResponseEntity<?> delete(int userId);

	ResponseEntity<?> update(int userId, UserInfoModel userInfo);
}
