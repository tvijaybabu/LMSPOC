package com.hcl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CourseDet {
	private static final Logger logger = LoggerFactory.getLogger(CourseDet.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
public ArrayList getCourseInfo()
{
	//ArrayList kk = new ArrayList();
	ArrayList<String> ab = null;
	/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_55f90d0a45a5495";
	String dbusername="bc7976fa98b14c";
	String dbpassword="5ace9996";*/
	String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_9bac56037f9df2e";
	String dbusername="b4628615f5cc69";
	String dbpassword="8f91d31a";
	Connection dbCon = null;
	Statement stmt = null; 
	ResultSet rs = null;
	int rowcount=0;
	String query ="select c.course_id,c.course_name,c.status from ad_9bac56037f9df2e.Course c order by c.course_name ";
	//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
	logger.info("search query.. "+query);
	try { 
	//getting database connection to MySQL server 
	dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
	//System.out.println("count of stock : "+dbCon);
	//getting PreparedStatment to execute query 
	stmt = dbCon.createStatement();
	/*stmt.setString(1, username);
	stmt.setString(2, password);
	stmt.setString(3, email);
	stmt.executeUpdate();*/
	rs	=	stmt.executeQuery(query);
	ab = new ArrayList<String>();
	String data="";
	while(rs.next()){
		
		logger.info("CATID.. "+rs.getString("status"));
		logger.info("CATID.. "+rs.getString("course_id"));
		logger.info("CATID.. "+rs.getString("course_name"));
		/*CourseBean cb	= new CourseBean();
		cb.setCat_id(rs.getString("CATALOG_ID"));
		cb.setCat_name(rs.getString("CATALOG_NAME"));
		cb.setCourse_id(rs.getString("course_id"));
		cb.setCourse_name(rs.getString("course_name"));*/
		data=rs.getString("course_id")+"#"+rs.getString("course_name")+"#"+rs.getString("status");
		rowcount++;
		//catmap.put(rs.getInt(1), rs.getString(2));
		ab.add(data);
	}
			if(rowcount>0)
			{
				
			}
			else{
				//result= "Category does not exists..";
				ab=null;
			}
	}
		//result= "User reistered Successfully..."; 
		
		catch (SQLException ex)
		{ 
		 ex.printStackTrace();
		////logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
		 ab=null;
		 //result=ab;
		}
		catch (Exception ex)
		{ 
		ex.printStackTrace();
		ab=null;
		 
		}
		finally{ try {
			stmt.close();
			dbCon.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		}
return ab;
}
}
