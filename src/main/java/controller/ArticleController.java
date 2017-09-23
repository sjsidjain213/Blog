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
import service.ArticleService;
import service.NewArticleService;
import service.ValidationService;

@Path("/articles")
public class ArticleController {
	@Context HttpServletRequest req;
	  
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/home")
	 public List getAllBlog(@Context HttpServletRequest req){
		 		return	new ArticleService().getAllBlog(req);
		}
	 
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/specific/{aid}")
	 public List getBlog(@PathParam("aid")String aid){
				return	new ArticleService().getBlog(aid);
		}
	 
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/user/specific")
	 public List getAllUserSpecificBlog(){
		 String username  = (String)req.getSession().getAttribute("username");
		if(username !=null)		
		 return	new ArticleService().geAllUserSpecifictBlog(username);
		else
			return null;
	 }
	 
	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/new/article")
	 public boolean setNewBlog(Article article){
		 if(new ValidationService().sessionValidator(req))
		      return new NewArticleService().newArticleInsert(article,req);
		 else 
			 return false;
	 }
	 
	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/update/article")
	 public boolean updateBlog(Article article){
		 if(new ValidationService().sessionValidator(req))
		      return new NewArticleService().articleUpdate(article,req);
		 else 
			 return false;
	 }
	 
	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/delete")
	 public boolean deleteArticle(Article article){
		 if(new ValidationService().sessionValidator(req))
		      return new ArticleService().deleteArticle(article,req);
		 else 
			 return false;
	 }

	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/filter/{filter}")
	 public List deleteArticle(@PathParam("filter")String filter){
		      return new ArticleService().filterArticle(filter);
	}
	 
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/upvote/{articleid}/{genrestring}")
	 public boolean upvoteArticle(@PathParam("articleid")String articleid,@PathParam("genrestring")String genrestring){
		 if(new ValidationService().sessionValidator(req))
			return new ArticleService().upvoteArticle(articleid,(String)req.getSession().getAttribute("username"),genrestring);
		 else 
			return false;
	 }
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/downvote/{articleid}")
	 public boolean downvoteArticle(@PathParam("articleid")String articleid){
		 if(new ValidationService().sessionValidator(req))
			return new ArticleService().downvoteArticle(articleid,(String)req.getSession().getAttribute("username"));
		 else 
			return false;
	 }
	 
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/votecount/{articleid}")
	 public List upvoteCount(@PathParam("articleid")String articleid){
			return new ArticleService().upvoteCount(articleid);
	}
}