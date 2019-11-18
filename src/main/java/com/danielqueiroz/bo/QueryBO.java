package com.danielqueiroz.bo;

import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintTarget;

import org.cogroo.analyzer.ComponentFactory;

import com.danielqueiroz.constants.Constants.Entity.Type;
import com.danielqueiroz.dao.QueryDAO;
import com.danielqueiroz.exception.QueryProcessorException;
import com.danielqueiroz.model.Entity;
import com.danielqueiroz.model.Post;
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
	private Integer entitiesCount;
	private List<Entity> entities;

	public QueryBO(User user) {
		this.user = user;
	}

	public QueryBO(String text, User user) throws QueryProcessorException {
		this.user = user;
		this.text = text;
		this.entitiesCount = 0;
		entities = new ArrayList<>();
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

		 // Ver com professor como bildar projeto cogroo no projeto e usar RESOURCES (models)
		System.out.println(queryObject);
		return queryObject;
	}

	public String getSqlQuery() throws IOException {
		//filtra tipos de entidades
		QueryObject queryObj = processQuery();
		List<Entity> persons = queryObj.getEntity(Type.PERSON);
		List<Entity> places = queryObj.getEntity(Type.PLACE);
		List<Entity> organizations = queryObj.getEntity(Type.ORGANIZATION);
		List<Entity> nouns = queryObj.getEntity(Type.NOUN);
		List<Entity> events = queryObj.getEntity(Type.EVENT);
		List<Entity> dates = queryObj.getEntity(Type.TIME);

		this.entitiesCount = persons.size() + places.size() + organizations.size() + nouns.size() + events.size();
		
		entities.addAll(persons);
		entities.addAll(nouns);
		entities.addAll(organizations);
		entities.addAll(places);
		entities.addAll(dates);
		entities.addAll(events);
				
		//melhorias
		 // se antes de lugar tiver estiver em pesquisar fazer where post.place={lugar}
		 // se antes de pessoa tiver => chamadas, de, do, da (lema) fazer where posts.username={nome}
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select (count(id)/ " + this.entitiesCount +" * 100) as relevance, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from ( ");
		mountSelectFromEntities(entities, sb);
		sb.append( ") as result group by id order by relevance desc limit 25;");
		
		return sb.toString();
	}

	private void mountSelectFromEntities(List<Entity> entities, StringBuilder sb) {
		for (int i =0; i < entities.size(); i++) {
			sb.append(
					" select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like \"%" + entities.get(i).getDescription()+ "%\" group by id ");
			if (!(i + 1  == entities.size())) {
				sb.append(" union all ");	
			}
		}
	}

	public List<Query> getQueries() {
		QueryDAO dao = new QueryDAO();
		return dao.getQueries();
	}

	
	public void saveQuery(List<Post> posts) {
		Integer count = posts.size();
		Integer sum = 0;
		for (Post p : posts) {
			sum += p.getRelevance();
		}
		
	}

	public Query getQuery(int postsSize) {
		Query query = new Query();
		query.setUser(user);
		query.setMessage(text);
		return query;
	}

	public Double getAcuracy() {
		Double sum = 0D;
		for (Entity e : entities) {
			sum += e.getProbability();
		}
		return (sum *100 ) / entities.size();
		
	}

}













