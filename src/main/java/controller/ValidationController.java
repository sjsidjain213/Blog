package controller;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import service.ValidationService;
import bean.Message;
import bean.SignUp;

@Path("/validation")
public class ValidationController {
	 
	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/signup")
	public Message userSignUp(SignUp signup)
	{
        return new ValidationService().signupInputValidation(signup);
	}
	 
	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/login")
	public boolean userLogin(SignUp signup,@Context HttpServletRequest req)
	{
	    return new ValidationService().loginInputValidation(signup.getUsername(),signup.getPassword(),req);
	}
	 
	 @Context HttpServletRequest req;
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/session")
	public boolean sessionValidation()
	{
     return new ValidationService().sessionValidator(req);
	}
	 
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/logout")
	public boolean sessionInvalidator()
	{
       req.getSession().setAttribute("username",null);
       return true;
    }
	 
	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/disable/user")
	public boolean disableUser()
	{
     String username = (String)req.getSession().getAttribute("username");
     return new ValidationService().disableUser(username,req);
    }
	
}
