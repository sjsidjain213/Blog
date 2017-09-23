package dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.Article;

public class DatabaseConnection {
//	public static void main(String args[]) throws ClassNotFoundException, SQLException{
//		connect();
//	}
	public static Connection connect() throws SQLException, ClassNotFoundException{
	    //	String connectionUrl = "jdbc:sqlserver://localhost:1433;" +  
	      //          "databaseName=DBAAssignments;user=sa;password=ITT@123456";  
	    	String connectionUrl = "jdbc:sqlserver://localhost:1433;" +  
	               "databaseName=blog;user=sa;password=ITT@123456";  

	             // Declare the JDBC objects.  
	             Connection con = null;  
	             Statement stmt = null;  
	             ResultSet rs = null;  

	             try {  
	                // Establish the connection.  
	                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
	                con = DriverManager.getConnection(connectionUrl);  
	             }  

	             // Handle any errors that may have occurred.  
	             catch (Exception e) {  
	                e.printStackTrace();  
	             }  
	             finally {  
	             }  
return con;
	}
}
