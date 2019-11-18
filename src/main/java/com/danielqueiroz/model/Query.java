package com.danielqueiroz.model;

import java.util.Date;

public class Query {

	private Long id;
	private User user;
	private Date date;
	private String message;
	private String sql;
	private int relevance;

	
	public Query() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getRelevance() {
		return relevance;
	}
	public void setRelevance(int relevance) {
		this.relevance = relevance;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	@Override
	public String toString() {
		return "Query id " + id + ", user " + user + ", date " + date + ", message " + message  + ", sql " + sql + ", relevance " + relevance;
	}
	
	
	
	
	
	
}
