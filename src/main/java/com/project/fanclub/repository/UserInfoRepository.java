package com.project.fanclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fanclub.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	UserInfo findByUserId(int userId);
//	void deleteByUserId(int userId);
}
