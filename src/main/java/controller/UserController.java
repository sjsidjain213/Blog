package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import service.ArticleService;
import service.UserService;

@Path("/users")
public class UserController {
	@Context HttpServletRequest req;
	
   	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/state")
	public List getState()
	{
			return new UserService().getState();
	}
 	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/city/{stateid}")
	public List getCity(@PathParam("stateid")String stateid)
	{        System.out.println(stateid);
			return new UserService().getCity(stateid);
	}
  	
	

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/profile")
	public List getUserProfile()
	{ String username  = (String)req.getSession().getAttribute("username");
          if(req.getSession().getAttribute("username")!=null)
			return new UserService().getUserProfile(username);
          else 
        	return null;
	}
	
}
