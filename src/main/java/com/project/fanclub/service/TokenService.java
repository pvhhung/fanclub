package com.project.fanclub.service;

import com.project.fanclub.entity.Token;

public interface TokenService {
	Token save(Token token);

	Token findByToken(String token);
}
