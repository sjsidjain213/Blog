package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Location;
import bean.User;
import dao.DatabaseConnection;

public class UserService {

	public List getUserProfile(String username)
	{
		List profile = new ArrayList();
		try {
			Connection conn=DatabaseConnection.connect();
			Statement   stmt = (Statement) conn.createStatement();
            String query = "SELECT Name,Username,EmailID,Tags from fn_user_profile(?)";
            PreparedStatement ps  = conn.prepareStatement(query);
            ps.setString(1, username);
			ResultSet rss  = ps.executeQuery();
			while(rss.next())
			{
			profile.add(new User(rss.getString("Name"),rss.getString("EmailID"),rss.getString("Username"),rss.getString("Tags")));
			}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return profile;
	}
	
	public List getState(){
		List location = new ArrayList();
		try {

			Connection conn=DatabaseConnection.connect();
			Statement   stmt = (Statement) conn.createStatement();
           
            String query = "select StateName,StateID from [Location].State";
            PreparedStatement ps  = conn.prepareStatement(query);
        	//ResultSet rss = stmt.executeQuery("SELECT * from vPersonalDetailUID where Username = "+username);
			ResultSet rss  = ps.executeQuery();
			while(rss.next())
			{
						location.add(new Location(rss.getString("StateName"),rss.getInt("StateID")));
			}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return location;
	}
	
	public List getCity(String stateid){
		List location = new ArrayList();
		try {

			Connection conn=DatabaseConnection.connect();
			Statement   stmt = (Statement) conn.createStatement();
           
            String query = "select CityName,CityID from [Location].City where StateID = ?";
            PreparedStatement ps  = conn.prepareStatement(query);
            ps.setString(1, stateid);
        	ResultSet rss  = ps.executeQuery();
			while(rss.next())
			{
				location.add(new Location(rss.getInt("CityID"),rss.getString("CityName")));
			}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return location;
	}
}
