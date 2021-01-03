package com.project.fanclub.extension;

public class StringExtension {
	public static boolean isNullOrEmpty(String str) {
		if (str != null && str.trim().length() != 0)
			return false;
		return true;
	}
}
