package com.slq.util;

import java.util.List;

import com.slq.pojo.Permission;
import com.slq.pojo.Role;
import com.slq.pojo.User;

import lombok.Data;
@Data
public class ActiveUser {

	private User user;
	
	private List<String> roles;
	
	private List<String> permissions;


	public ActiveUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActiveUser(User user, List<String> roles2, List<String> permissions2) {
		super();
		this.user = user;
		this.roles = roles2;
		this.permissions = permissions2;
	}

	public ActiveUser(User user) {
		super();
		this.user = user;
	}
	
	
}
