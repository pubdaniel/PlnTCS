package com.danielqueiroz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.danielqueiroz.model.Query;
import com.danielqueiroz.model.QueryObject;
import com.danielqueiroz.model.User;

public class QueryDAO {

	public List<Query> getQueries(User user) {
		String sql = "select * from query where user_id=? order by date desc limit 25 ";
		List<Query> queries = new ArrayList<>();

		try (Connection conn = Conn.getConnection();
				PreparedStatement prepStmt = Conn.getPreparedStatement(conn, sql)) {
			prepStmt.setLong(1, user.getId());
			
			ResultSet result = prepStmt.executeQuery();

			while (result.next()) {
				Query query = new Query();
				query.setId(result.getLong("id"));
				query.setUser(user);
				query.setDate(result.getDate("date"));
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

		String sql = "insert into query (user_id, message, date, relevance) values (? ,?, now(), ?)";

		try (Connection conn = Conn.getConnection();
				PreparedStatement prepStmt = Conn.getPreparedStatement(conn, sql)) {
			prepStmt.setLong(1, query.getUser().getId());
			prepStmt.setString(2, query.getMessage());
			prepStmt.setInt(3, query.getRelevance());
			prepStmt.execute();
			System.out.println("Query [" + query.toString() + "] registrada com sucesso");
		} catch (SQLException e) {
			System.err.println("Erro ao registrar log de query: " + e.getMessage());
		}
	}

}
