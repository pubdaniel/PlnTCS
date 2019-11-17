package com.danielqueiroz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.danielqueiroz.model.Query;
import com.danielqueiroz.model.QueryObject;
import com.danielqueiroz.model.User;

public class QueryDAO {

	public List<Query> getQueries() {
		String sql = "select * from query inner join user on user.id = query.user_id order by query.id desc limit 200";
		List<Query> queries = new ArrayList<>();

		try (Connection conn = Conn.getConnection();
				PreparedStatement prepStmt = Conn.getPreparedStatement(conn, sql)) {
						
			ResultSet result = prepStmt.executeQuery();

			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			while (result.next()) {
				User user = new User();
				user.setId(result.getLong("user_id"));
				user.setName(result.getString("name"));
				user.setNickname(result.getString("nickname"));
				
				Query query = new Query();
				query.setId(result.getLong("id"));
				query.setUser(user);
				
				query.setDate(new Date(result.getTimestamp("date").getTime()));
				query.setMessage(result.getString("message"));
				query.setRelevance(result.getInt("relevance"));
				queries.add(query);
			}

		} catch (SQLException e) {
			System.err.println("Erro ao registrar log de query: " + e.getMessage());
		}
		return queries;
	}

	public void saveQuery(Query query) {

		String sql = "insert into query (user_id, message, date, relevance) values (? ,?, now() ,?)";

		try (Connection conn = Conn.getConnection();
				PreparedStatement prepStmt = Conn.getPreparedStatement(conn, sql)) {
			prepStmt.setLong(1, query.getUser().getId());
			prepStmt.setString(2, query.getMessage());
			prepStmt.setDate(3, new java.sql.Date(new Date().getTime()));
			prepStmt.setInt(3, query.getRelevance());
			prepStmt.execute();
			System.out.println("Query [" + query.toString() + "] registrada com sucesso");
		} catch (SQLException e) {
			System.err.println("Erro ao registrar log de query: " + e.getMessage());
		}
	}

}
