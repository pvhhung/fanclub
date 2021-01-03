package com.project.fanclub.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_role")
public class Role extends EntityBase {

	private String roleName;

	private String roleKey;

	@OneToMany(mappedBy = "role", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Set<User> users = new HashSet<>();

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

}
