package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import bean.Article;
import dao.DatabaseConnection;

public class NewArticleService {
	
	public boolean newArticleInsert(Article article, HttpServletRequest req) {
		try {
			Connection con = DatabaseConnection.connect();
			String proc3StoredProcedure = "{ call usp_BlogData_Insert(?,?,?,?,?,?,?) }";
			Connection conn = DatabaseConnection.connect();
			CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
			cs.setString(1,article.getTitle());
			cs.setString(2,article.getContent());
			cs.setString(3,(String)req.getSession().getAttribute("username"));
	       int i = 4;
	       for(String s:article.getGenre())
	       {
	    	       cs.setString(i, article.getGenre()[i-4]);
				   i++;
	       }
	       while(i<7)
	       {
	    	   		cs.setString(i, null);
	    	   		i++;
	       }
			cs.registerOutParameter(7, java.sql.Types.BIT);
			cs.execute();
			if (cs.getBoolean(7) == true) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	public boolean articleUpdate(Article article, HttpServletRequest req) {
		try {
			Connection con = DatabaseConnection.connect();
			String proc3StoredProcedure = "{ call usp_BlogData_Update(?,?,?,?,?,?,?,?) }";
			Connection conn = DatabaseConnection.connect();
			CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
			cs.setString(1,article.getTitle());
			cs.setString(2,article.getContent());
			cs.setString(3,(String)req.getSession().getAttribute("username"));
			cs.setInt(8, article.getAid());
	       int i = 4;
	       for(String s:article.getGenre())
	       {
	    	       cs.setString(i, article.getGenre()[i-4]);
				   i++;
	       }
	       while(i<7)
	       {
	    	   		cs.setString(i, null);
	    	   		i++;
	       }
			cs.registerOutParameter(7, java.sql.Types.BIT);
			cs.execute();
			if (cs.getBoolean(7) == true) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		
	}
}
