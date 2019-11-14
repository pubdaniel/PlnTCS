package com.danielqueiroz.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.cogroo.analyzer.Analyzer;
import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.config.Analyzers;
import org.cogroo.text.Chunk;
import org.cogroo.text.Document;
import org.cogroo.text.Sentence;
import org.cogroo.text.SyntacticChunk;
import org.cogroo.text.Token;
import org.cogroo.text.impl.DocumentImpl;

import com.danielqueiroz.constants.Constants;
import com.danielqueiroz.constants.Constants.Entity.Type;
import com.danielqueiroz.model.Entity;


public class Cogroo {
	
	private Analyzer cogroo;
	private Document document;
	 
	
	public Cogroo(String text) throws IOException {
		
	    ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
	    cogroo = factory.createPipe();
	    Document document = new DocumentImpl();
		document.setText(text);
		
	    cogroo.analyze(document);
	    this.document = document;
	}
	
	public List<Sentence> getSentences() {
		return document.getSentences();
	}
	
	public List<Token> getTokens(Sentence sentence) {
		return sentence.getTokens();
	}
	
	public List<Token> getTokens() {
		return document.getSentences().get(0).getTokens();
	}
	
	public List<Entity> getNamesEntitys() {
		List<Token> tokens = document.getSentences().get(0).getTokens();
		List<Entity> namesEntitys= new ArrayList<>();
		
		tokens.forEach(t-> {
			if (t.getPOSTag().toString().equalsIgnoreCase("prop")) {
				Entity entity = new Entity();
				entity.setType(Constants.Entity.Type.PERSON);
				entity.setProbability(t.getPOSTagProb());
				entity.setDescription(t.getLexeme());
				
				namesEntitys.add(entity);
			}
		});
		
		
		return namesEntitys;
	}
	
	public List<Entity> getEntitys() {
		List<Entity> entities = new ArrayList<>();
		List<Token> tokens = new ArrayList<>();
		
		document.getSentences().forEach(s -> {
			s.getTokens().forEach(t -> {
				tokens.add(t);
			});
		});
		
		entities.addAll(getEntityFromTokensFiltredByPosTag("n", Constants.Entity.Type.NOUN, tokens));
		entities.addAll(getEntityFromTokensFiltredByPosTag("num", Constants.Entity.Type.NUMERIC, tokens));
		entities.addAll(getEntityFromTokensFiltredByPosTag("v-fin", Constants.Entity.Type.ACTION, tokens));
		
		System.out.println(tokens);
		
//		getTokens().stream().forEach(n -> {
//			Entity entity = new Entity();
//			entity.setProbability(n.getPOSTagProb());
//			entity.setType(n.getPOSTag());
//			entity.setDescription(n.getLexeme());
//			entities.add(entity);
//			System.out.println(n);
//		});
		
		System.out.println("Entidades Cogroo: " + entities);
		
		return entities;
	}
	
	private List<Entity> getEntityFromTokensFiltredByPosTag(String posTag, Type typeToCompare, List<Token> tokens) {
		List<Entity> entitys = new ArrayList<>();

		tokens.stream().filter(t -> posTag.equalsIgnoreCase(t.getPOSTag())).forEach(n -> {
			Entity entity = new Entity();
			entity.setProbability(n.getPOSTagProb());
			entity.setType(typeToCompare);
			entity.setDescription(n.getLexeme());
			entitys.add(entity);
		});
		return entitys;
	}
	
	public static void main(String[] args) throws IOException {
		Cogroo c = new Cogroo("pesquisa sobre Bolsonaro eleições 2019");
		c.getEntitys();
		
		
	}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
