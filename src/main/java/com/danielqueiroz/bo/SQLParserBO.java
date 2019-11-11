package com.danielqueiroz.bo;

import com.danielqueiroz.model.QueryObject;

public class SQLParserBO {

	QueryObject queryObject;
	
	public SQLParserBO(QueryObject queryObj) {
		this.queryObject = queryObj;
	}

	public String makeSqlQuery() {

		return "select * from posts";
	}
	
}
