package com.mkyong.rest;
 
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.http.entity.StringEntity;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;


import com.hcl.integration.ReportStatus;
//import com.hcl.learning.ReportController;
import com.hcl.services.ReportManager;
import com.hcl.services.ReportManagerImpl;
import com.hcl.util.CourseDet;
//import com.hcl.util.CacheUpdate;
import com.hcl.util.User;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
 
@Path("/admin")
public class HelloWorldService {
	static MemcachedClient mc=null;
	static AuthDescriptor ad = null; 
	@Autowired
    ReportManager reportManager;
	@GET
	@Path("/")
	public Response TestConnection() {
 
		String output = "Connected";
		//System.out.println("araylist."+output);
		
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
	
		System.out.println("araylist."+output);
		return Response.status(200).entity(output).build();
		
	}
	@GET
	@Path("/catalog/ADD/{catname}/{catdescp}/{status}")
	public Response putCatalogDetails(@PathParam("catname") String catname,@PathParam("catdescp") String catdescp,@PathParam("status") String status) 
	{
		String output = "Jersey say : " + catname+"--"+catdescp+"--"+status;
		System.out.println("jersey post****************** : "+output);
		catname	=	catname.replace("+", " ");
		catdescp	=	catdescp.replace("+", " ");
		status	=	status.replace("+", " ");
		output = "Jersey say : " + catname+"--"+catdescp+"--"+status;
		System.out.println("jersey post******** sfter********** : "+output);
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_55f90d0a45a5495";
		String dbusername="bc7976fa98b14c";
		String dbpassword="5ace9996";*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_9bac56037f9df2e";
		String dbusername="b4628615f5cc69";
		String dbpassword="8f91d31a";
		
		String result="";
	//System.out.println("count of stock : "+dbURL);
		Connection dbCon = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null; 
		//String query ="SELECT * FROM ad_b2664ef2ceb2d68.doc;";
		String query ="INSERT INTO ad_9bac56037f9df2e.CATALOG(CATALOG_NAME,CATALOG_DESCP) VALUES(?,?)";
		try { 
		//getting database connection to MySQL server 
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		stmt = dbCon.prepareStatement(query);
		stmt.setString(1, catname.toLowerCase());
		stmt.setString(2, catdescp);
		stmt.executeUpdate(); 
		//updating the data into memcache.*************/
	//	CacheUpdate cu = new CacheUpdate(); 
	//	cu.updateUserListCache(username.toLowerCase(),password,email);
		
		//updating the data into memcache.***********/
		//SimpleMail sm	=	new SimpleMail();
		//sm.sendMail(email);
		result= "Catalog Added Successfully...";
		}
		catch (SQLException ex)
		{ 
		 ex.printStackTrace();
		//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
		 result="Catalog addition Failed";
		}
		catch (Exception ex)
		{ 
		ex.printStackTrace();
		result="Catalog addition Failed";
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
		return Response.status(200).entity(result).build();
 
	}
	
	@GET
	@Path("/course/ADD/{catid}/{coursename}/{coursedescp}/{status}")
	public Response putCourseDetails(@PathParam("catid") String catid,@PathParam("coursename") String coursename,@PathParam("coursedescp") String coursedescp,@PathParam("status") String status) 
	{
		String output = "Jersey say : " + coursename+"--"+coursedescp+"--"+status;
		System.out.println("jersey post****************** : "+output);
		coursename	=	coursename.replace("+", " ");
		coursedescp	=	coursedescp.replace("+", " ");
		status	=	status.replace("+", " ");
		output = "Jersey say : " + coursename+"--"+coursedescp+"--"+status;
		System.out.println("jersey post******** sfter********** : "+output);
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_55f90d0a45a5495";
		String dbusername="bc7976fa98b14c";
		String dbpassword="5ace9996";*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_9bac56037f9df2e";
		String dbusername="b4628615f5cc69";
		String dbpassword="8f91d31a";
		String result="";
	//System.out.println("count of stock : "+dbURL);
		Connection dbCon = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null; 
		//String query ="SELECT * FROM ad_b2664ef2ceb2d68.doc;";
		String query ="INSERT INTO ad_9bac56037f9df2e.COURSE(CATALOG_ID,COURSE_NAME,COURSE_DESCP,STATUS) VALUES(?,?,?,?)";
		try { 
		//getting database connection to MySQL server 
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		stmt = dbCon.prepareStatement(query);
		stmt.setString(1, catid.toLowerCase());
		stmt.setString(2, coursename.toLowerCase()); 
		stmt.setString(3, coursedescp);
		stmt.setString(4, status);
		
		stmt.executeUpdate(); 
		//updating the data into memcache.*************/
	//	CacheUpdate cu = new CacheUpdate(); 
	//	cu.updateUserListCache(username.toLowerCase(),password,email);
		
		//updating the data into memcache.***********/
		//SimpleMail sm	=	new SimpleMail();
		//sm.sendMail(email);
		result= "Course Added Successfully...";
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
		return Response.status(200).entity(result).build();
 
	}
	@GET
	@Path("/course/update/{courseid}/{status}")
	public Response updateCourseDetails(@PathParam("courseid") int courseid,@PathParam("status") String status) 
	{
		String output = "Jersey say : " + courseid+"--"+status;
		System.out.println("jersey post****************** : "+output);
		/*coursename	=	coursename.replace("+", " ");
		coursedescp	=	coursedescp.replace("+", " ");
		status	=	status.replace("+", " ");
		output = "Jersey say : " + coursename+"--"+coursedescp+"--"+status;
		System.out.println("jersey post******** sfter********** : "+output);*/
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_55f90d0a45a5495";
		String dbusername="bc7976fa98b14c";
		String dbpassword="5ace9996";*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_9bac56037f9df2e";
		String dbusername="b4628615f5cc69";
		String dbpassword="8f91d31a";
		String result="";
	//System.out.println("count of stock : "+dbURL);
		Connection dbCon = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null; 
		//String query ="SELECT * FROM ad_b2664ef2ceb2d68.doc;";
		String query ="UPDATE ad_9bac56037f9df2e.COURSE SET STATUS='"+status+"' WHERE COURSE_ID="+courseid;
		System.out.println("UPDATE COURSE QUERY::"+query);
		try { 
		//getting database connection to MySQL server 
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		//stmt = dbCon.prepareStatement(query);
		stmt = dbCon.prepareStatement(query);
		stmt.executeUpdate();  
		//updating the data into memcache.*************/
	//	CacheUpdate cu = new CacheUpdate(); 
	//	cu.updateUserListCache(username.toLowerCase(),password,email);
		
		//updating the data into memcache.***********/
		//SimpleMail sm	=	new SimpleMail();
		//sm.sendMail(email);
		result= "Course Updated Successfully...";
		}
		catch (SQLException ex)
		{ 
		 ex.printStackTrace();
		//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
		 result="Course Updation Failed";
		}
		catch (Exception ex)
		{ 
		ex.printStackTrace();
		result="Course Updation Failed";
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
		return Response.status(200).entity(result).build();
 
	}


	public MemcachedClient getMCClient(){
		  String memcachier_servers = "";
		    String memcachier_username = "";
		    String memcachier_password = "";
		 
		/*memcachier_servers = "mc5.dev.ec2.memcachier.com:11211";
	    memcachier_username = "6bf938";
	    memcachier_password = "ef429855122cd0eff1257e4c0993182b";*/
		    memcachier_servers = "mc5.dev.ec2.memcachier.com:11211";
		    memcachier_username = "c2c85f";
		    memcachier_password = "12e2b2736488335e5849fe2049f44de0";
		    
	    if(ad==null)
	    	{ad = new AuthDescriptor(new String[] { "PLAIN" },
	        new PlainCallbackHandler(memcachier_username,
	            memcachier_password));
	    	} System.out.println("mc value.in getMCClient...........::"+mc);
	    try {
	    	if(mc==null)
				mc = new MemcachedClient(new ConnectionFactoryBuilder().setProtocol(ConnectionFactoryBuilder.Protocol.BINARY).setAuthDescriptor(ad).build(),AddrUtil.getAddresses(memcachier_servers));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	return mc;    
	}
	@POST
	@Path("/getUserListFromCache")
	public Response GetUserListfromCache(){
		ConcurrentHashMap<String, String> userlist	=	null;
		 User us=new User();
		try {
		    	mc=getMCClient();
		     // if(operation==0)
		    	System.out.println("mc value............::"+mc);
		      userlist=  (ConcurrentHashMap<String, String>)mc.get("UserList");
		      System.out.println("userlist from cache* for admin getUserListFromCache.....::"+mc.get("UserList"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			 System.out.println("Exception while getting userlist from cache* for admin......::");
		} 
	
		return Response.status(200).entity(userlist.toString()).build();
		}
	
	@POST
	@Path("/getUserReportFromCache")
	public Response GetUserReportfromCache(){
		ConcurrentHashMap<String, String> userlist	=	null;
		 //User us=new User();
		String uuid="";
		try {
			ReportManager reportManager= new ReportManagerImpl();
			System.out.println("reportManager**************...........::"+reportManager);
			uuid=reportManager.generateReport();
			
	        UriComponents uriComponents = MvcUriComponentsBuilder
	                .fromMethodName(HelloWorldService.class, "viewReportStatus", uuid).build();
	        System.out.println("download URL.. in helow world service.."+uriComponents.encode().toUri().toString() + "/download");
			
		    //	mc=getMCClient();
		     // if(operation==0)
		    	System.out.println("uuid value............::"+uuid);
		      //userlist=  (ConcurrentHashMap<String, String>)mc.get("UserList");
		      //System.out.println("userlist from cache* for admin getUserListFromCache.....::"+mc.get("UserList"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			 System.out.println("Exception while generating userlist report from cache* for admin......::");
		} 
		/*JSONObject obj = new JSONObject();
		JSONObject json = new JSONObject();
		json.put("name1", "value1");
		json.put("name2", "value2");
		StringEntity se=null;
		try {
			se = new StringEntity( json.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 //System.out.println("Exception while getting userlist from cache* for se*********......::"+se);
		//(JSONObjec  t)JSONSerializer.toJSON(userlist);
		//System.out.println("JSONObject :: "+(JSONObject)JSONSerializer.toJSON(userlist));
//return userlist;
		return Response.status(200).entity(uuid.toString()).build();
		//return userlist;
	}
	public ReportStatus viewReportStatus(@PathVariable String uuid) {
		ReportManager reportManager= new ReportManagerImpl();
        return reportManager.getReportStatus(uuid);
    }
	@GET
	@Path("/getCourseInfo")
	public Response getCourseInfo(){
		String output="";
		ArrayList<String> ab = null;
		try {
			CourseDet cd	= 	new CourseDet();//getCourseInfo
			ab	=	cd.getCourseInfo();
		      System.out.println("getCourseInfo. in MS3.. ....::"+ab);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			 System.out.println("Exception while getting getCourseInfo......::");
		} 
	if(ab==null )
		output=null;
		else
			output=ab.toString();
			
		return Response.status(200).entity(output).build();
		}
}