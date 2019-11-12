package com.danielqueiroz.resource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.model.ParamQualifier;

import com.danielqueiroz.bo.UserBO;
import com.danielqueiroz.constants.Constants;
import com.danielqueiroz.model.User;



@Path("/user")
public class UserService {

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("key") String key) {
		UserBO bo = new UserBO();
		User user = bo.getUser(key);
		
		return Response.ok(user).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLogin(@FormParam("username") String username, @FormParam("password") String password) {
		
		UserBO bo = new UserBO();
		String authkey = bo.getKey(username, password);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", authkey);
		return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();
	}
	

}
