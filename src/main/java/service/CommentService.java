package service;
import bean.Article;
import bean.Comment;
import dao.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
public class CommentService {

	public List getComment(String aid)
	{
		List<Comment> comment = new ArrayList<Comment>();
		try {
			Connection conn=DatabaseConnection.connect();
			PreparedStatement ps = conn.prepareStatement("SELECT Name,Comment from vComment  where ArticleID = ?");
			ps.setInt(1, Integer.parseInt(aid));
			ResultSet rss = ps.executeQuery();
			while(rss.next())
			{
			comment.add(new Comment(rss.getString("Name"),rss.getString("Comment")));
			}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return comment;
	}
	
	public boolean newCommentInsert(Comment comment,HttpServletRequest req) {
		try {
            int UID = new ValidationService().getUID((String)req.getSession().getAttribute("username"));
			 Connection con = DatabaseConnection.connect();
			String proc3StoredProcedure = "{ call usp_Article_Comment_Insert(?, ?,?,?) }";
			Connection conn = DatabaseConnection.connect();
			CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
			cs.setInt(1, comment.getAid());
			cs.setInt(2, UID);
			cs.setString(3, comment.getComment());
			cs.registerOutParameter(4, java.sql.Types.BIT);
			cs.execute();
			if (cs.getBoolean(4) == true) {
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
