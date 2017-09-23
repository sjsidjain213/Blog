package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import bean.Article;
import bean.Comment;
import service.CommentService;
import service.NewArticleService;
import service.ValidationService;

@Path("/comments")
public class CommentController {
	@Context HttpServletRequest req;
	  
	@GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/{aid}")
	public List getBlogComment(@PathParam("aid")String aid)
	{
		List listofcomments = new CommentService().getComment(aid);
		return listofcomments;
	}
	  
		 @POST
		 @Produces(MediaType.APPLICATION_JSON)
		 @Path("/insert")
		public boolean setBlogComment(Comment comment)
		{
			if(req.getSession().getAttribute("username")!=null)
              return new CommentService().newCommentInsert(comment,req);
			else return false;
		}
	
}