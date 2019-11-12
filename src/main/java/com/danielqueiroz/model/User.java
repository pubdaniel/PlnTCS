package com.danielqueiroz.model;

public class User {

	private Long id;
	private String name;
	private String nickname;
	private String email;
	private String phone;
	private String password;
	private String authkey;
	private Integer permission;
	
	public User() {
		super();
	}
	
	
	public User(Long id) {
		super();
		this.id = id;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getPermission() {
		return permission;
	}
	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}


	@Override
	public String toString() {
		return "User id " + id + ", name " + name + ", nickname " + nickname + ", email " + email + ", phone " + phone
				+ ", password " + password + ", authkey " + authkey + ", permission " + permission;
	}

	
	
	
	
	
	
}
