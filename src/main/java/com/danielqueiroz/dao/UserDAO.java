package com.danielqueiroz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.danielqueiroz.model.User;

public class UserDAO {

	public User getLogin(String username, String password) {

		return null;
	}

	public User getUser(String key) {

		String sql = "select * from user where authkey=?";

		
		try  (Connection conn = Conn.getConnection(); PreparedStatement prepStmt = Conn.getPreparedStatement(conn, sql);){
			User user = new User();
			
			prepStmt.setString(1, key);
			ResultSet result = prepStmt.executeQuery();

			if (result.next()) {
				user.setId(result.getLong("id"));
				user.setName(result.getString("name"));
				user.setNickname(result.getString("nickname"));
				user.setPermission(result.getInt("permission"));
				user.setAuthkey(key);
				
				return user;
			}

		} catch (SQLException e) {
			System.out.println("Erro ao carregar usuário: " + e.getMessage());
		}
		return null;
	}

	public static void main(String[] args) {
		UserDAO dao = new UserDAO();
		User user = dao.getUser("askkasjsd");
		System.out.println(user.toString());
	}

	public User getUser(String username, String password) {
		String sql = "select * from user where nickname =? AND password=?";
		
		try  (Connection conn = Conn.getConnection(); PreparedStatement prepStmt = Conn.getPreparedStatement(conn, sql);){
			User user = new User();
			
			prepStmt.setString(1, username);
			prepStmt.setString(2, password);
			
			ResultSet result = prepStmt.executeQuery();

			if (result.next()) {
				user.setId(result.getLong("id"));
				user.setName(result.getString("name"));
				user.setNickname(result.getString("nickname"));
				user.setPermission(result.getInt("permission"));
				user.setAuthkey(result.getString("authkey"));
				return user;
			}

		} catch (SQLException e) {
			System.out.println("Erro ao carregar usuário: " + e.getMessage());
		}
		return null;
	}

	public User updateAuthKey(User user) {
		String sql = "update user set authkey=? where id=?";
		
		try  (Connection conn = Conn.getConnection(); PreparedStatement prepStmt = Conn.getPreparedStatement(conn, sql);){
			
			String key = new Date().getTime() + user.getName();
			
			prepStmt.setString(1, String.valueOf(key.hashCode()));
			prepStmt.setLong(2, user.getId());
			
			Integer response = prepStmt.executeUpdate();
			
			if (response > 0) {
				return getUser(user.getAuthkey());
			}
		} catch (SQLException e) {
			System.out.println("Erro ao carregar usuário: " + e.getMessage());
		}
		return null;
		
	}

}
