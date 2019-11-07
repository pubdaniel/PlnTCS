package com.danielqueiroz.model;

import com.danielqueiroz.constants.Constants;

public class Permission {

	private Long id;
	private String description;
	private Integer level;
	
	public Permission() {
		super();
	}
	
	public static Permission newUserPermission() {
		Permission p = new Permission();
		p.setId(1L);
		p.setLevel(Constants.User.Level.USER.level());
		p.setDescription(Constants.User.Level.USER.description());
		return p;
		
	}
	
	public static Permission newAdminPermission() {
		Permission p = new Permission();
		p.setId(1L);
		p.setLevel(Constants.User.Level.USER.level());
		p.setDescription(Constants.User.Level.USER.description());
		return p;
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
	
}
