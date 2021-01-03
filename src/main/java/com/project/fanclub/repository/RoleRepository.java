package com.project.fanclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fanclub.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Integer>{
	Role findByRoleKey (String roleKey);
}
