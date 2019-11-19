package com.danielqueiroz.model;

import javax.validation.ConstraintTarget;

import com.danielqueiroz.constants.Constants;
import com.danielqueiroz.constants.Constants.Entity.Type;

import opennlp.tools.util.Span;

public class Entity {

	private Long id;
	private int index;
	private String description;
	private Type type;
	private double probability;
	
	
	public Entity() {
		super();
	}
	
	
	public Entity(Span name) {
		this.probability = name.getProb();
		this.index = name.getStart();
		Type type = getTypeConstant(name.getType());
		this.type = type;
		
	}

	private Type getTypeConstant(String type) {
		switch (type) {
		case "person":
			return Constants.Entity.Type.PERSON;
		case "numeric":
			return Constants.Entity.Type.NUMERIC;
		case "time":
			return Constants.Entity.Type.TIME;
		case "organization":
			return Constants.Entity.Type.ORGANIZATION;
		case "place":
			return Constants.Entity.Type.PLACE;
		case "event":
			return Constants.Entity.Type.EVENT;
		default:
			return null;
		}
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
	
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	@Override
	public String toString() {
		return description + " | " + type + " | " + probability;
	}
	
	

}
