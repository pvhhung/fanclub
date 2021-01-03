package com.project.fanclub.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_user")
public class User extends EntityBase {
	private String username;
	private String password;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@OneToMany(mappedBy = "user",cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Set<Post> posts = new HashSet<>();

	@OneToMany(mappedBy = "user",cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<>();

	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private UserInfo userInfo;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

//	public Set<Post> getPosts() {
//		return posts;
//	}
//
//	public void setPosts(Set<Post> posts) {
//		this.posts = posts;
//	}
//
//	public Set<Comment> getComments() {
//		return comments;
//	}
//
//	public void setComments(Set<Comment> comments) {
//		this.comments = comments;
//	}
//
	public UserInfo getUserInfo() {
		return userInfo;
	}
//
//	public void setUserInfo(UserInfo userInfo) {
//		this.userInfo = userInfo;
//	}

}
