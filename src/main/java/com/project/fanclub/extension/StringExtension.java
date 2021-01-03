package com.project.fanclub.extension;

import java.util.Optional;

import com.project.fanclub.entity.User;

public class StringExtension {
	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.trim().isEmpty())
			return false;
		return true;
	}
}
