package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Article;
import bean.Message;
import bean.SignUp;
import dao.DatabaseConnection;

public class ValidationService {
	
	public Message signupInputValidation(SignUp signup) {
		Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
		Pattern pname = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		
		Matcher m = p.matcher(signup.getUsername());
		boolean username = m.find();
		Matcher m2 = pname.matcher(signup.getName());
		boolean name = m2.find();
		if (username || name || signup.getName().trim().length() < 2 || signup.getUsername().trim().length() < 6|| signup.getPassword().trim().length() < 6 ) {
			return new Message("invalid input");
		}
		else if(!usernameValidation(signup))
		{
			return new Message("invalid username");
		}
		else {
			return userRegister(signup);
		}
	}

	public boolean loginInputValidation(String username, String password, HttpServletRequest req) {
         if(username.trim().length()>4 || password != null)
         {
        	return login(username,password,req);
         }
	return false;
	}

	public boolean usernameValidation(SignUp signup)
	{
		try{
			String proc3StoredProcedure = "{ call usp_username_validation(?, ?)}";
			Connection conn = DatabaseConnection.connect();
			CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
			cs.setString(1, signup.getUsername());
			cs.registerOutParameter(2, java.sql.Types.BIT);
			cs.execute();
			if (cs.getBoolean(2) == true) {
				return true;
			}
		}catch(Exception e)
		{
			
		}
		return false;
	}
	
	public Message userRegister(SignUp signup) {
		try {
			String proc3StoredProcedure = "{ call userRegistration(?, ?, ?, ?,?, ?,?) }";
			Connection conn = DatabaseConnection.connect();
			CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
			cs.setString(1, signup.getUsername());
			cs.setString(2, signup.getName());
			cs.setString(3, signup.getPassword());
			cs.setString(4, signup.getEmailid());
			cs.setString(5, signup.getStatename());
			cs.setString(6,signup.getCityname());
			cs.registerOutParameter(7, java.sql.Types.BIT);
			cs.execute();
			if (cs.getBoolean(7) == true) {
				return new Message("valid");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Message("invalid");
	}

	public boolean login(String username, String password, HttpServletRequest req) {
		try {
			Connection con = DatabaseConnection.connect();
			String proc3StoredProcedure = "{ call usp_password_verifier(?, ?, ?)}";
			Connection conn = DatabaseConnection.connect();
			CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
			cs.setString(2, username);
			cs.setString(1, password);
			cs.registerOutParameter(3, java.sql.Types.BIT);
			cs.execute();
			if (cs.getBoolean(3) == true) {
				HttpSession session  =  req.getSession(true);
				session.setAttribute("username", username);
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean sessionValidator(HttpServletRequest req)
	{
		if(req.getSession().getAttribute("username")!=null)
			return true;
		else 
			return false;
	}
	
	public int getUID(String username)
	{
		int UID = Integer.MIN_VALUE;
		try {
			Connection conn=DatabaseConnection.connect();
			Statement   stmt = (Statement) conn.createStatement();
           
            String query = "select UserID from [User].PersonalDetail where Username = ?";
            PreparedStatement ps  = conn.prepareStatement(query);
            ps.setString(1, username);
			//ResultSet rss = stmt.executeQuery("SELECT * from vPersonalDetailUID where Username = "+username);
			ResultSet rss  = ps.executeQuery();
            while(rss.next())
			{
			UID = rss.getInt("UserID");
			}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return UID;
	}
	
	public boolean disableUser(String username, HttpServletRequest req)
	{
			try {
				Connection con = DatabaseConnection.connect();
				String proc3StoredProcedure = "{ ? = call usp_user_disable(?)}";
				Connection conn = DatabaseConnection.connect();
				CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
				cs.setString(2, username);
				cs.registerOutParameter(1, java.sql.Types.BIT);	
				cs.execute();
				if (cs.getBoolean(1) == true) {
					req.getSession().setAttribute("username",null);;
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



