package com.danielqueiroz.resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.danielqueiroz.bo.QueryBO;
import com.danielqueiroz.dao.PostDAO;
import com.danielqueiroz.exception.QueryProcessorException;
import com.danielqueiroz.model.Query;
import com.danielqueiroz.model.QueryObject;


@Path("/")
public class QueryResource {
	
	
	//buscar lista de pesquisas realizadas
	@GET
	@Path("/queries")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQueryes() {
		List<Query> queries = new ArrayList<>();
		return null;
	}
	
//	@GET
//	@Path("/query")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response sendQuery(@QueryParam("text") String text) throws IOException, QueryProcessorException {
//	   
//		QueryBO control = new QueryBO(text);
//		
//		try {
//			QueryObject queryObject = control.processQuery();
//			
//			return Response.ok(queryObject).build(); // listResults
//		} catch (IOException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
//		}
//		
//	}
	
	
}
