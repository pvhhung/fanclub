package com.project.fanclub.entity;

import java.util.HashSet;
import java.util.Set;

public class PostModel {
	private String title;
	private String content;
	private String image;
	private Set<String> categoryKeys = new HashSet<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<String> getCategoryKeys() {
		return categoryKeys;
	}

	public void setCategoryKeys(Set<String> categoryKeys) {
		this.categoryKeys = categoryKeys;
	}



}
