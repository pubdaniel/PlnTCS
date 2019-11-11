package com.danielqueiroz.bo;

import java.io.IOException;
import java.util.List;

import com.danielqueiroz.dao.PostDAO;
import com.danielqueiroz.exception.QueryProcessorException;
import com.danielqueiroz.model.Post;
import com.danielqueiroz.model.QueryObject;

public class PostBO {

	public List<Post> getPosts() {
		
		PostDAO dao = new PostDAO();
		return dao.getPosts();
	}
	
	public List<Post> getPosts(String text) throws QueryProcessorException, IOException {
		
		QueryBO queryBO = new QueryBO(text);
		String sql = queryBO.getSqlQuery();
		
		PostDAO dao = new PostDAO();
		return dao.getPosts(sql);		

	}
	
}
