package com.project.fanclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fanclub.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
	Token findByToken(String token);
}
