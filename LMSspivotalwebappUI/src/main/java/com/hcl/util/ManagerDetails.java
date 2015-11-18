package com.hcl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ManagerDetails {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static HashMap getManagerList()
	{
		//Class.forName("com.mysql.jdbc.driver");
		
		HashMap Ml = new HashMap();
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_467de3d0d776395";
	
		String dbusername="b73452b3df25cc";
		String dbpassword="7b2fbfb7";
			
		//System.out.println("count of stock : "+dbURL);
		Connection dbCon = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null; 
		//String query ="SELECT * FROM ad_b2664ef2ceb2d68.doc;";
		String query ="SELECT USER_ID,EMAILID FROM ad_467de3d0d776395.USER_DET WHERE ROLE='Manager'";
		try { 
	//		Class.forName("com.mysql.jdbc.Driver");
			//getting database connection to MySQL server 
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		stmt = dbCon.prepareStatement(query);
		rs=stmt.executeQuery(); 
		System.out.println("manager query...: "+query);
		//updating the data into memcache.*************/
		while(rs.next()){//USER_ID,EMAILID
			Ml.put(rs.getString(1), rs.getString(2));	
		}
		}
		catch (Exception ex)
		{
		ex.printStackTrace();
		//result="User registration Failed";
		Ml=null;
		}
		finally{  try {
			stmt.close();
			dbCon.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			Ml=null;
		}
		}

		return Ml; 
}
}
