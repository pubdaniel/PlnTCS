package com.danielqueiroz.resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.danielqueiroz.bo.QueryControl;
import com.danielqueiroz.exception.QueryProcessorException;
import com.danielqueiroz.model.QueryObject;


@Path("/query")
public class Query {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendQuery(@QueryParam("text") String text) throws IOException, QueryProcessorException {
	   
		//ler o arquivo saida.txt (aguardar o mesmo existir)
		QueryControl control = new QueryControl(text);
		QueryObject queryObject;
		try {
			queryObject = control.processQuery(); 
			return Response.ok(queryObject).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		
	}
	
	
}
