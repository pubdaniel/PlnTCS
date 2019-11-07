package com.danielqueiroz.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

import org.cogroo.text.Sentence;

import opennlp.tools.langdetect.Language;

public class QueryObject {

	private Language language;
	private List<String> phrases;
	private List<Entity> names;
	private List<Sentence> sentences;
	private List<Entity> places;
	private Period period;
	

	
	public QueryObject() {
		this.period = Period.between(LocalDate.MIN, LocalDate.MAX);
	}

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

	public List<String> getPhrases() {
		return phrases;
	}

	public void setPhrases(List<String> phrases) {
		this.phrases = phrases;
	}

	public void setPersons(List<Entity> names) {
		this.names = names;
	}

	public List<Entity> getNames() {
		return names;
	}

	public void setNames(List<Entity> names) {
		this.names = names;
	}

	public List<Entity> getPlaces() {
		return places;
	}

	public void setPlaces(List<Entity> places) {
		this.places = places;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	
	
	
	
}
