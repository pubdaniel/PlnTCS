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
	
	public List<Token> getNamesTokens() {
		List<Token> tokens = document.getSentences().get(0).getTokens();
		List<Token> namesTokens = new ArrayList<>();
		
		tokens.forEach(t-> {
			if (t.getPOSTag().toString().equalsIgnoreCase("prop")) {
				namesTokens.add(t);
			}
		});
		return namesTokens;
	}
	
	public List<Entity> getNamesEntitys() {
		List<Token> tokens = document.getSentences().get(0).getTokens();
		List<Entity> namesEntitys= new ArrayList<>();
		
		tokens.forEach(t-> {
			if (t.getPOSTag().toString().equalsIgnoreCase("prop")) {
				Entity entity = new Entity();
				entity.setType(Constants.Entity.Type.PERSON);
				entity.setTypeProbability(t.getPOSTagProb());
				entity.setDescription(t.getLexeme());
				
				namesEntitys.add(entity);
			}
		});
		return namesEntitys;
	}
	
	public void getEntitys() {
		List<Token> tokens = document.getSentences().get(0).getTokens();
		tokens.forEach(t-> {
			if (t.getPOSTag().toString().equalsIgnoreCase("prop")) {
				System.out.println("pessoa: " + t.getLexeme());
			
			}
			
			System.out.println(
					"[LEXEME] " + t.getLexeme()+" " + 
					"[FEATURE] " + t.getFeatures() + " " + 
					"[CHUNK] " + t.getChunkTag()+" " + 
					"[POST] " + t.getPOSTag()+" " + 
					"[" + t.getPOSTagProb()+"] " + 
					"[SYNTAG] " + t.getSyntacticTag()+" " + 
					"[LEMAS]: " + Arrays.asList(t.getLemmas()));
		});
		
	}
	
	
	public Document extractDocument(String text) {
	    
	    for (Sentence sentence : document.getSentences()) { // lista de sentenças
	  	

//	  	   Tokens
	  	  for (Token token : sentence.getTokens()) { // lista de tokens
	  		System.out.println(token.getStart()); token.getEnd(); // caracteres onde o token começa e termina
	  		System.out.println(token.getLexeme()); // o texto do token
	  		System.out.println(token.getLemmas()); // um array com os possíveis lemas para o par lexeme+postag
	  		System.out.println(token.getPOSTag()); // classe morfológica de acordo com o contexto
	  		System.out.println(token.getFeatures()); // gênero, número, tempo etc
	  	  }

	  	  // Chunks
	  	  for (Chunk chunk : sentence.getChunks()) { // lista de chunks
	  	    chunk.getStart(); chunk.getEnd(); // índice do token onde o chunk começa e do token onde ele termina
	  	    chunk.getTag(); // the chunk tag
	  	    chunk.getTokens(); // a list with the covered tokens
	  	  }

	  	  // Structure
	  	  for (SyntacticChunk structure : sentence.getSyntacticChunks()) { // lista de SyntacticChunks
	  	    structure.getStart(); structure.getEnd(); // índice do token onde o structure começa e do token onde ele termina
	  	    structure.getTag(); // the structure tag
	  	    structure.getTokens(); // a list with the covered tokens
	  	  }
	  	  
	  	}
	     document.getSentences();
		return null;
	}

	public List<Entity> getNames(String text) {

		return null;
	}
	
	public static void main(String[] args) throws IOException {
		Cogroo c = new Cogroo("Pedro e Ana andam de lindas biciletas azuis ");
		c.getEntitys();
		
		
	}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
