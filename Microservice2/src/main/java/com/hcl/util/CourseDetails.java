package com.hcl.util;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.hcl.learning.HomeController;

public class CourseDetails {
	private static final Logger logger = LoggerFactory.getLogger(CourseDetails.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static String UserCourseApproval(int userid,int courseid,String status,String reason)
	{
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_9344a537232b26c";
		String dbusername="bea1dd1789dafa";
		String dbpassword="a43b655f";
		*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_18c6b257c8ae39b";
		String dbusername="b55f20c448bcf4";
		String dbpassword="1b47012b";
	
		String result="";
		Connection dbCon = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		//int catalogid;
		//int courseid;
		try{
			//for(int i=0;i<userCourses.length;i++)
			{
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
				Date date = new Date();
				//logger.info("Welcome  SaveCourse**************userCourses***"+userCourses[i]);
				//catalogid=Integer.parseInt(userCourses[i].split("#")[0]);
				//courseid=Integer.parseInt(userCourses[i].split("#")[1]);
			String query ="UPDATE ad_18c6b257c8ae39b.user_catalog SET RM_STATUS='"+status+"',REASON='"+reason+"' WHERE COURSE_ID="+courseid+" AND USER_ID="+userid;
			//getting database connection to MySQL server 
			dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
			System.out.println("update query for course finish : "+query);
			//getting PreparedStatment to execute query 
			stmt = dbCon.prepareStatement(query);
			//stmt.setInt(1, catalogid);
			//stmt.setInt(2, courseid);
			//stmt.setInt(3, userid);
			stmt.executeUpdate();
			}
			//updating the data into memcache.*************/
			//CacheUpdate cu = new CacheUpdate(); 
//			cu.updateUserListCache(username.toLowerCase(),password,email);
			
			//updating the data into memcache.***********/
			//SimpleMail sm	=	new SimpleMail();
			//sm.sendMail(email);
			result= "User Course Approval status changed Successfully...";
			}
			catch (SQLException ex)
			{ 
			 ex.printStackTrace();
			////logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
			 result="User Course Approval status updation Failed";
			}
			catch (Exception ex)
			{ 
			ex.printStackTrace();
			result="User Course Approval status updation Failed";
			}
			finally{ //close connection ,stmt and resultset here } 
				try {
					stmt.close();
					dbCon.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

		return result;
	}


	public static String UserCourseFinish(int userid,int courseid)
	{
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_9344a537232b26c";
		String dbusername="bea1dd1789dafa";
		String dbpassword="a43b655f";
		*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_18c6b257c8ae39b";
		String dbusername="b55f20c448bcf4";
		String dbpassword="1b47012b";
	
		String result="";
		Connection dbCon = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		//int catalogid;
		//int courseid;
		try{
			//for(int i=0;i<userCourses.length;i++)
			{
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
				Date date = new Date();
				//logger.info("Welcome  SaveCourse**************userCourses***"+userCourses[i]);
				//catalogid=Integer.parseInt(userCourses[i].split("#")[0]);
				//courseid=Integer.parseInt(userCourses[i].split("#")[1]);
			String query ="UPDATE ad_18c6b257c8ae39b.user_catalog SET COURSE_STATUS='COMPLETED' ,COMPLETION_DATE='"+dateFormat.format(date)+"' WHERE COURSE_ID="+courseid+" AND USER_ID="+userid;
			//getting database connection to MySQL server 
			dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
			System.out.println("update query for course finish : "+query);
			//getting PreparedStatment to execute query 
			stmt = dbCon.prepareStatement(query);
			//stmt.setInt(1, catalogid);
			//stmt.setInt(2, courseid);
			//stmt.setInt(3, userid);
			stmt.executeUpdate();
			}
			//updating the data into memcache.*************/
			//CacheUpdate cu = new CacheUpdate(); 
//			cu.updateUserListCache(username.toLowerCase(),password,email);
			
			//updating the data into memcache.***********/
			//SimpleMail sm	=	new SimpleMail();
			//sm.sendMail(email);
			result= "Course Finish status updated Successfully...";
			}
			catch (SQLException ex)
			{ 
			 ex.printStackTrace();
			////logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
			 result="Course Finish status updation Failed";
			}
			catch (Exception ex)
			{ 
			ex.printStackTrace();
			result="Course Finish status updation Failed";
			}
			finally{ //close connection ,stmt and resultset here } 
				try {
					stmt.close();
					dbCon.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

		return result;
	}

	public static String AddCoursetoUser(int userid,int catalogid,int courseid,int RepMgrid)
	{
	/*	String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_9344a537232b26c";
		String dbusername="bea1dd1789dafa";
		String dbpassword="a43b655f";
	*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_18c6b257c8ae39b";
		String dbusername="b55f20c448bcf4";
		String dbpassword="1b47012b";
	
		String result="";
		Connection dbCon = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		//int catalogid;
		//int courseid;
		try{
			//for(int i=0;i<userCourses.length;i++)
			{
				//logger.info("Welcome  SaveCourse**************userCourses***"+userCourses[i]);
				//catalogid=Integer.parseInt(userCourses[i].split("#")[0]);
				//courseid=Integer.parseInt(userCourses[i].split("#")[1]);
			String query ="INSERT INTO ad_18c6b257c8ae39b.user_catalog(CATALOG_ID,COURSE_ID,USER_ID,RM_ID) VALUES(?,?,?,?)";
			//getting database connection to MySQL server 
			dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
			System.out.println("count of stock : "+dbCon);
			//getting PreparedStatment to execute query 
			stmt = dbCon.prepareStatement(query);
			stmt.setInt(1, catalogid);
			stmt.setInt(2, courseid);
			stmt.setInt(3, userid);
			stmt.setInt(4, RepMgrid);
			
			stmt.executeUpdate();
			}
			//updating the data into memcache.*************/
			//CacheUpdate cu = new CacheUpdate(); 
//			cu.updateUserListCache(username.toLowerCase(),password,email);
			
			//updating the data into memcache.***********/
			//SimpleMail sm	=	new SimpleMail();
			//sm.sendMail(email);
			result= "Course(s) Added Successfully...";
			}
			catch (SQLException ex)
			{ 
			 ex.printStackTrace();
			////logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
			 result="Course addition Failed";
			}
			catch (Exception ex)
			{ 
			ex.printStackTrace();
			result="Course addition Failed";
			}
			finally{ //close connection ,stmt and resultset here } 
				try {
					stmt.close();
					dbCon.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
			

		return result;
	}
	public static ArrayList<UserCourse> getUserCourse(int userid)
	{
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
		String dbusername="b71a8affc1f50f";
		String dbpassword="ac84a196";
		String result="";
		Connection dbCon = null;
		Statement stmt = null; 
		ResultSet rs = null;
		ArrayList<UserCourse> UCList= new ArrayList<UserCourse>();
		String query ="SELECT CT.CATALOG_NAME,CO.COURSE_NAME,UC.CATALOG_ID,UC.COURSE_ID,UC.COMPLETION_DATE,UC.COURSE_STATUS,UC.COURSE_TYPE,UC.USER_ID FROM ad_b2664ef2ceb2d68.USER_CATALOG UC,ad_b2664ef2ceb2d68.CATALOG CT,ad_b2664ef2ceb2d68.COURSE CO WHERE CT.CATALOG_ID=UC.CATALOG_ID AND CO.COURSE_ID =UC.COURSE_ID AND UC.USER_ID="+userid;
		//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
		//logger.info("search query.. "+query);
	try{
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		//System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		stmt = dbCon.createStatement();
		rs	=	stmt.executeQuery(query);
		while(rs.next())
		{
			UserCourse UC = new UserCourse(); 
			//logger.info("CATNAME.. "+rs.getString("CATALOG_NAME"));
			//logger.info("CATID.. "+rs.getString("CATALOG_ID"));
			//logger.info("course_id.. "+rs.getString("course_id"));
			//logger.info("course_name.. "+rs.getString("course_name"));
			//logger.info("completion_date.. "+rs.getString("completion_date"));
			//logger.info("status.. "+rs.getString("COURSE_STATUS"));
			UC.setCat_id(rs.getString("CATALOG_ID"));
			UC.setCat_name(rs.getString("CATALOG_NAME"));
			UC.setCourse_id(rs.getString("course_id"));
			UC.setCourse_name(rs.getString("course_name"));
			if(rs.getString("completion_date")==null || rs.getString("completion_date")=="")
				UC.setCompletiondate("");
			else
				UC.setCompletiondate(rs.getString("completion_date"));
			
			UC.setStatus(rs.getString("COURSE_STATUS"));
			UCList.add(UC);
		}
    }
	catch (SQLException ex)
	{ 
	 ex.printStackTrace();
	////logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
	 //ab=null;
	 //result=ab;
	}
	catch (Exception ex)
	{ 
	ex.printStackTrace();
	///rab=null;
	 
	}
	finally{ try {
		stmt.close();
		dbCon.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	

}


		return UCList;
	}
	public static ArrayList<String> getCourseList(int cat_id,String coursename)
	{
		ArrayList<String> ab = null;
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_55f90d0a45a5495";
		String dbusername="bc7976fa98b14c";
		String dbpassword="5ace9996";
		*//*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
		String dbusername="b71a8affc1f50f";
		String dbpassword="ac84a196";*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_9bac56037f9df2e";
		String dbusername="b4628615f5cc69";
		String dbpassword="8f91d31a";
		String result="";
		String searchcond="";
	//S eystem.out.println("count of stock : "+dbURL);
		Connection dbCon = null;
		Statement stmt = null; 
		ResultSet rs = null;
		int rowcount=0;
		int x=0;
		int y=0;
		/*logger.info("getCourseList search searchcond.cat_id. "+cat_id);
				logger.info(" getCourseListsearch searchcond.coursename... "+coursename);*/
				
		System.out.println("getCourseList search searchcond.cat_id. "+cat_id);
		System.out.println(" getCourseListsearch searchcond.coursename... "+coursename.length());
		if(cat_id!=-1){
			searchcond=" WHERE c.CATALOG_ID="+cat_id;
			x=1;
		}
		if(coursename!=null && (!coursename.equalsIgnoreCase("null")) && coursename!=""){
			if(x==1)
			searchcond=searchcond+" AND c.COURSE_NAME LIKE'%"+coursename+"%'";
			else
				searchcond=" WHERE c.COURSE_NAME LIKE '%"+coursename+"%'";
		}
		
		//String query ="select c.catalog_id,c.course_id,c.course_name,ct.Catalog_name from ad_b2664ef2ceb2d68.Catalog ct,ad_b2664ef2ceb2d68.course c where c.CATALOG_ID=1042 and c.course_name like '%jav%' and c.catalog_id=ct.catalog_id;";
		String query ="select c.catalog_id,c.course_id,c.course_name,ct.Catalog_name from ad_9bac56037f9df2e.Catalog ct,ad_9bac56037f9df2e.course c";//;+" and c.catalog_id=ct.catalog_id";
		//logger.info("search searchcond.. "+searchcond);
		System.out.println("search searchcond.. "+searchcond);
		if(searchcond.trim()=="")
			query=query+" WHERE c.catalog_id=ct.catalog_id AND c.status='ACTIVE'";
		else
			query=query+searchcond+" AND c.catalog_id=ct.catalog_id AND c.status='ACTIVE'";
		//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
		//logger.info("search query.. "+query);
		System.out.println("search query.. "+query);
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
			logger.info("CATNAME.. "+rs.getString("CATALOG_NAME"));
			logger.info("CATID.. "+rs.getString("CATALOG_ID"));
			logger.info("CATID.. "+rs.getString("course_id"));
			logger.info("CATID.. "+rs.getString("course_name"));
			/*CourseBean cb	= new CourseBean();
			cb.setCat_id(rs.getString("CATALOG_ID"));
			cb.setCat_name(rs.getString("CATALOG_NAME"));
			cb.setCourse_id(rs.getString("course_id"));
			cb.setCourse_name(rs.getString("course_name"));*/
			data=rs.getString("CATALOG_ID")+"#"+rs.getString("CATALOG_NAME")+"#"+rs.getString("course_id")+"#"+rs.getString("course_name");
			rowcount++;
			//catmap.put(rs.getInt(1), rs.getString(2));
			ab.add(data);
			
		}
		//SimpleMail sm	=	new SimpleMail();
		//sm.sendMail(email);
		if(rowcount>0)
		{
			
		}
		else{
			//result= "Category does not exists..";
			ab=null;
		}
	
		//result= "User reistered Successfully..."; 
		}
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
	public static ArrayList<String> getPendingCourseList(int RepMgr_id)
	{
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_9344a537232b26c";
		String dbusername="bea1dd1789dafa";
		String dbpassword="a43b655f";
		*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_18c6b257c8ae39b";
		String dbusername="b55f20c448bcf4";
		String dbpassword="1b47012b";
	
		String result="";
		ArrayList ll=null;
		Connection dbCon = null;
		Statement stmt = null; 
		ResultSet rs = null;
		//ArrayList<UserCourse> UCList= new ArrayList<UserCourse>();
		String query ="SELECT UC.CATALOG_ID,UC.COURSE_ID,UC.USER_ID,UC.RM_STATUS FROM ad_18c6b257c8ae39b.USER_CATALOG UC WHERE UC.RM_ID="+RepMgr_id+" AND UC.RM_STATUS!='Approved'";
		//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
		logger.info("search query user coursse list.. "+query);
		System.out.println("search query user coursse list.. "+query);
	try{
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		//System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		stmt = dbCon.createStatement();
		rs	=	stmt.executeQuery(query);
		ll= new ArrayList();
		String catname="";
		String coursename="";
		String rm_status="";
		String Userid="";

		HashMap<Integer, String> catmap=new HashMap<Integer, String>();
		HashMap<Integer, String> coursemap=new HashMap<Integer, String>();
		catmap		=	getCatalogNames();
		coursemap	=	getCourseNames();
		int catid=0;
		int courseid=0;
		//String catname="";
		//String coursename="";
		String completiondate="";
		String status="";
		String coursetype="";
		int count=0;
		System.out.println("catmap.."+catmap);
		System.out.println("coursemap.."+coursemap);
		while(rs.next())
		{count++;
			catid			= 	rs.getInt("CATALOG_ID");
			courseid		= 	rs.getInt("COURSE_ID");
			catname			=	catmap.get(catid);
			coursename		=	coursemap.get(courseid);
			rm_status		=	rs.getString("RM_STATUS");
			Userid			=rs.getString("UC.USER_ID");
			result			=Userid+"#"+catid+"#"+catname+"#"+courseid+"#"+coursename+"#"+rm_status; 
			logger.info("course details after concatenating.. "+result);
			ll.add(result);
			
		}
		if(count==0){
			ll=null;
		}
    }
	catch (SQLException ex)
	{ 
	 ex.printStackTrace();
	////logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
	 //ab=null;
	 //result=ab;
		ll=null;
	}
	catch (Exception ex)
	{ 
	ex.printStackTrace();
	///rab=null;
	ll=null;
	}
	finally{ try {
		stmt.close();
		dbCon.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		ll=null;
	} 
	
	

}
	System.out.println("pending list before return ...."+ll);
return ll;
}
	public static HashMap<Integer, String> getCourseNames()
	{
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_55f90d0a45a5495";
		String dbusername="bc7976fa98b14c";
		String dbpassword="5ace9996";*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_9bac56037f9df2e";
		String dbusername="b4628615f5cc69";
		String dbpassword="8f91d31a";
		
		String result="";
		Connection dbCon = null;
		Statement stmt = null; 
		ResultSet rs = null;
		ArrayList<UserCourse> UCList= new ArrayList<UserCourse>();
		HashMap<Integer, String> coursemap=new HashMap<Integer, String>();
		String query ="SELECT COURSE_ID,COURSE_NAME FROM ad_9bac56037f9df2e.COURSE";
		//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
		//logger.info("search query.. "+query);
	try{
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		//System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		stmt = dbCon.createStatement();
		rs	=	stmt.executeQuery(query);
		while(rs.next())
		{
			//result=rs.getString("COURSE_NAME");
			coursemap.put(rs.getInt("COURSE_ID"), rs.getString("COURSE_NAME"));
			logger.info("COURENAME.. "+coursemap);
			
		}
    }
	catch (SQLException ex)
	{ 
	 ex.printStackTrace();
	////logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
	 //ab=null;
	 //result=ab;
	}
	catch (Exception ex)
	{ 
	ex.printStackTrace();
	///rab=null;
	 
	}
	finally{ try {
		stmt.close();
		dbCon.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	

}
		return coursemap;
	}
	public static HashMap<Integer, String> getCatalogNames()
	{
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_55f90d0a45a5495";
		String dbusername="bc7976fa98b14c";
		String dbpassword="5ace9996";*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_9bac56037f9df2e";
		String dbusername="b4628615f5cc69";
		String dbpassword="8f91d31a";
		
		String result="";
		Connection dbCon = null;
		Statement stmt = null; 
		ResultSet rs = null;
		HashMap<Integer, String> catmap=new HashMap<Integer, String>();
		//ArrayList<UserCourse> UCList= new ArrayList<UserCourse>();
		String query ="SELECT CATALOG_ID,CATALOG_NAME FROM ad_9bac56037f9df2e.CATALOG";
		//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
		//logger.info("search query.. "+query);
	try{
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		//System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		stmt = dbCon.createStatement();
		rs	=	stmt.executeQuery(query);
		
		while(rs.next())
		{
			 
			catmap.put(rs.getInt("CATALOG_ID"), rs.getString("CATALOG_NAME"));
			logger.info("COURSENAME.. "+catmap);
			
			
		}
    }
	catch (SQLException ex)
	{ 
	 ex.printStackTrace();
	////logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
	 //ab=null;
	 //result=ab;
	}
	catch (Exception ex)
	{ 
	ex.printStackTrace();
	///rab=null;
	 
	}
	finally{ try {
		stmt.close();
		dbCon.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	

		}


		return catmap;
	}
	public static ArrayList getUserCourseList(int user_id)
	{
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_9344a537232b26c";
		String dbusername="bea1dd1789dafa";
		String dbpassword="a43b655f";
		*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_18c6b257c8ae39b";
		String dbusername="b55f20c448bcf4";
		String dbpassword="1b47012b";
	
		String result="";
		ArrayList ll=null;
		Connection dbCon = null;
		Statement stmt = null; 
		ResultSet rs = null;
		//ArrayList<UserCourse> UCList= new ArrayList<UserCourse>();
		String query ="SELECT UC.CATALOG_ID,UC.COURSE_ID,UC.COMPLETION_DATE,UC.COURSE_STATUS,UC.COURSE_TYPE,UC.USER_ID,UC.RM_STATUS,UC.REASON FROM ad_18c6b257c8ae39b.USER_CATALOG UC WHERE UC.USER_ID="+user_id;
		//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
		logger.info("search query user coursse list.. "+query);
	try{
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		//System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		stmt = dbCon.createStatement();
		rs	=	stmt.executeQuery(query);
		ll= new ArrayList();
		String catname="";
		String coursename="";
		String rm_status="";
		String rm_reason="";

		HashMap<Integer, String> catmap=new HashMap<Integer, String>();
		HashMap<Integer, String> coursemap=new HashMap<Integer, String>();
		catmap		=	getCatalogNames();
		coursemap	=	getCourseNames();
		int catid=0;
		int courseid=0;
		//String catname="";
		//String coursename="";
		String completiondate="";
		String status="";
		String coursetype="";
		
		System.out.println("catmap.."+catmap);
		System.out.println("coursemap.."+coursemap);
		while(rs.next())
		{
			catid			= 	rs.getInt("CATALOG_ID");
			courseid		= 	rs.getInt("COURSE_ID");
			catname			=	catmap.get(catid);
			coursename		=	coursemap.get(courseid);
			completiondate	=	rs.getString("COMPLETION_DATE");
			status			=	rs.getString("COURSE_STATUS");
			coursetype		=	rs.getString("COURSE_TYPE");
			rm_status		=	rs.getString("RM_STATUS");
			rm_reason		=	rs.getString("REASON");
			result			=catid+"#"+catname+"#"+courseid+"#"+coursename+"#"+completiondate+"#"+status+"#"+coursetype+"#"+rm_status+"#"+rm_reason; 
			logger.info("course details after concatenating.. "+result);
			ll.add(result);
			
		}
    }
	catch (SQLException ex)
	{ 
	 ex.printStackTrace();
	////logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
	 //ab=null;
	 //result=ab;
	}
	catch (Exception ex)
	{ 
	ex.printStackTrace();
	///rab=null;
	 
	}
	finally{ try {
		stmt.close();
		dbCon.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	

}
		return ll;
	}


}
