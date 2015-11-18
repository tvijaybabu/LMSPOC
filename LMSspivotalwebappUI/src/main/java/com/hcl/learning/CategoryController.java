package com.hcl.learning;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import net.sf.json.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hcl.util.CacheUpdate;
import com.hcl.util.Catalog;
import com.hcl.util.CourseBean;
import com.hcl.util.CourseDetails;
import com.hcl.util.SimpleMail;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
/**
 * Handles requests for the registering new user.
 */
@Controller

public class CategoryController {
	@Autowired 
	private HttpSession httpSession;

private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/catalog/Add", method = RequestMethod.POST)
	public String addCatalog(HttpServletRequest request,Model model) {
		logger.info("Welcome  addCatalog ");
		
	String event = request.getParameter("event");
	String result="";
	String page="";
	logger.info("Welcome addCatalog event:"+event);
	//if(event.equalsIgnoreCase("register")){
		String catname = request.getParameter("catname");
		String catdescp = request.getParameter("CategoryDescp");
		String catstatus = null;//request.getParameter("catstatus");
		logger.info("Welcome addCatalog catname:"+catname);
		logger.info("Welcome addCatalog catdescp:"+catdescp);
		String user = request.getParameter("user");
		//model.addAttribute("result", user );
		//result=InsertCatalog(catname,catdescp);
		
		/**********************/
		@SuppressWarnings("deprecation")
		HttpClient client1 = new DefaultHttpClient();
		String query="";
		try {
			query=catname+"/"+catdescp+"/"+catstatus;
			query=query.replace(" ", "+");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  //String uu="http://restfulexample.cfapps.io/post/rest/admin/catalog/"+catname+"/"+catdescp+"/"+catstatus; 
		String uu="http://adminservices.cfapps.io/rest/admin/catalog/ADD/"+query;
		  URI cc=null;
		try {
			cc = new URI(uu);
			System.out.println("cc--"+cc);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//@SuppressWarnings("deprecation")
		//HttpClient client1 = new DefaultHttpClient();
		HttpGet request2 = new HttpGet(cc);
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
				  result=line;
			  }
			  
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    finally{
	    	
	    }
				/**********************/
		logger.info("Welcome addCatalog result:"+result);
		model.addAttribute("status", result );
		model.addAttribute("user", user);
		
		CourseDetails cd = new CourseDetails();
		ArrayList<CourseBean> cb = new ArrayList<CourseBean>();
		cb	=	cd.getCourseList();
		System.out.println("cb in controller "+cb);
		model.addAttribute("courseInfoList", cb );
		
		ConcurrentHashMap<String, String> userlist	=	null;
		CacheUpdate cu = new CacheUpdate();
		userlist=cu.GetUserListfromCache();	
		System.out.println("after getting userlist  from  cache admin...: "+userlist);
		model.addAttribute("userList", userlist );
		Catalog c = new Catalog();
		HashMap<Integer, String> catmap= null;
		catmap	=	c.getCatoryList();
		System.out.println("catmap from util...: "+catmap);
		model.addAttribute("catList", catmap );
		page="AdminPage";
		
	//}
	/*else
	{*/
			
	
	return page;
	}
	
	public static String InsertCatalog(String catname,String catdescp)
	{
		/*try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	//Local instance MySQL56
	//String dbURL = "jdbc:mysql://localhost:3306/pivot_schema"; 
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
		String dbusername="b71a8affc1f50f";
		String dbpassword="ac84a196";
		String result="";
	//System.out.println("count of stock : "+dbURL);
	Connection dbCon = null;
	PreparedStatement stmt = null; 
	ResultSet rs = null; 
	//String query ="SELECT * FROM ad_b2664ef2ceb2d68.doc;";
	String query ="INSERT INTO ad_b2664ef2ceb2d68.CATALOG(CATALOG_NAME,CATALOG_DESCP) VALUES(?,?)";
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
	CacheUpdate cu = new CacheUpdate(); 
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
	
	return result;
		}
}
