package com.danielqueiroz.dao;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.danielqueiroz.model.Post;

public class PostDAO {

	public List<Post> getPosts(String sql) {
		List<Post> posts = new ArrayList<>();

		Connection conn = Conn.getConnection();
		PreparedStatement prepStmt = Conn.getPreparedStatement(conn, sql);

		try {
			ResultSet result = prepStmt.executeQuery();

			while (result.next()) {
				Post p = sqlResultToPost(result);
				p.setRelevance(result.getInt("relevance"));
				posts.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}

	
	public List<Post> getPosts() {

		List<Post> posts = new ArrayList<>();

		String sql = "select * from post limit 10";

		Connection conn = Conn.getConnection();
		PreparedStatement prepStmt = Conn.getPreparedStatement(conn, sql);

		try {
			ResultSet result = prepStmt.executeQuery();
			while (result.next()) {
				Post p = sqlResultToPost(result);
				p.setRelevance(0);
				posts.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}

	
	private Post sqlResultToPost(ResultSet result) throws SQLException {
		Post p = new Post();
		p.setId(result.getString("id"));
		p.setUsername(result.getString("username"));
		p.setName(result.getString("name"));
		p.setFollowers(result.getInt("fallowers"));
		p.setPlace(result.getString("location"));
		p.setPostid(result.getLong("postid"));
		p.setRetweet(result.getBoolean("isRetweet"));
		p.setText(result.getString("message"));
		p.setDate(new Date(result.getTimestamp("postdate").getTime()));
		
		return p;
	}

	public static void main(String[] args) {
		PostDAO dao = new PostDAO();
		System.out.println(dao.getPosts());
	}

}
