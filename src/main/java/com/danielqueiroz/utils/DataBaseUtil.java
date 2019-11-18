package com.danielqueiroz.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;

import com.danielqueiroz.dao.Conn;
import com.danielqueiroz.dao.PostDAO;
import com.danielqueiroz.model.Post;

public class DataBaseUtil {

	static int count = 0;
	static List<Post> posts;
	static Date date;
	
	public static void savePostsFromCSV() {
		
		date = new Date();
		File fileCSV = new File(
				"D:\\Desenvimento\\git\\PlnTCS\\src\\main\\resources\\db\\08112019 144958-tweets-pt-BR.csv");

		posts = new ArrayList<>();
		try {
			Files.lines(fileCSV.toPath(), Charset.forName("UTF-8")).forEach(l -> {
				date = new Date((date.getTime() - (1000L * 120)));
				String[] line = l.split(";");
				if (line.length == 9) {
					
					Post p = new Post();
					p.setId(line[0]);
					p.setUsername(line[1]);
					p.setName(line[2]);
					p.setFollowers(Integer.parseInt(line[3]));
					p.setPlace(line[4]);
					p.setPostid(Long.parseLong(line[5]));
					p.setRetweet(Boolean.parseBoolean((line[6])));
					p.setText(line[7]);
					p.setDate(date);

					System.out.print(date);					
					if (posts.size() >= 100) {
						savePosts(posts);
						posts = new ArrayList<>();
						System.out.println(count += posts.size());
						System.out.println("wait...");
						
					} else {
						posts.add(p);
					}
					
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void savePost(Post p) {
		String sql = "insert into post (id, username, name, fallowers, location, postid, isRetweet, message, postdate) values (?,?,?,?,?,?,?,?,?)";

		try (Connection conn = Conn.getConnection();
				PreparedStatement prepStmt = Conn.getPreparedStatement(conn, sql);) {
			prepStmt.setString(1, p.getId());
			prepStmt.setString(2, p.getUsername());
			prepStmt.setString(3, p.getName());
			prepStmt.setInt(4, p.getFollowers());
			prepStmt.setString(5, p.getPlace());
			prepStmt.setLong(6, p.getPostid());
			prepStmt.setBoolean(7, p.isRetweet());
			prepStmt.setString(8, p.getText());
			prepStmt.setTimestamp(9, new Timestamp(p.getDate().getTime()));
			prepStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void savePosts(List<Post> posts) {

		Connection conn = Conn.getConnection();
		PreparedStatement prepStmt = null;
		try {
			for (Post p : posts) {
				String sql = "insert into post (id, username, name, fallowers, location, postid, isRetweet, message, postdate) values (?,?,?,?,?,?,?,?,?)";
				prepStmt = Conn.getPreparedStatement(conn, sql);
				try {
					prepStmt.setString(1, p.getId());
					prepStmt.setString(2, p.getUsername());
					prepStmt.setString(3, p.getName());
					prepStmt.setInt(4, p.getFollowers());
					prepStmt.setString(5, p.getPlace());
					prepStmt.setLong(6, p.getPostid());
					prepStmt.setBoolean(7, p.isRetweet());
					prepStmt.setString(8, p.getText());
					prepStmt.setTimestamp(9, new Timestamp(p.getDate().getTime()));
					prepStmt.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		Conn.closeConnection(conn);
		Conn.closePreparedStatement(prepStmt);			

	}

	public static void main(String[] args) throws IOException {
		DataBaseUtil.savePostsFromCSV();
	}
	
}
