package org.java.app.db.auth.service;

import java.util.List;

import org.java.app.db.auth.pojo.Role;
import org.java.app.db.auth.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	@Autowired
	private RoleRepo roleRepo;
	
	public List<Role> findAll() {
		return roleRepo.findAll();
	}
	
	public Role findById(int id) {
		return roleRepo.findById(id).get();
	}
	
	public void saveRole(Role role) {
		roleRepo.save(role);
	}
}
