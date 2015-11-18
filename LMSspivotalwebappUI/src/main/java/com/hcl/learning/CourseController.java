package com.hcl.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;

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
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hcl.util.CacheUpdate;
import com.hcl.util.CourseBean;
import com.hcl.util.CourseDetails;
import com.hcl.util.ManagerDetails;
import com.hcl.util.SimpleMail;
import com.hcl.util.Catalog;
import com.hcl.util.UserCourse;
//import org.springframework.boot:spring-cloud-starter-eureka-server;
/**
 * Handles requests for the registering new user.
 */
@Controller

public class CourseController {
	@Autowired 
	private HttpSession httpSession;

private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

@RequestMapping(value = "/manager/approve", method = RequestMethod.POST)
public String Approve(HttpServletRequest request,Model model) {
	String userid=request.getParameter("userid");
	String courseid=request.getParameter("courseid");
	String status=request.getParameter("RM_status");
	String reason=request.getParameter("RM_Reason");
	String Mgrid=request.getParameter("Mgrid");
	String user=request.getParameter("user");
	
	reason=reason.replace(" ", "+");
	System.out.println("Approve userid--"+userid);
	System.out.println("Approve courseid--"+courseid);
	System.out.println("Approve status--"+status);
	System.out.println("Approve reason--"+reason);
	
String result="";
	HttpClient client1 = new DefaultHttpClient();
	String uu1="http://usercourseassignment.cfapps.io/rest/ms2/managerApprove/"+userid+"/"+courseid+"/"+status+"/"+reason; 
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
//		  ab = new ArrayList<CourseBean>();
		  while ((line = rd.readLine()) != null) {
			  result=line;
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

/***********************************************************************************/
    HttpClient client2 = new DefaultHttpClient();
	String uu2="http://usercourseassignment.cfapps.io/rest/ms2/getPendingCourseList/"+Mgrid; 
	URI cc2=null;
	try {
		cc2 = new URI(uu2);
		System.out.println("cc--"+cc2);
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    HttpGet request1 = new HttpGet(cc2);
	//HttpPost request1 = new HttpPost(cc);
    System.out.println("resst Req--"+request1);
    ArrayList<UserCourse> courselist = new ArrayList<UserCourse>();
    try {
    	
		HttpResponse response = client2.execute(request1);
		System.out.println("resst Response--"+response.getEntity().getContent());
		BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		  String line1 = "";
		  //String resultmsg = "";
//		  ab = new ArrayList<CourseBean>();
		  while ((line1 = rd.readLine()) != null) {
			  System.out.println("__11111*************"+line1);
			  line1=line1.replace("{", "");
			  line1=line1.replace("}", "");
			  line1=line1.replace("[", "");
			  line1=line1.replace("]", "");
			  System.out.println("__*******22******"+line1);
			  if(line1.length()>0){
			  String[] aa = line1.split(",");
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
    if(courselist.size()==0)
    	courselist=null;
 model.addAttribute("pendinglist", courselist );
 model.addAttribute("userid",  Mgrid );
 model.addAttribute("user",  user );

    /************************************************************************************************/
model.addAttribute("status", result);
	return "ManagerPage";
}

@RequestMapping(value = "/course/complete", method = RequestMethod.POST)
public String FinishCourse(HttpServletRequest request,Model model) {

String result="";
int userid=Integer.parseInt(request.getParameter("USERID"));
int courseid=Integer.parseInt(request.getParameter("compcourseid"));
//String username=request.getParameter("userName");
	logger.info("Welcome  FinishCourse**************userCourses***"+courseid);
	logger.info("Welcome  FinishCourse**************userid***"+userid);
	CourseDetails cd = new CourseDetails();
		HttpClient client1 = new DefaultHttpClient();
		String uu1="http://usercourseassignment.cfapps.io/rest/ms2/course/finish/"+userid+"/"+courseid; 
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
				  result=line;
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

		
		
//	}
	/******************************************************/
	
	
	HashMap<Integer, String> catmap=null;
	ArrayList<UserCourse> courselist = new ArrayList<UserCourse>(); 
	Catalog ct= new Catalog();
	catmap=ct.getCatoryList();
	
	
	CourseDetails coursedet = new CourseDetails();
	courselist	=	coursedet.getUserCourses(userid);
	/********************************************/
	String user=request.getParameter("UserName");
	String pwd=request.getParameter("oldpassword");
	String role=request.getParameter("Role");
	String email=request.getParameter("Email");
	System.out.println("FinishCourse::"+user);
	System.out.println("FinishCourse pwd::"+pwd);
	System.out.println("FinishCourse role::"+role);
	System.out.println("FinishCourse email::"+email);
	System.out.println("FinishCourse userid::"+userid);
	model.addAttribute("role", role );
	model.addAttribute("user", user );
	model.addAttribute("email", email );
	model.addAttribute("pwd", pwd );
	model.addAttribute("result", result );
	model.addAttribute("catList", catmap );
	//model.addAttribute("courseadd", result);
	//model.addAttribute("result", user);
	model.addAttribute("userid", userid);
	model.addAttribute("usercourselist",courselist);
	String reportingManager = request.getParameter("ReportingManager");
	model.addAttribute("ReportingManager", reportingManager );
	HashMap Ml = new HashMap();
	Ml=ManagerDetails.getManagerList();
	model.addAttribute("ManagerMap", Ml);
	String rmid=request.getParameter("RpMgr_id");
	logger.info("Welcome  SaveCourse**************rmid***"+rmid);
	model.addAttribute("rmid", rmid );
	return "LearningHome";
}	
@RequestMapping(value = "/course/Open", method = RequestMethod.GET)
public String OpenCourse(HttpServletRequest request,Model model) {
String pagenum=request.getParameter("Page");
String coursename=request.getParameter("coursename");
if(coursename!=null)
coursename=coursename.replace("+", " ");


	logger.info("Welcome  OpenCourse**************pagenum***"+pagenum);
	
	/**********************/
	/*@SuppressWarnings("deprecation")
	HttpClient client = new DefaultHttpClient();
	ArrayList<String> s = new ArrayList<String>();
	s.add("Vijay");
	s.add("Priya");
	  String uu="http://adminservice.cfapps.io/rest/admin/s"; 
	  URI cc=null;
	try {
		cc = new URI(uu);
		System.out.println("cc--"+cc);
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    HttpGet request1 = new HttpGet(cc);
    System.out.println("resst Req--"+request);
		    try {
			HttpResponse response = client.execute(request1);
			System.out.println("resst Response--"+response.getEntity().getContent());
			BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			  String line = "";
			  while ((line = rd.readLine()) != null) {
			    System.out.println(line);
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


*/	/****************************/
	String redirectpage="";
if(coursename!=null){
if(coursename.equalsIgnoreCase("core java"))
	redirectpage="CoreJava"+pagenum;
else if(coursename.equalsIgnoreCase("Spring"))
	redirectpage="Spring"+pagenum;
else if(coursename.equalsIgnoreCase("Hibernate"))
	redirectpage="Hibernate"+pagenum;
else
	redirectpage="Page"+pagenum;
	}
	else
	redirectpage="Page"+pagenum;

model.addAttribute("coursename", coursename);
	
return redirectpage;//+"?coursename="+coursename;
	//return "Page"+pagenum;
}
@RequestMapping(value = "/course/save", method = RequestMethod.POST)
public String SaveCourse(HttpServletRequest request,Model model) {
String[] userCourses=request.getParameterValues("UserCourses");
String result="";
int userid=Integer.parseInt(request.getParameter("USERID"));
//String username=request.getParameter("userName");
	logger.info("Welcome  SaveCourse**************userCourses***"+userCourses);
	logger.info("Welcome  SaveCourse**************userid***"+userid);
	String reportingManager = request.getParameter("ReportingManager");
	String rmid=request.getParameter("RpMgr_id");
	logger.info("Welcome  SaveCourse**************rmid***"+rmid);
	System.out.println("Welcome  SaveCourse**************rmid***"+rmid);
	//logger.info("Welcome  SaveCourse**************username***"+username);
	/*for(int i=0;i<userCourses.length;i++)
	{
		logger.info("Welcome  SaveCourse**************userCourses***"+userCourses[i]);
	}*/
	CourseDetails cd = new CourseDetails();
	//result	=	cd.AddCoursetoUser(userCourses, userid);
	/*****************************************************/
	ArrayList<UserCourse> newcourselist = new ArrayList<UserCourse>();
	int catalogid=0;
	int courseid=0;
	for(int i=0;i<userCourses.length;i++)
	{
		logger.info("Welcome  SaveCourse**************userCourses***"+userCourses[i]);
		catalogid=Integer.parseInt((userCourses[i].split("#")[0]).trim());
		courseid=Integer.parseInt(userCourses[i].split("#")[1]);
		
		HttpClient client1 = new DefaultHttpClient();
		String uu1="http://usercourseassignment.cfapps.io/rest/ms2/saveCourseList/"+userid+"/"+catalogid+"/"+courseid+"/"+rmid; 
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
				  result=line;
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

		
		
	}
	/******************************************************/
	
	
	HashMap<Integer, String> catmap=null;
	ArrayList<UserCourse> courselist = new ArrayList<UserCourse>(); 
	Catalog ct= new Catalog();
	catmap=ct.getCatoryList();
	
	
	CourseDetails coursedet = new CourseDetails();
	courselist	=	coursedet.getUserCourses(userid);
/*	//courselist	=	coursedet.getUserCourse(userid);
	*//********************************************//*
	HttpClient client1 = new DefaultHttpClient();
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
//		  ab = new ArrayList<CourseBean>();
		  while ((line = rd.readLine()) != null) {
			  line=line.replace("{", "");
			  line=line.replace("}", "");
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
				  uc.setCompletiondate(beanarray[4]);
				  uc.setStatus(beanarray[5]);
				  uc.setCourse_type(beanarray[6]);
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

*/
	
	/********************************************/
	String user=request.getParameter("UserName");
	String pwd=request.getParameter("oldpassword");
	String role=request.getParameter("Role");
	String email=request.getParameter("Email");
	System.out.println("SaveCourse::"+user);
	System.out.println("SaveCourse pwd::"+pwd);
	System.out.println("SaveCourse role::"+role);
	System.out.println("SaveCourse email::"+email);
	System.out.println("SaveCourse userid::"+userid);
	
	model.addAttribute("ReportingManager", reportingManager );
	HashMap Ml = new HashMap();
	//ManagerDetails 
	Ml=ManagerDetails.getManagerList();
	model.addAttribute("rmid", rmid );
	model.addAttribute("ManagerMap", Ml);
	model.addAttribute("role", role );
	model.addAttribute("user", user );
	model.addAttribute("email", email );
	model.addAttribute("pwd", pwd );
	model.addAttribute("result", result );
	model.addAttribute("catList", catmap );
	model.addAttribute("courseadd", result);
	model.addAttribute("result", result);
	model.addAttribute("userid", userid);
	model.addAttribute("usercourselist",courselist);
	
	SimpleMail sm	=	new SimpleMail();
	try {
		sm.sendRMMail(user,reportingManager,courselist);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return "LearningHome";
}
@RequestMapping(value = "/course/search", method = RequestMethod.POST)
public String SearchCourse(HttpServletRequest request,Model model) {
String pagenum=request.getParameter("Page");
String userid=request.getParameter("USERID");

ArrayList<CourseBean> ab = null;
	logger.info("Welcome  SearchCourse**************pagenum***"+pagenum);
	String cat_id=request.getParameter("Cat_id");
	String course_name=request.getParameter("course_name");
	String rmid=request.getParameter("RpMgr_id");
	logger.info("Welcome  SaveCourse**************rmid***"+rmid);
	logger.info("Welcome  SearchCourse**************cat_id***"+cat_id);
	logger.info("Welcome  SearchCourse**************course_name***"+course_name);
	if(course_name.trim()=="")
		course_name=null;
	else
		course_name=course_name.replace(" ", "+");
		
	CourseDetails cd = new CourseDetails();
	//ab=cd.getCourseList(cat_id, course_name);
	
	/****************************************************/
	
	HttpClient client1 = new DefaultHttpClient();
	String uu1="http://usercourseassignment.cfapps.io/rest/ms2/getCourseList/"+cat_id+"/"+course_name; 
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
		  ab = new ArrayList<CourseBean>();
		  while ((line = rd.readLine()) != null) {
			  System.out.println("__courselist search *************from MS2__"+line);
			  //ab=line;
			  line=line.replace("[", "");
			  line=line.replace("]", "");
			  String[] aa = line.split(",");
			  System.out.println("____courselist length__"+aa.length);
			//  System.out.println("__o element__"+aa[0]);
			  //System.out.println("__1st element__"+aa[1]);
			  //nn.put(aa., value)
			  String[] beanarray=null;
			  for(int i=0;i<aa.length;i++)
			  {
				  System.out.println("____courselist from MS2_aa[...]_"+aa[i]);
				  beanarray=aa[i].split("#");
				  //for(int j=0;j<beanarray.length;j++){
					  CourseBean cb	= new CourseBean();
						cb.setCat_id(beanarray[0]);
						cb.setCat_name(beanarray[1]);
						cb.setCourse_id(beanarray[2]);
						cb.setCourse_name(beanarray[3]);
						ab.add(cb);
				  //}
				  //ab.put();
				  //ab.add(aa[i].);
			  }
		    System.out.println("CourseBean array from MS2__"+ab);
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


	
	
	/****************************************************/
	model.addAttribute("courselist",ab); 
	model.addAttribute("userid",userid);
	HashMap<Integer, String> catmap=null;
	Catalog ct= new Catalog();
	catmap=ct.getCatoryList();
	model.addAttribute("catList", catmap );
	
	String user=request.getParameter("UserName");
	String pwd=request.getParameter("oldpassword");
	String role=request.getParameter("Role");
	String email=request.getParameter("Email");
	System.out.println("uuid in requestCertGeneration::"+user);
	System.out.println("uuid in requestCertGeneration pwd::"+pwd);
	System.out.println("uuid in requestCertGeneration rolw::"+role);
	System.out.println("uuid in requestCertGeneration email::"+email);
	CourseDetails coursedet = new CourseDetails();
	ArrayList<UserCourse> courselist = new ArrayList<UserCourse>();
	courselist	=	coursedet.getUserCourses(Integer.parseInt(userid));
	model.addAttribute("usercourselist",courselist);
	String reportingManager = request.getParameter("ReportingManager");
	model.addAttribute("ReportingManager", reportingManager );
	HashMap Ml = new HashMap();
	Ml=ManagerDetails.getManagerList();
	model.addAttribute("ManagerMap", Ml);
	
	model.addAttribute("role", role );
	model.addAttribute("user", user );
	model.addAttribute("email", email );
	model.addAttribute("pwd", pwd );
	model.addAttribute("rmid", rmid );
	//	model.addAttribute("result", user );
	
	return "LearningHome";
}
/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/course/Add", method = RequestMethod.POST)
	public String addCourse(HttpServletRequest request,Model model) {
		logger.info("Welcome  addCourse ");
		
	/*	Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
	*/	String event = request.getParameter("event");
	String result="";
	String page="";
	logger.info("Welcome addCourse event:"+event);
	//if(event.equalsIgnoreCase("register")){
		String cat_id = request.getParameter("catalog_id");
		String coursename = request.getParameter("coursename");
		String coursedescp = request.getParameter("courseDescp");
		String status		=request.getParameter("coursestatus");
		logger.info("Welcome addCourse coursename:"+coursename);
		logger.info("Welcome addCourse catdescp:"+coursedescp);
		logger.info("Welcome addCourse coursestatus:"+status);
		String user = request.getParameter("user");
		model.addAttribute("result", user );
		//result=InsertCourse(coursename,coursedescp,cat_id);
		/**********************/
		@SuppressWarnings("deprecation")
		HttpClient client1 = new DefaultHttpClient();
		String query="";
		try {
			query=cat_id+"/"+coursename+"/"+coursedescp+"/"+status;
			query=query.replace(" ", "+");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  //String uu="http://restfulexample.cfapps.io/post/rest/admin/catalog/"+catname+"/"+catdescp+"/"+catstatus; 
		String uu="http://adminservices.cfapps.io/rest/admin/course/ADD/"+query;
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
	return page;
	}
	@RequestMapping(value = "/course/update", method = RequestMethod.POST)
	public String updateCourse(HttpServletRequest request,Model model) {
		logger.info("Welcome  updateCourse ");
		
	/*	Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
	*/	String event = request.getParameter("event");
		String result="";
		String page="";
		logger.info("Welcome updateCourse event:"+event); 
	//if(event.equalsIgnoreCase("register")){ 
		String course_id = request.getParameter("Hidcourseid");
		String status		=request.getParameter("Hidcoursestatus");
		logger.info("Welcome updateCourse coursestatus:"+status);
		logger.info("Welcome updateCourse course_id:"+course_id);
		String user = request.getParameter("user");
		model.addAttribute("result", user );
		//result=InsertCourse(coursename,coursedescp,cat_id);
		/**********************/
		@SuppressWarnings("deprecation")
		HttpClient client1 = new DefaultHttpClient();
		String query="";
		try {
			query=course_id+"/"+status;
			//query=query.replace(" ", "+");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  //String uu="http://restfulexample.cfapps.io/post/rest/admin/catalog/"+catname+"/"+catdescp+"/"+catstatus; 
		String uu="http://adminservices.cfapps.io/rest/admin/course/update/"+query;
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
	return page;
	}
	
	public static String InsertCourse(String coursename,String coursedescp,String cat_id)
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
	String query ="INSERT INTO ad_b2664ef2ceb2d68.COURSE(CATALOG_ID,COURSE_NAME,COURSE_DESCP) VALUES(?,?,?)";
	try { 
	//getting database connection to MySQL server 
	dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
	System.out.println("count of stock : "+dbCon);
	//getting PreparedStatment to execute query 
	stmt = dbCon.prepareStatement(query); 
	stmt.setString(1, cat_id);
	stmt.setString(2, coursename.toLowerCase());
	stmt.setString(3, coursedescp);
	stmt.executeUpdate(); 
	//updating the data into memcache.*************/
	CacheUpdate cu = new CacheUpdate(); 
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
	
	return result;
		}
}
