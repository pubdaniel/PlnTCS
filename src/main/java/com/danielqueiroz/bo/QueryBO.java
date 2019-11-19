package com.danielqueiroz.bo;

import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintTarget;

import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.text.Token;

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

		return queryObject;
	}

	public String getSqlQuery() throws IOException {
		// filtra tipos de entidades
		QueryObject queryObj = processQuery();
		List<Entity> persons = queryObj.getEntity(Type.PERSON);

		List<Entity> places = queryObj.getEntity(Type.PLACE);
		List<Entity> organizations = queryObj.getEntity(Type.ORGANIZATION);
		List<Entity> nouns = queryObj.getEntity(Type.NOUN);
		List<Entity> events = queryObj.getEntity(Type.EVENT);
		List<Entity> dates = queryObj.getEntity(Type.TIME);

		List<Token> tokens = cogroo.getTokens();
		System.out.println(tokens);

		// who when where
		extractWWW(queryObj, persons, places, dates, tokens);

		this.entitiesCount = persons.size() + places.size() + organizations.size() + nouns.size() + events.size();

		entities.addAll(persons);
		entities.addAll(nouns);
		entities.addAll(organizations);
		entities.addAll(places);
		entities.addAll(dates);
		entities.addAll(events);

		String sql = getSQLString(queryObj);
		return sql;
	}

	private String getSQLString(QueryObject queryObj) {
		List<String> queries = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		sb.append(" select (count(id)/ ##### * 100) as relevance, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from ( ");

		for (Entity e : entities) {
			queries.add(
					" select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like \"%"
							+ e.getDescription().trim() + "%\" group by id ");
		}
		if (queryObj.hasFromWhere()) {
			queries.add(
					" select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where location like \"%"
							+ queryObj.getWhere().trim() + "%\" group by id ");
		}
		if (queryObj.hasFromWho()) {
			queries.add(
					" select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where location like \"%"
							+ queryObj.getFromWho().trim() + "%\" group by id ");
		}
		
		for (int i = 0; i < queries.size(); i++) {
			sb.append(queries.get(i));
			if (i + 1 != queries.size()) {
				sb.append(" union all ");
			}
			
		}

		sb.append(") as result group by id order by relevance desc limit 25;");

		String sql = sb.toString().replace("#####", String.valueOf(queries.size()));
		System.out.println();
		System.out.println(sql);
		return sql ;
	}

	private void extractWWW(QueryObject queryObj, List<Entity> persons, List<Entity> places, List<Entity> dates,
			List<Token> tokens) {
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).getPOSTag().equalsIgnoreCase("prp")) {

				if (tokens.get(i).getLemmas()[0].toString().equalsIgnoreCase("de")) {
					String name = tokens.get(i + 1).getLexeme().trim();
					for (Entity e : persons) {
						if (e.getDescription().toString().trim().equalsIgnoreCase(name)) {
							queryObj.setFromWho(name.trim());
							System.out.println("*Buscando posts do usuário: " + queryObj.getFromWho());
						}
					}
				}
				if (tokens.get(i).getLemmas()[0].toString().equalsIgnoreCase("em")) {
					if (tokens.get(i + 1).getPOSTag().equalsIgnoreCase("prop")) {
						String place = tokens.get(i + 1).getLexeme().trim();
						for (Entity e : places) {
							if (e.getDescription().toString().trim().equalsIgnoreCase(place)) {
								queryObj.setWhere(place.trim());
								System.out.println("*Buscando posts em: " + queryObj.getWhere());
							}
						}
					}
					if (tokens.get(i + 1).getPOSTag().equalsIgnoreCase("num")) {
						for (Entity e : dates) {
							if (e.getDescription().toString().trim().equalsIgnoreCase(tokens.get(i + 1).getLexeme())) {
//								queryObj.setFromWhen();
								System.out.println("*Data é: " + tokens.get(i + 1).getLexeme());
							}
						}
					}
				}
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
		return (sum * 100) / entities.size();

	}

}
