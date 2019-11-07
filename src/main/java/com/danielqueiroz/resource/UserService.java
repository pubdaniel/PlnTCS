package com.danielqueiroz.resource;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.model.ParamQualifier;

import com.danielqueiroz.constants.Constants;
import com.danielqueiroz.model.Permission;
import com.danielqueiroz.model.User;



@Path("/user")
public class UserService {


	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveUser(//
			@FormParam("name") String name, //
			@FormParam("nickname") String nickname, //
			@FormParam("email") String email, //
			@FormParam("phone") String phone, //
			@FormParam("password") String password) {

		User user = new User();
		user.setId(1L);
		user.setName(name);
		user.setNickname(nickname);
		user.setEmail(email);
		user.setPassword(password);
		user.setPhone(phone);
		user.setPermission(Permission.newAdminPermission());
		return Response.ok(user).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLogin(@FormParam("username") String username, @FormParam("password") String password) {
		User user = new User();
		user.setId(1L);
		user.setName(username);
		user.setNickname(username);
		user.setEmail(username + "@gmail.com");
		user.setPassword(password);
		user.setPhone("48 99999999");
		
		String path = new File("teste.txt").getAbsolutePath();


		Permission permission = new Permission();
		permission.setId(1L);
		permission.setDescription(Constants.User.Level.ADMIN.description());
		permission.setLevel(Constants.User.Level.ADMIN.level());

		user.setPermission(permission);

		return Response.ok(user).build();
	}
	

}
