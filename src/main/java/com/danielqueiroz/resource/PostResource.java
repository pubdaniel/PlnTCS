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
import com.danielqueiroz.bo.UserBO;
import com.danielqueiroz.exception.QueryProcessorException;
import com.danielqueiroz.model.Post;
import com.danielqueiroz.model.User;
import com.mysql.cj.protocol.x.Ok;

@Path("/posts")
public class PostResource {
	
	
	@GET
	@Path("test")
	public Response teste() {
		return  Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPosts(@QueryParam("text") String text, String key) {
		UserBO userBO = new UserBO();
		User user  = userBO.getUser(key);
		
		if (user.getId() == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}		

		PostBO bo = new PostBO(user);
		
		if (text != null) {
			try {
				return Response.ok(bo.getPosts(text)).build();
			} catch (QueryProcessorException | IOException e) {
				return Response.status(Status.BAD_REQUEST).entity(e).build();
			}			
		} 
		return Response.ok(bo.getPosts()).build();
	}
	
	
}
