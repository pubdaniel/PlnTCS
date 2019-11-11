package com.danielqueiroz.resource;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.danielqueiroz.bo.PostBO;
import com.danielqueiroz.exception.QueryProcessorException;
import com.danielqueiroz.model.Post;

@Path("/post")
public class PostResource {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPosts(@QueryParam("text") String text) {
		if (text != null) {
			PostBO bo = new PostBO();
			try {
				return Response.ok(bo.getPosts(text)).build();
			} catch (QueryProcessorException | IOException e) {
				return Response.status(Status.BAD_REQUEST).entity(e).build();
			}			
		}
		PostBO bo = new PostBO();
		return Response.ok(bo.getPosts()).build();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostDetails(Post post) {
		
		
		return Response.ok().build();
	}
	
}
