package com.danielqueiroz.bo;

import java.io.IOException;
import java.time.Period;
import java.util.List;

import org.cogroo.analyzer.ComponentFactory;

import com.danielqueiroz.exception.QueryProcessorException;
import com.danielqueiroz.model.Entity;
import com.danielqueiroz.model.QueryObject;
import com.danielqueiroz.nlp.Cogroo;
import com.danielqueiroz.nlp.OpenNlp;

public class QueryControl {

	private OpenNlp nlp;
	private Cogroo cogroo;
	private String text;
	
	public QueryControl(String text) throws QueryProcessorException {
		this.text = text;
		try {
			nlp = new OpenNlp();
			cogroo = new Cogroo(text);
		} catch (IOException e) {
			throw new QueryProcessorException("Erro ao carregar ferramentas PLN. Erro: " + e.getMessage());
		}
	}
	
	public QueryObject processQuery() throws IOException {

		QueryObject queryObject = new QueryObject();
		queryObject.setLanguage(nlp.detectLanguage(text));
		queryObject.setPersons(getNames());
		queryObject.setPlaces(getPlaces());
		queryObject.setPeriod(getPeriod());

		queryObject.setPhrases(nlp.extractPhrases(text));
		
//		queryObject.setSentences(cogroo.extractDocument(text).getSentences()); // Ver com professor como bildar projeto cogroo no projeto e usar RESOURCES (models)
		
		return queryObject;
	}

	
	

	private List<Entity> getPlaces() {
		
		return null;
	}

	private List<Entity> getNames() {
		return cogroo.getNamesEntitys();
		
	}
	
	private Period getPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
