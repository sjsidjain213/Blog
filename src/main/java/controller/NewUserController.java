package controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import service.ArticleService;
import service.NewUserService;
import service.UserService;

@Path("/newuser")
public class NewUserController {
	@GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/{username}/{name}")
	public String getNewUser(@PathParam("username")String username,@PathParam("name")String name)
	{
		return new NewUserService().setNewUser(username, name);
	}
	
}
