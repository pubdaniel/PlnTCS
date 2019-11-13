package com.danielqueiroz.model;

import java.util.Date;

public class Post {

	private String id;
	private String username;
	private String name;
	private int followers;
	private String place;
	private Long postid;
	private boolean isRetweet;
	private String text;
	private Date date;
	private Integer Relevance;

	public Post() {
		super();
	}
	
	public Integer getRelevance() {
		return Relevance;
	}

	public void setRelevance(Integer relevance) {
		Relevance = relevance;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Long getPostid() {
		return postid;
	}

	public void setPostid(Long postid) {
		this.postid = postid;
	}

	public boolean isRetweet() {
		return isRetweet;
	}

	public void setRetweet(boolean isRetweet) {
		this.isRetweet = isRetweet;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "POST id " + id + ", username " + username + ", name " + name + ", followers " + followers + ", place "
				+ place + ", postid " + postid + ", isRetweet " + isRetweet + ", text " + text + ", date " + date;
	}
	
	
	
}
