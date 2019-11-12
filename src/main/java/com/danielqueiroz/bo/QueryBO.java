package com.danielqueiroz.bo;

import java.io.IOException;
import java.time.Period;
import java.util.List;

import javax.validation.ConstraintTarget;

import org.cogroo.analyzer.ComponentFactory;

import com.danielqueiroz.constants.Constants.Entity.Type;
import com.danielqueiroz.dao.QueryDAO;
import com.danielqueiroz.exception.QueryProcessorException;
import com.danielqueiroz.model.Entity;
import com.danielqueiroz.model.Query;
import com.danielqueiroz.model.QueryObject;
import com.danielqueiroz.model.User;
import com.danielqueiroz.nlp.Cogroo;
import com.danielqueiroz.nlp.OpenNlp;

public class QueryBO {

	private OpenNlp nlp;
	private Cogroo cogroo;
	private String text;
	private User user;
	
	public QueryBO(User user ) {
		this.user = user;
	}
	
	public QueryBO(String text, User user) throws QueryProcessorException {
		this.user = user;
		this.text = text;
		try {
			nlp = new OpenNlp(text);
			cogroo = new Cogroo(text);
		} catch (IOException e) {
			throw new QueryProcessorException("Erro ao carregar ferramentas PLN. Erro: " + e.getMessage());
		}
	}
	
	public QueryObject processQuery() throws IOException {

		QueryObject queryObject = new QueryObject();
		queryObject.setLanguage(nlp.detectLanguage());
		queryObject.setText(text);
		
		queryObject.setEntitys(nlp.findNamedEntity());
		
		queryObject.getEntitys().addAll(cogroo.getEntitys());
		
//		queryObject.setSentences(cogroo.extractDocument(text).getSentences()); // Ver com professor como bildar projeto cogroo no projeto e usar RESOURCES (models)
		
		return queryObject;
	}

	

	public String getSqlQuery() throws IOException {
		QueryObject queryObj = processQuery();
		List<Entity> pesons = queryObj.getEntity(Type.PERSON);
		List<Entity> places = queryObj.getEntity(Type.PLACE);
		List<Entity> organizations = queryObj.getEntity(Type.ORGANIZATION);
		List<Entity> nouns = queryObj.getEntity(Type.NOUN);
		List<Entity> dates = queryObj.getEntity(Type.TIME);
		
		QueryDAO dao = new QueryDAO();
		
		Query query = new Query();
		query.setMessage(text);
		query.setUser(user);
		query.setRelevance(65);//TODO ajustar para valor real
		dao.saveQuery(query);
		
		return "select * from post limit 10";
	}

	public List<Query> getQueries(User user) {
		QueryDAO dao = new QueryDAO();
		return dao.getQueries(user);
	}

	
	
}
