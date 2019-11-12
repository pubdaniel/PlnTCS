package com.danielqueiroz.bo;

import com.danielqueiroz.dao.UserDAO;
import com.danielqueiroz.model.User;

public class UserBO {

	
	UserDAO dao;
	
	public UserBO() {
		this.dao = new UserDAO();
	}
	
	public User getUser(String key) {
		return dao.getUser(key);
	}

	public String getKey(String username, String password) {
		User user = dao.getUser(username, password);
		
		if (user == null ) {
			return null;
		}
		
		if (user.getAuthkey() != null) {
			return user.getAuthkey();
		}
		
		return dao.updateAuthKey(user).getAuthkey();
	}

}
