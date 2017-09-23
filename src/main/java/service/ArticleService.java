package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import bean.Article;
import bean.Count;
import bean.SignUp;
import dao.DatabaseConnection;

public class ArticleService {

	public ArticleService() {
		super();
	}

	public List getAllArticles(String operator, String value, String valueTwo) {
		List<Article> article = new ArrayList<Article>();
		try {
			Connection con = DatabaseConnection.connect();
			String proc3StoredProcedure = "{ call articleLogical(?, ?, ?) }";
			Connection conn = DatabaseConnection.connect();
			CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
			cs.setString(1, operator);
			cs.setString(2, value);
			cs.setString(3, valueTwo);
			ResultSet rss = cs.executeQuery();
			while (rss.next()) {
				article.add(new Article(rss.getString("Title"), rss.getString("Username"), 0, rss.getInt("UserID"),
						rss.getInt("Rating")));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return article;
	}

	
	public List getAllBlog(@Context HttpServletRequest req) {
		ArrayList<Article> article = new ArrayList<Article>();
		try {
			Connection conn = DatabaseConnection.connect();
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rss = stmt.executeQuery("SELECT Title, Content, Name, ArticleID, Value,Upvote, Downvote,CreationDate from fn_homepage() ");
			int index=0;
			while (rss.next()) {
				String content = rss.getString("Content");
				int len = rss.getString("Content").length();
				if (len > 300) {
					content = content.substring(0, 300);
				} else {
					content = content.substring(0, len);
				}
				String genre = rss.getString("Value");
				article.add(new Article(index++,rss.getString("Title"), content + "....", rss.getString("Name"),
						genre.substring(0, genre.length()), Integer.parseInt(rss.getString("ArticleID")),
						rss.getInt("Upvote"),rss.getInt("Downvote"),String.valueOf(rss.getDate("CreationDate"))));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return article;
	}

	public List getBlog(String articleid) {
		ArrayList<Article> article = new ArrayList<Article>();
		try {
			Connection conn = DatabaseConnection.connect();
			PreparedStatement ps = conn.prepareStatement("SELECT * from fn_specific_blog(?)");
			ps.setInt(1, Integer.parseInt(articleid));
			ResultSet rss = ps.executeQuery();
			while (rss.next()) 
			{
				article.add(new Article(rss.getString("Title"), rss.getString("Content"), rss.getString("Name"),
				Integer.parseInt(rss.getString("ArticleID")),String.valueOf(rss.getDate("CreationDate")),rss.getString("Value")));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return article;
	}

	public List geAllUserSpecifictBlog(String username) {
		List article = new ArrayList();
		try {

			Connection conn = DatabaseConnection.connect();
			Statement stmt = (Statement) conn.createStatement();
			String query = "select * from fn_article_list_profile(?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ResultSet rss = ps.executeQuery();
			while (rss.next()) {
				article.add(new Article(rss.getString("Title"), rss.getInt("ArticleID"), rss.getInt("Upvote"), rss.getInt("Downvote")));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return article;
	}

	public boolean deleteArticle(Article article, HttpServletRequest req) {
		try {
			Connection con = DatabaseConnection.connect();
			String proc3StoredProcedure = "{ call usp_Article_Delete(?,?) }";
			Connection conn = DatabaseConnection.connect();
			CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
			cs.setInt(1, article.getAid());
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.execute();
			return cs.getBoolean(2);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List filterArticle(String filter) {
		List article = new ArrayList();
		try {
			Connection conn = DatabaseConnection.connect();
			Statement stmt = (Statement) conn.createStatement();
			// ResultSet rss = stmt.executeQuery("SELECT Title, Content, Name,
			// AID, value from func_HomePageFilter() ");
			String query = "select * from func_homepage_filter(?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, filter);
			ResultSet rss = ps.executeQuery();
			int index=0;
			while (rss.next()) {
				String content = rss.getString("Content");
				int len = rss.getString("Content").length();
				if (len > 300) {
					content = content.substring(0, 300);
				} else {
					content = content.substring(0, len);
				}
			String genre = rss.getString("value");
			article.add(new Article(index++,rss.getString("Title"), content + "....", rss.getString("Name"),
						genre.substring(0, genre.length()), Integer.parseInt(rss.getString("ArticleID")),
						rss.getInt("Upvote"),rss.getInt("Downvote"),String.valueOf(rss.getDate("CreationDate"))));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return article;
	}
	
	public boolean upvoteArticle(String articleid,String username,String genrestring)
	{
		try {
			Connection con = DatabaseConnection.connect();
			String proc3StoredProcedure = "{ ? = call usp_article_upvote(?,?,?) }";
			Connection conn = DatabaseConnection.connect();
			CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
			cs.registerOutParameter(1, java.sql.Types.BIT);	
			cs.setInt(2, Integer.parseInt(articleid));
			cs.setString(3, username);
			cs.setString(4, genrestring);
			cs.execute();
			return cs.getBoolean(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean downvoteArticle(String articleid,String username)
	{
		try {
			Connection con = DatabaseConnection.connect();
			String proc3StoredProcedure = "{ ? = call usp_article_downvote(?,?) }";
			Connection conn = DatabaseConnection.connect();
			CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
			cs.registerOutParameter(1, java.sql.Types.BIT);	
			cs.setInt(2, Integer.parseInt(articleid));
			cs.setString(3, username);
			cs.execute();
			return cs.getBoolean(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public List upvoteCount(String articleid){
		List count = new ArrayList();
		try {

			Connection conn = DatabaseConnection.connect();
			Statement stmt = (Statement) conn.createStatement();
			String query = "select * from fn_vote_count(?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(articleid));
			ResultSet rss = ps.executeQuery();
			while (rss.next()) {
				count.add(new Article(rss.getInt("Upvote"),rss.getInt("Downvote")));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public void downvoteArticle()
	{
		
	}
}
