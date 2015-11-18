 package com.hcl.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.hcl.util.CacheUpdate;
import com.hcl.util.Catalog;
import com.hcl.util.CourseBean;
import com.hcl.util.CourseDetails;
import com.hcl.util.ManagerDetails;
import com.hcl.util.SimpleMail;
import com.hcl.util.UserCourse;
import com.hcl.util.UserRestURIConstants;
import com.hcl.util.User;
/**
 * Handles requests for the registering new user.
 */
@Controller

public class AuthenticateController {
	@Autowired 
	private HttpSession httpSession;

private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
//public static final String SERVER_URI = "http://springrestms1.cfapps.io/";
public static final String SERVER_URI = "http://userregistrationservice.cfapps.io/";
	@RequestMapping(value = "/login/Auth", method = RequestMethod.POST)
	public String registeruser(HttpServletRequest request,Model model) {
		logger.info("Welcome registeruser ");
		
	/*	Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
	*/	String event = request.getParameter("event");
	String result="";
	String page="";
	logger.info("Welcome registeruser event:"+event);
	boolean testServices=false;
	String result2="";
	String result3="";
	if(TestMicrservice2().equalsIgnoreCase("NotConnected"))
	{testServices=true;
		result2="UserCourse Microservice deployment is going on..Please Try later";	
	}
	if(TestMicrservice3().equalsIgnoreCase("NotConnected"))
	{testServices=true;
		result3="Admin Microservice deployment is going on..Please Try later";
	}
	
	if(testServices==true){
		page="home";
		model.addAttribute("MS2", result2 );
		model.addAttribute("MS3", result3 );
	}
	else
	{
	
		if(event.equalsIgnoreCase("register")){
			String user = request.getParameter("NewUserName");
			String pwd = request.getParameter("Newpassword");
			String email = request.getParameter("NewEmail");
			String role = request.getParameter("Role");
			int rmid = Integer.parseInt(request.getParameter("RMID"));
			String reportingManager = request.getParameter("ReportingManager");
			
			logger.info("Welcome registeruser reportingManager:"+reportingManager);
			logger.info("Welcome registeruser user:"+user);
			logger.info("Welcome registeruser pwd:"+pwd);
			logger.info("Welcome registeruser email:"+email);
			logger.info("Welcome registeruser role:"+role);
			//result=InsertUser(user,pwd,email,role);
//			result=InsertUser(user,pwd,email,role,rmid,reportingManager);
			/************** Microservice1 using Spring Restful webservice***/
			   RestTemplate restTemplate = new RestTemplate();
		        User userobj = new  User();
		        userobj.setUsername(user);
		        userobj.setPassword(pwd);
		        userobj.setEmailid(email);
		        userobj.setRole(role);
		        userobj.setRm_id(rmid);
		        userobj.setRm_Mgr_email(reportingManager);
		        //emp.setId(1);emp.setName("Pankaj Kumar");
		        
		       // Employee response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP, emp, Employee.class);
		        String response = restTemplate.postForObject(SERVER_URI+UserRestURIConstants.CREATE_USER, userobj, String.class);
		        System.out.println("response==ID="+response);
		        /************** Microservice1 using Spring Restful webservice***/
		        model.addAttribute("result", response );
			HashMap Ml = new HashMap();
			//ManagerDetails 
			Ml=ManagerDetails.getManagerList();
			model.addAttribute("ManagerMap", Ml);
			
			page="home";
			
		}
		else
		{
			String user = request.getParameter("UserName");
			String pwd = request.getParameter("password");
			String role="";	
			String email;
			String userid="";
			String rmid="";
			String rmemailid="";
			//model.addAttribute("serverTime", formattedDate );
			logger.info("Welcome login user:"+user);
			logger.info("Welcome login pwd:"+pwd);
			result	=	ChecktUser(user,pwd);
			logger.info("result from checkusexr:"+result);
			//model.addAttribute("result", "" );
			model.addAttribute("username", result );
			if(result.contains("#")){
			role	=	 result.split("#")[1];	
			email	=	 result.split("#")[2];
			userid	=	 result.split("#")[3];
			rmid	=	 result.split("#")[4];
			rmemailid=	 result.split("#")[5];
			result	=	 result.split("#")[0];
			model.addAttribute("ReportingManager", rmemailid );
			model.addAttribute("rmid", rmid );
			model.addAttribute("role", role );
			model.addAttribute("user", result );
			model.addAttribute("email", email );
			model.addAttribute("pwd", pwd );
			model.addAttribute("userid", userid );
			}
			//model.addAttribute("result", result );
			logger.info("Welcome login user check:"+user+"======"+result);
			if(result.equalsIgnoreCase(user))
			{
				//httpSession.setAttribute("user",user);
				HashMap<Integer, String> catmap=null;
				catmap=getCatoryList();
				model.addAttribute("catList", catmap );
	
				/*ArrayList<UserCourse> courselist = new ArrayList<UserCourse>();
				CourseDetails coursedet = new CourseDetails();
				courselist	=	coursedet.getUserCourses(Integer.parseInt(userid));*/
				/********************************************/
				/*HttpClient client1 = new DefaultHttpClient();
				String uu1="http://microservice2.cfapps.io/rest/ms2/getUserCourseList/"+userid; 
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
	//				  ab = new ArrayList<CourseBean>();
					  String[] beanarray;
					  while ((line = rd.readLine()) != null) {
						  line=line.replace("{", "");
						  line=line.replace("}", "");
						  if(line.contains(",")){
						  String[] aa = line.split(",");
						  System.out.println("__length__"+aa.length);
						  //System.out.println("__o element__"+aa[0]);
						  //System.out.println("__1st element__"+aa[1]);
						  //nn.put(aa., value)
						
						  					  for(int i=0;i<aa.length;i++)
						  {
							  UserCourse uc = new UserCourse(); 
							  beanarray=aa[i].split("#");
							  uc.setCat_id(beanarray[0]);
							  uc.setCat_name(beanarray[1]);
							  uc.setCourse_id(beanarray[2]);
							  uc.setCourse_name(beanarray[3]);
							  uc.setCompletiondate(beanarray[4]);
							  uc.setStatus(beanarray[5]);
							  uc.setCourse_type(beanarray[6]);
							
							  courselist.add(uc);
						  }}
						  else
						  {
							  UserCourse uc = new UserCourse(); 
							  beanarray=line.split("#");
							  uc.setCat_id(beanarray[0]);
							  uc.setCat_name(beanarray[1]);
							  uc.setCourse_id(beanarray[2]);
							  uc.setCourse_name(beanarray[3]);
							  uc.setCompletiondate(beanarray[4]);
							  uc.setStatus(beanarray[5]);
							  uc.setCourse_type(beanarray[6]);
							
							  courselist.add(uc);
							  
						  }
							  }
					    System.out.println("__courselist__"+courselist);
	
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
	
	
				
	*/			/********************************************/
	
	//			model.addAttribute("usercourselist",courselist);
				
				if(role.equalsIgnoreCase("Manager")){
					
					HttpClient client1 = new DefaultHttpClient();
					String uu1="http://usercourseassignmentservice.cfapps.io/rest/ms2/getPendingCourseList/"+userid; 
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
				    ArrayList<UserCourse> courselist = new ArrayList<UserCourse>();
				    try {
				    	
						HttpResponse response = client1.execute(request2);
						System.out.println("resst Response--"+response.getEntity().getContent());
						BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
						  String line = "";
						  //String resultmsg = "";
//						  ab = new ArrayList<CourseBean>();
						  while ((line = rd.readLine()) != null) {
							  System.out.println("__****1111*********"+line);
							  line=line.replace("{", "");
							  line=line.replace("}", "");
							  line=line.replace("[", "");
							  line=line.replace("]", "");
							  System.out.println("__****1221*********"+line.length());
							  if(line.length()>0){
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
								  uc.setUser_id(beanarray[0]);
								  uc.setCat_id(beanarray[1]);
								  uc.setCat_name(beanarray[2]);
								  uc.setCourse_id(beanarray[3]);
								  uc.setCourse_name(beanarray[4]);
								  uc.setRm_status(beanarray[5]);
								  courselist.add(uc);
							  }
						    System.out.println("__courselist__"+courselist);
							  }
						  }///while
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
				    if(courselist.size()==0)
				    	courselist=null;
				    
				    model.addAttribute("pendinglist", courselist );
					page= "ManagerPage";
				}
				else if(role.equalsIgnoreCase("admin")){
					
					CourseDetails cd = new CourseDetails();
					ArrayList<CourseBean> cb = new ArrayList<CourseBean>();
					cb	=	cd.getCourseList();
					System.out.println("cb in controller "+cb);
					model.addAttribute("courseInfoList", cb );
					/*for(int i=0;i<cb.size();i++)
					  {
						String[] beanarray=null;
					  CourseBean cb1 = new CourseBean(); 
					  System.out.println("bean array....aa[i]....."+cb);
					  cb1	=(CourseBean)cb[i];
					  beanarray=cb1[i].split("#");
					  System.out.println("bean array....."+beanarray[0]+"=="+beanarray[1]+"=="+beanarray[2]);
					  cb.setCourse_id(beanarray[0]);
					  cb.setCourse_name(beanarray[1]);
					  cb.setCourse_Status
					  }*/
					System.out.println("cb before putting into model"+cb);
					
					@SuppressWarnings("deprecation")
					HttpClient client = new DefaultHttpClient();
					ConcurrentHashMap<String, String> userlist	=	null;
					page="AdminPage";
					System.out.println("Before getting userlist  from  cache admin...: ");
					//updating the data into memcache.*************/
					CacheUpdate cu = new CacheUpdate();
					cu.updateUserListfromDB();
					//userlist=cu.GetUserListfromCache();
					/**********************/
					String uu="http://adminservice.cfapps.io/rest/admin/getUserListFromCache";
					//String uu="http://adminservices.cfapps.io/rest/admin/getUserListFromCache";
					URI cc=null;
					try {
						cc = new URI(uu);
						System.out.println("cc--"+cc);
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    //HttpGet request1 = new HttpGet(cc);
					HttpPost request1 = new HttpPost(cc);
				    System.out.println("resst Req--"+request);
				    try {
						HttpResponse response = client.execute(request1);
						System.out.println("resst Response--"+response.getEntity().getContent());
						BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
						  String line = "";
						  userlist = new ConcurrentHashMap<String, String>();
						  while ((line = rd.readLine()) != null) {
							  line=line.replace("{", "");
							  line=line.replace("}", "");
							  String[] aa = line.split(",");
							  System.out.println("__length__"+aa.length);
							  System.out.println("__o element__"+aa[0]);
							  System.out.println("__1st element__"+aa[1]);
							  //nn.put(aa., value)
							  for(int i=0;i<aa.length;i++)
							  {
								  userlist.put(aa[i].split("=")[0], aa[i].split("=")[1]);
							  }
						    System.out.println("____"+userlist);
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
	
					System.out.println("after getting userlist  from  cache admin...: "+userlist);
					model.addAttribute("userList", userlist );
					model.addAttribute("REPORT_DOWNLOAD_URL","");
					
				}
				else{
					HashMap Ml = new HashMap();
					Ml=ManagerDetails.getManagerList();
					model.addAttribute("ManagerMap", Ml);

					ArrayList<UserCourse> courselist = new ArrayList<UserCourse>();
					CourseDetails coursedet = new CourseDetails();
					courselist	=	coursedet.getUserCourses(Integer.parseInt(userid));
					model.addAttribute("usercourselist",courselist);
					page= "LearningHome";}
			}
			else{
				HashMap Ml = new HashMap();
				//ManagerDetails 
				Ml=ManagerDetails.getManagerList();
				model.addAttribute("ManagerMap", Ml);
				model.addAttribute("result", result );
				page= "home";
			}
				
		}
	}
	return page;
	}
	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public String updateuserdet(HttpServletRequest request,Model model) {
		logger.info("  Welcome updateuserdet ");
		String pwd="";
		String userid=request.getParameter("USERID");;
		String user=request.getParameter("UserName");
		if(request.getParameter("Newpassword")!=null)
			pwd=request.getParameter("Newpassword");
		else
		{
			pwd=request.getParameter("oldpassword");
		}
		String role=request.getParameter("Role");
		String email=request.getParameter("Email");
		//String rmid = request.getParameter("RMID");
		int rmid = Integer.parseInt(request.getParameter("RMID"));
		String reportingManager = request.getParameter("ReportingManager");
	//	String result=UpdateUser(user,pwd,email,role,rmid,reportingManager);
		/************** Microservice1 using Spring Restful webservice***/
		   RestTemplate restTemplate = new RestTemplate();
	        User userobj = new  User();
	        userobj.setUsername(user);
	        userobj.setPassword(pwd);
	        userobj.setEmailid(email);
	        userobj.setRole(role);
	        userobj.setRm_id(rmid);
	        userobj.setRm_Mgr_email(reportingManager);
	        //emp.setId(1);emp.setName("Pankaj Kumar");
	        
	       // Employee response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP, emp, Employee.class);
	        String result = restTemplate.postForObject(SERVER_URI+UserRestURIConstants.UPDATE_USER, userobj, String.class);
	        System.out.println("response==ID="+result);
	        /************** Microservice1 using Spring Restful webservice***/
	    
		System.out.println("result updateuserdet: "+result);
		
		if(result.equalsIgnoreCase(user)){
		/*role	= result.split("#")[1];	
		email	= result.split("#")[2];
		result	= result.split("#")[0];*/
			
			
			//courselist	=	coursedet.getUserCourses(Integer.parseInt(userid));
			//model.addAttribute("usercourselist",courselist);
		ArrayList<UserCourse> courselist = new ArrayList<UserCourse>();
		CourseDetails coursedet = new CourseDetails();
		courselist	=	coursedet.getUserCourses(Integer.parseInt(userid));
		model.addAttribute("usercourselist",courselist);
		HashMap<Integer, String> catmap=null;
		catmap=getCatoryList();
		model.addAttribute("catList", catmap );
		model.addAttribute("result", "User details updated Successfully.." );
		model.addAttribute("role", role );
		model.addAttribute("user", user );
		model.addAttribute("email", email );
		model.addAttribute("pwd", pwd );
		model.addAttribute("userid", userid);
		model.addAttribute("ReportingManager", reportingManager );
		HashMap Ml = new HashMap();
		//ManagerDetails 
		Ml=ManagerDetails.getManagerList();
		model.addAttribute("rmid", rmid );
		model.addAttribute("ManagerMap", Ml);
		//model.addAttribute("result", result );
		}
				return "LearningHome";
	}	
	public static String InsertUser(String username,String password,String email,String role,int rmid,String reportingManager)
	{
		String result="";
		String usercheck=ChecktUser(username,password);
		String user	= usercheck.split("#")[0];	
		//result	= result.split("#")[0];
		if(user.equalsIgnoreCase(username))
		{
			result	="User Already Exists..";
		}
		else{
			//Local instance MySQL56
			//String dbURL = "jdbc:mysql://localhost:3306/pivot_schema"; 
				/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
				String dbusername="b71a8affc1f50f";
				String dbpassword="ac84a196";*/
			String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_467de3d0d776395";
			String dbusername="b73452b3df25cc";
			String dbpassword="7b2fbfb7";
				
			//System.out.println("count of stock : "+dbURL);
			Connection dbCon = null;
			PreparedStatement stmt = null; 
			ResultSet rs = null; 
			//String query ="SELECT * FROM ad_b2664ef2ceb2d68.doc;";
			String query ="INSERT INTO ad_467de3d0d776395.USER_DET(USER_NAME,PASSWORD,EMAILID,ROLE,RM_ID,RM_EMAILID) VALUES(?,?,?,?,?,?)";
			try { 
			//getting database connection to MySQL server 
			dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
			System.out.println("count of stock : "+dbCon);
			//getting PreparedStatment to execute query 
			stmt = dbCon.prepareStatement(query);
			stmt.setString(1, username.toLowerCase());
			stmt.setString(2, password);
			stmt.setString(3, email);
			stmt.setString(4, role);
			stmt.setInt(5, rmid);
			stmt.setString(6, reportingManager);
			stmt.executeUpdate(); 
			System.out.println("Before updating cache...: ");
			//updating the data into memcache.*************/
			CacheUpdate cu = new CacheUpdate(); 
			cu.updateUserListCache(username.toLowerCase(),password,email);
			
			//updating the data into memcache.***********/
			SimpleMail sm	=	new SimpleMail();
			sm.sendMail(email);
			result= "User registered Successfully...";
			}
			catch (SQLException ex)
			{ 
			 ex.printStackTrace();
			//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
			 result="User registration Failed";
			}
			catch (Exception ex)
			{ 
			ex.printStackTrace();
			result="User registration Failed";
			}
			finally{  try {
				stmt.close();
				dbCon.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
		
			}
			}
	}
	
	return result;
		}
	public static String UpdateUser(String username,String password,String email,String role,String rmid,String reportingManager)
	{
		String result=username;
		//String usercheck=ChecktUser(username,password);
		//String user	= usercheck.split("#")[0];	
		//result	= result.split("#")[0];
		/*if(user.equalsIgnoreCase(username))
		{
			result	="User Already Exists..";
		}
		else{*/
			//Local instance MySQL56
			//String dbURL = "jdbc:mysql://localhost:3306/pivot_schema"; 
		/*		String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
				String dbusername="b71a8affc1f50f";
				String dbpassword="ac84a196";
		*/
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_467de3d0d776395";
		String dbusername="b73452b3df25cc";
		String dbpassword="7b2fbfb7";
			//System.out.println("count of stock : "+dbURL);
			Connection dbCon = null;
			PreparedStatement stmt = null; 
			ResultSet rs = null; 
			//String query ="SELECT * FROM ad_b2664ef2ceb2d68.doc;";
			
			String query ="UPDATE ad_467de3d0d776395.USER_DET SET PASSWORD='"+password+"',EMAILID='"+email+"',ROLE='"+role+"',RM_ID="+rmid+",RM_EMAILID='"+ reportingManager+"' WHERE USER_NAME='"+username+"'";
			System.out.println("update user query : "+query);
			try { 
			//getting database connection to MySQL server 
			dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
			System.out.println("connection : "+dbCon);
			//getting PreparedStatment to execute query 
			stmt = dbCon.prepareStatement(query);
			stmt.executeUpdate(); 
			System.out.println("Before updating cache...: ");
			//updating the data into memcache.*************/
			//CacheUpdate cu = new CacheUpdate(); 
			//cu.updateUserListCache(username.toLowerCase(),password,email);
			
			//updating the data into memcache.***********/
			/*SimpleMail sm	=	new SimpleMail();
			sm.sendMail(email);
			result= "User registered Successfully...";*/
			}
			catch (SQLException ex)
			{ 
			 ex.printStackTrace();
			//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
			 result="User Updation ailed";
			}
			catch (Exception ex)
			{ 
			ex.printStackTrace();
			result="User Updation Failed";
			}
			finally{  try {
				stmt.close();
				dbCon.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
		
		//	}
			}
	}
	
	return result;
		}
	
	public static String ChecktUser(String username,String password)
	{
	
		/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
		String dbusername="b71a8affc1f50f";
		String dbpassword="ac84a196";
		*/String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_467de3d0d776395";
		String dbusername="b73452b3df25cc";
		String dbpassword="7b2fbfb7";
		
		String result="";
		//System.out.println("count of stock : "+dbURL);
		Connection dbCon = null;
		Statement stmt = null; 
		ResultSet rs = null;
		int rowcount=0;
		String query ="SELECT USER_ID,USER_NAME,ROLE,EMAILID,RM_ID,RM_EMAILID FROM ad_467de3d0d776395.USER_DET WHERE USER_NAME='"+username.toLowerCase()+"' AND PASSWORD='"+password+"'";
		//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
		logger.info("Welcome checkuer query.. "+query);
		try { 
		//getting database connection to MySQL server 
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
	//	System.out.println("count of stock : "+dbCon);
		//getting PreparedStatment to execute query 
		stmt = dbCon.createStatement();
		/*stmt.setString(1, username);
		stmt.setString(2, password);
		stmt.setString(3, email);
		stmt.executeUpdate();*/
		rs	=	stmt.executeQuery(query);
		String role="";
		String email="";
		String userid="";
		String rmid="";
		String rmEmailid="";
		while(rs.next()){
			rowcount++;
			role	=	rs.getString("ROLE");
			email	=	rs.getString("EMAILID");
			userid	=	rs.getString("USER_ID");
			rmid	=	rs.getString("RM_ID");
			 rmEmailid=	rs.getString("RM_EMAILID");
		}
		//SimpleMail sm	=	new SimpleMail();
		//sm.sendMail(email);
		if(rowcount>0)
		{
			result= username+"#"+role+"#"+email+"#"+userid+"#"+rmid+"#"+rmEmailid;
		}
		else
			result= "User does not exists..";
		
		//result= "User reistered Successfully..."; 
		}
		catch (SQLException ex)
		{ 
		 ex.printStackTrace();
		//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
		 result="Login Failed";
		}
		catch (Exception ex)
		{ 
		ex.printStackTrace();
		result="Login Failed";
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
	public static String TestMicrservice3()
	{
		String result="connected";
		URL url = null;
		try {
			//url = new URL("http://adminservice.cfapps.io/rest/admin/");
			url = new URL("http://adminservices.cfapps.io/rest/admin/");
			URLConnection connection = url.openConnection();
			System.out.println("nn......"+connection);
			InputStream response = connection.getInputStream();
			//url.openStream();
			//connection.connect();
			System.out.println("......"+response);
		} catch (Exception e) {
			//e.printStackTrace();
			result="NotConnected";
		}
	
		return result;
	}
	public static String TestMicrservice2()
	{
		String result="connected";
		URL url = null;
		try {
			url = new URL("http://usercourseassignmentservice.cfapps.io/rest/ms2/");
			URLConnection connection = url.openConnection();
			System.out.println("nn......"+connection);
			InputStream response = connection.getInputStream();
			//url.openStream();
			//connection.connect();
			System.out.println("......"+response);
		} catch (Exception e) {
			//e.printStackTrace();
			result="NotConnected";
		}
		return result;
	}
	public static HashMap getCatoryList()
	{
		Catalog c = new Catalog();
		return 	c.getCatoryList();
		/*try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<Integer, String> catmap= null;
	//Local instance MySQL56
	//String dbURL = "jdbc:mysql://localhost:3306/pivot_schema"; 
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
		String dbusername="b71a8affc1f50f";
		String dbpassword="ac84a196";
		String result="";
	//System.out.println("count of stock : "+dbURL);
	Connection dbCon = null;
	Statement stmt = null; 
	ResultSet rs = null;
	int rowcount=0;
	String query ="SELECT CATALOG_ID,CATALOG_NAME FROM ad_b2664ef2ceb2d68.CATALOG ORDER BY CATALOG_NAME";
	//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
	logger.info("Welcome category query.. "+query);
	try { 
	//getting database connection to MySQL server 
	dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
//	System.out.println("count of stock : "+dbCon);
	//getting PreparedStatment to execute query 
	stmt = dbCon.createStatement();
	stmt.setString(1, username);
	stmt.setString(2, password);
	stmt.setString(3, email);
	stmt.executeUpdate();
	rs	=	stmt.executeQuery(query);
	catmap= new HashMap<Integer, String>();
	while(rs.next()){
		logger.info("CATNAME.. "+rs.getString("CATALOG_NAME"));
		logger.info("CATID.. "+rs.getString("CATALOG_ID"));
		logger.info("Welcome category query.. "+rs.getInt(1));
		
		rowcount++;
		catmap.put(rs.getInt(1), rs.getString(2));
		
	}
	//SimpleMail sm	=	new SimpleMail();
	//sm.sendMail(email);
	if(rowcount>0)
	{
		
	}
	else{
		//result= "Category does not exists..";
		catmap=null;
	}
	
	//result= "User reistered Successfully..."; 
	}
	catch (SQLException ex)
	{ 
	 ex.printStackTrace();
	//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
	 result="Login Failed";
	}
	catch (Exception ex)
	{ 
	ex.printStackTrace();
	result="Login Failed";
	}
	finally{ try {
		stmt.close();
		dbCon.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 



	}
	
	return catmap;*/
		}

}
