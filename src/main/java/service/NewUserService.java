package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.User;
import dao.DatabaseConnection;

public class NewUserService {
	public String setNewUser(String username,String name)
	{
	try {
	Connection con=	DatabaseConnection.connect();
    String proc3StoredProcedure = "{ call newUserInsert(?,?)}";
	Connection conn=DatabaseConnection.connect();
	CallableStatement cs = conn.prepareCall(proc3StoredProcedure);
	cs.setString(1,username);
	cs.setString(2,name);
	cs.execute();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "created";
	}
}
