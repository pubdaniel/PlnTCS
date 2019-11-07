package com.danielqueiroz.model;

import com.danielqueiroz.constants.Constants.Entity.Type;

public class Entity {

	private Long id;
	private String description;
	private Type type;
	private double typeProbability;
	
	
	public Entity() {
		super();
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type person) {
		this.type = person;
	}
	
	public double getTypeProbability() {
		return typeProbability;
	}
	public void setTypeProbability(double typeProbability) {
		this.typeProbability = typeProbability;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return description + " | " + type + " | " + typeProbability;
	}
	
	

}
