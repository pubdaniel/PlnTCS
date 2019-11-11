package com.danielqueiroz.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.cogroo.text.Sentence;

import com.danielqueiroz.constants.Constants;
import com.danielqueiroz.constants.Constants.Entity.Type;

import opennlp.tools.langdetect.Language;

public class QueryObject {

	private Language language;
	private List<Entity> entitys;
	private List<Sentence> sentences;
	private String text;
	
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public List<Sentence> getSentences() {
		return sentences;
	}
	
	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}

	public List<Entity> getEntitys() {
		return entitys;
	}

	public void setEntitys(List<Entity> entitys) {
		this.entitys = entitys;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public List<Entity> getEntity(Type type) {
		return entitys.stream().filter(p -> type.equals(p.getType())).collect(Collectors.toList());
	}

	public void setPersons(List<Entity> names) {
		// TODO Auto-generated method stub
		
	}

	public void setPlaces(List<Entity> places) {
		// TODO Auto-generated method stub
		
	}

	public void setPeriod(Period period) {
		// TODO Auto-generated method stub
		
	}

	public void setPhrases(List<String> extractPhrases) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
