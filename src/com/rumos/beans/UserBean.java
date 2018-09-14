package com.rumos.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.rumos.dao.UserDAO;
import com.rumos.exceptions.PreexistingUserException;

@Named("user")
@SessionScoped
public class UserBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	UserDAO userDao;

	private String username;
	private String password;
	private String role;
	private ArrayList<String> roleList = new ArrayList<String>();
	
	@PostConstruct
	public void init() {
		roleList.add("user");
		roleList.add("administrator");
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public ArrayList<String> getRoleList() {
		
		return roleList;
	}

	public String addUser() throws PreexistingUserException, Exception {
	
		userDao.novoUser(username, password, role);
		username = "";
		password = "";
		role="user";
		return "index";

	}

}
