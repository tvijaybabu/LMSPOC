package com.hcl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.learning.HomeController;

public class CourseDetails {
	private static final Logger logger = LoggerFactory.getLogger(CourseDetails.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static String AddCoursetoUser(String[] userCourses,int userid)
	{
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
		String dbusername="b71a8affc1f50f";
		String dbpassword="ac84a196";
		String result="";
		Connection dbCon = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;
		int catalogid;
		int courseid;
		try{
			for(int i=0;i<userCourses.length;i++)
			{
				logger.info("Welcome  SaveCourse**************userCourses***"+userCourses[i]);
				catalogid=Integer.parseInt(userCourses[i].split("#")[0]);
				courseid=Integer.parseInt(userCourses[i].split("#")[1]);
			String query ="INSERT INTO ad_b2664ef2ceb2d68.user_catalog(CATALOG_ID,COURSE_ID,USER_ID) VALUES(?,?,?)";
			//getting database connection to MySQL server 
			dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
			System.out.println("count of stock : "+dbCon);
			//getting PreparedStatment to execute query 
			stmt = dbCon.prepareStatement(query);
			stmt.setInt(1, catalogid);
			stmt.setInt(2, courseid);
			stmt.setInt(3, userid);
			stmt.executeUpdate();
			}
			//updating the data into memcache.*************/
			CacheUpdate cu = new CacheUpdate(); 
//			cu.updateUserListCache(username.toLowerCase(),password,email);
			
			//updating the data into memcache.***********/
			//SimpleMail sm	=	new SimpleMail();
			//sm.sendMail(email);
			result= "Course(s) Added Successfully...";
			}
			catch (SQLException ex)
			{ 
			 ex.printStackTrace();
			//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
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
		logger.info("search query.. "+query);
	try{
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		//System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		stmt = dbCon.createStatement();
		rs	=	stmt.executeQuery(query);
		while(rs.next())
		{
			UserCourse UC = new UserCourse(); 
			logger.info("CATNAME.. "+rs.getString("CATALOG_NAME"));
			logger.info("CATID.. "+rs.getString("CATALOG_ID"));
			logger.info("course_id.. "+rs.getString("course_id"));
			logger.info("course_name.. "+rs.getString("course_name"));
			logger.info("completion_date.. "+rs.getString("completion_date"));
			logger.info("status.. "+rs.getString("COURSE_STATUS"));
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
	//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
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
	public static ArrayList<CourseBean> getCourseList(String cat_id,String coursename)
	{
		ArrayList<CourseBean> ab = null;
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
		String dbusername="b71a8affc1f50f";
		String dbpassword="ac84a196";
		String result="";
		String searchcond="";
	//S eystem.out.println("count of stock : "+dbURL);
		Connection dbCon = null;
		Statement stmt = null; 
		ResultSet rs = null;
		int rowcount=0;
		int x=0;
		int y=0;
		if(cat_id!=null){
			searchcond=" WHERE c.CATALOG_ID="+cat_id;
			x=1;
		}
		if(coursename!=null){
			if(x==1)
			searchcond=searchcond+" AND c.COURSE_NAME LIKE'%"+coursename+"%'";
			else
				searchcond=" WHERE c.COURSE_NAME LIKE '%"+coursename+"%'";
		}
		
		//String query ="select c.catalog_id,c.course_id,c.course_name,ct.Catalog_name from ad_b2664ef2ceb2d68.Catalog ct,ad_b2664ef2ceb2d68.course c where c.CATALOG_ID=1042 and c.course_name like '%jav%' and c.catalog_id=ct.catalog_id;";
		String query ="select c.catalog_id,c.course_id,c.course_name,ct.Catalog_name from ad_b2664ef2ceb2d68.Catalog ct,ad_b2664ef2ceb2d68.course c"+searchcond+" and c.catalog_id=ct.catalog_id";
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
		ab = new ArrayList<CourseBean>(); 
		while(rs.next()){
			logger.info("CATNAME.. "+rs.getString("CATALOG_NAME"));
			logger.info("CATID.. "+rs.getString("CATALOG_ID"));
			logger.info("CATID.. "+rs.getString("course_id"));
			logger.info("CATID.. "+rs.getString("course_name"));
			CourseBean cb	= new CourseBean();
			cb.setCat_id(rs.getString("CATALOG_ID"));
			cb.setCat_name(rs.getString("CATALOG_NAME"));
			cb.setCourse_id(rs.getString("course_id"));
			cb.setCourse_name(rs.getString("course_name"));
			rowcount++;
			//catmap.put(rs.getInt(1), rs.getString(2));
			ab.add(cb);
			
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
		//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
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
	public ArrayList<UserCourse> getUserCourses(int userid)
	{
		/********************************************/
		ArrayList<UserCourse> courselist = new ArrayList<UserCourse>();
		HttpClient client1 = new DefaultHttpClient();
		String uu1="http://usercourseassignment.cfapps.io/rest/ms2/getUserCourseList/"+userid; 
		URI cc1=null;
		try {
			cc1 = new URI(uu1);
			System.out.println("cc--"+cc1);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    HttpGet request2 = new HttpGet(cc1);
		//HttpPost request1 = new HttpPost(cc);
	    System.out.println("resst Req--"+request2);
	    try {
			HttpResponse response = client1.execute(request2);
			System.out.println("resst Response--"+response.getEntity().getContent());
			BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			  String line = "";
			  //String resultmsg = "";
//			  ab = new ArrayList<CourseBean>();
			  while ((line = rd.readLine()) != null) {
				  System.out.println("__*************"+line);
				  line=line.replace("{", "");
				  line=line.replace("}", "");
				  line=line.replace("[", "");
				  line=line.replace("]", "");
				  String[] aa = line.split(",");
				  System.out.println("__length__"+aa.length);
				  //System.out.println("__o element__"+aa[0]);
				  //System.out.println("__1st element__"+aa[1]);
				  //nn.put(aa., value)
				  String[] beanarray;
				  for(int i=0;i<aa.length;i++)
				  {
					  UserCourse uc = new UserCourse(); 
					  beanarray=aa[i].split("#");
					  uc.setCat_id(beanarray[0]);
					  uc.setCat_name(beanarray[1]);
					  uc.setCourse_id(beanarray[2]);
					  uc.setCourse_name(beanarray[3]);
					 
					  if(beanarray[4]==null || beanarray[4]=="")
							uc.setCompletiondate(" ");
						else
							uc.setCompletiondate(beanarray[4]);

					  uc.setStatus(beanarray[5]);
					  uc.setCourse_type(beanarray[6]);
					  uc.setRm_status(beanarray[7]);
					  uc.setRm_reason(beanarray[8]);
					  courselist.add(uc);
				  }
			    System.out.println("__courselist__"+courselist);

			  }
	    }
	    catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    finally{
		    	
		    }


		
		/********************************************/

		return courselist;
	}
	public ArrayList<CourseBean> getCourseList()
	{
		/********************************************/
		ArrayList<CourseBean> courselist = new ArrayList<CourseBean>();
		HttpClient client1 = new DefaultHttpClient();
		String uu1="http://adminservices.cfapps.io/rest/admin/getCourseInfo"; 
		URI cc1=null;
		try {
			cc1 = new URI(uu1);
			System.out.println("cc--"+cc1);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    HttpGet request2 = new HttpGet(cc1);
		//HttpPost request1 = new HttpPost(cc);
	    System.out.println("resst Req--"+request2);
	    try {
			HttpResponse response = client1.execute(request2);
			System.out.println("resst Response--"+response.getEntity().getContent());
			BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			  String line = "";
			  //String resultmsg = "";
//			  ab = new ArrayList<CourseBean>();
			  while ((line = rd.readLine()) != null) {
				  System.out.println("__line*************__"+line);
				  line=line.replace("[", "");
				  line=line.replace("]", "");
				  String[] aa = line.split(",");
				  System.out.println("__length__"+aa.length);
				  //System.out.println("__o element__"+aa[0]);
				  //System.out.println("__1st element__"+aa[1]);
				  //nn.put(aa., value)
				  String[] beanarray;
				  for(int i=0;i<aa.length;i++)
				  {
					  CourseBean cb = new CourseBean(); 
					  System.out.println("bean array....aa[i]....."+aa[i]);
					  beanarray=aa[i].split("#");
					  System.out.println("bean array....."+beanarray[0]+"=="+beanarray[1]+"=="+beanarray[2]);
					  cb.setCourse_id(beanarray[0]);
					  cb.setCourse_name(beanarray[1]);
					  cb.setCourse_Status(beanarray[2]);
					  courselist.add(cb);
				  }
			    System.out.println("__courselist__"+courselist);

			  }
	    }
	    catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    finally{
		    	
		    }
		/********************************************/

		return courselist;
	}

}
