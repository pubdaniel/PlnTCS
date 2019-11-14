package com.danielqueiroz.bo;

import java.io.IOException;
import java.util.List;

import com.danielqueiroz.dao.PostDAO;
import com.danielqueiroz.dao.QueryDAO;
import com.danielqueiroz.exception.QueryProcessorException;
import com.danielqueiroz.model.Post;
import com.danielqueiroz.model.Query;
import com.danielqueiroz.model.QueryObject;
import com.danielqueiroz.model.User;

public class PostBO {

	User user;
	
	public PostBO(User user) {
		this.user = user;
	}
	
	public List<Post> getPosts() {
		PostDAO dao = new PostDAO();
		return dao.getPosts();
	}
	
	public List<Post> getPosts(String text) throws QueryProcessorException, IOException {
		//TODO  implementar busca com parametr => mensagem
		QueryBO queryBO = new QueryBO(text, user);
		String querySql = queryBO.getSqlQuery();
		
		PostDAO dao = new PostDAO();
		List<Post> posts = dao.getPosts(querySql);
		
		Double acuracy = queryBO.getAcuracy();
		Query query = queryBO.getQuery(posts.size());
		query.setRelevance(acuracy.intValue());
		
		QueryDAO queryDao = new QueryDAO();
		queryDao.saveQuery(query);
		
//		queryBO.saveQuery(posts);
		
		return posts;

	}
	
}
