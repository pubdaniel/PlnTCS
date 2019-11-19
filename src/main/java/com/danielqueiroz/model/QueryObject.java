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
	private String fromWho;
	private String where;
	private Date fromWhen;
	
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

	public Boolean hasFromWho() {
		return getFromWho() != null && !getFromWho().trim().isEmpty();
	}
	
	public String getFromWho() {
		return fromWho;
	}

	public void setFromWho(String fromWho) {
		this.fromWho = fromWho;
	}
	
	public Boolean hasFromWhere() {
		return getWhere() != null && !getWhere().trim().isEmpty();
	}
	
	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public Date getFromWhen() {
		return fromWhen;
	}

	public void setFromWhen(Date fromWhen) {
		this.fromWhen = fromWhen;
	}

	
	
	
	
}
