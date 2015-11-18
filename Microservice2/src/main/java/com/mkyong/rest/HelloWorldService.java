package com.mkyong.rest;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.hcl.util.Catalog;
import com.hcl.util.CourseBean;
import com.hcl.util.CourseDetails;
 
@Path("/ms2")
public class HelloWorldService {
	@GET
	@Path("/")
	public Response TestConnection() {
 
		String output = "Connected";
		//System.out.println("araylist."+output);
		return Response.status(200).entity(output).build();
	}
/*	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say babu******: " + msg;
		HashMap<Integer, String> catmap= null;
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_55f90d0a45a5495";
		String dbusername="bc7976fa98b14c";
		String dbpassword="5ace9996";
		String result="";
	//System.out.println("count of stock : "+dbURL);
	Connection dbCon = null;
	Statement stmt = null; 
	ResultSet rs = null;
	int rowcount=0;
	String query ="SELECT CATALOG_ID,CATALOG_NAME FROM ad_55f90d0a45a5495.CATALOG ORDER BY CATALOG_NAME";
	System.out.println("query for  catalog box ms2--"+query);
	//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
	//logger.info("catalog query.. "+query);
	try { 
	//getting database connection to MySQL server 
	dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
	//Sy
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
		logger.info("Welcome catalog query.. "+rs.getInt(1));
		
		rowcount++;
		catmap.put(rs.getInt(1), rs.getString(2));
		
	}
	System.out.println("query for  catalog box ms2--"+catmap);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return Response.status(200).entity(output).build();
	}
*/	@GET
	@Path("/getCatalogList")
	public Response getCatalogList() {
 
		String output = "Jersey say : " ;
		Catalog c = new Catalog();
		HashMap<Integer, String> catmap= c.getCatalogList();
		System.out.println("Jersey say catmap: "+catmap );
		if(catmap==null)
			output=null;
			else
				output=catmap.toString();
		
		return Response.status(200).entity(output).build();
 
	}
	@GET
	@Path("/getCourseList/{param1}/{param2}")
	public Response getCourseList(@PathParam("param1") int cat_id,@PathParam("param2") String course_name) {
 
		String output = "Jersey say : " ;
		System.out.println("Jersey say cat_id: "+cat_id );
		System.out.println("Jersey say course_name: "+course_name );
		course_name=course_name.replace("+", " ");
		CourseDetails cd = new CourseDetails();
		ArrayList<String> cb= cd.getCourseList(cat_id,course_name);
		System.out.println("Jersey say ArrayList: "+cb );
		if(cb==null)
			output=null;
		else
			output=cb.toString();
		System.out.println("Jersey say ArrayList: "+output);
		return Response.status(200).entity(output).build();
 
	}
	@GET
	@Path("/getPendingCourseList/{param1}")
	public Response getCourseList(@PathParam("param1") int RepMgr_id) {
 
		String output = "Jersey say : " ;
		System.out.println("Jersey say RepMgr_id: "+RepMgr_id );
		CourseDetails cd = new CourseDetails();
		ArrayList<String> pendinglist= cd.getPendingCourseList(RepMgr_id);
		System.out.println("Jersey say ArrayList: "+pendinglist );
		//System.out.println("Jersey say ArrayList size**: "+pendinglist.size() );
		if(pendinglist==null)
			output=null;
		else if (pendinglist.size()==0)
			output=null;
		else
			output=pendinglist.toString();
		System.out.println("Jersey say ArrayList: "+output);
		return Response.status(200).entity(output).build();
 
	}

	@GET
	@Path("/saveCourseList/{param1}/{param2}/{param3}/{param4}")
	public Response saveCourseList(@PathParam("param1") int user_id,@PathParam("param2") int cat_id,@PathParam("param3") int course_id,@PathParam("param4") int RepMgrid) {
 
		String output = "" ;
		System.out.println("saveCourseList cat_id: "+cat_id );
		System.out.println("saveCourseList course_id: "+course_id );
		System.out.println("saveCourseList uerid: "+user_id );
		System.out.println("saveCourseList RepMgrid: "+RepMgrid );
		CourseDetails cd = new CourseDetails();
		//ArrayList<String> cb= 
		output=cd.AddCoursetoUser(user_id,cat_id,course_id,RepMgrid);
		System.out.println("Jersey say ArrayList: "+output );
		
		return Response.status(200).entity(output).build();
 
	}
 
	@GET
	@Path("/course/finish/{param1}/{param2}")
	public Response FinishCourse(@PathParam("param1") int user_id,@PathParam("param2") int course_id) {
 
		String output = "" ;
		System.out.println("FinishCourse user_id: "+user_id );
		System.out.println("FinishCourse course_id: "+course_id );
		//System.out.println("saveCourseList uerid: "+user_id );
		CourseDetails cd = new CourseDetails();
		//ArrayList<String> cb= 
		output=cd.UserCourseFinish(user_id,course_id);
		System.out.println("Jersey say ArrayList: "+output );
		
		return Response.status(200).entity(output).build();
 
	}
 
	@GET
	@Path("/getUserCourseList/{param1}")
	public Response getCourseName(@PathParam("param1") int user_id){
 
		String output = "" ;
		System.out.println("saveCourseList course_id: "+user_id );
		
		CourseDetails cd = new CourseDetails();
		ArrayList ll=cd.getUserCourseList(user_id);
		//ArrayList<String> cb= 
		//output=cd.AddCoursetoUser(user_id,cat_id,course_id);
		System.out.println(" getUserCourseList size..*******MS2..: "+ll.size() );
		if(ll==null || ll.size()==0)
			output=null;
		else
			output=ll.toString();
		System.out.println(" getUserCourseList*******MS2..: "+ll );
		
		return Response.status(200).entity(output).build();
 
	}
	@GET
	@Path("/managerApprove/{param1}/{param2}/{param3}/{param4}")
	public Response managerApprove(@PathParam("param1") int user_id,@PathParam("param2") int course_id,@PathParam("param3") String rm_status,@PathParam("param4") String Reason) {
 
		String output = "" ;
		System.out.println("managerApprove user_id: "+user_id );
		System.out.println("managerApprove course_id: "+course_id );
		System.out.println("managerApprove rm_status: "+rm_status );
		System.out.println("managerApprove Reason: "+Reason );
		Reason=Reason.replace("+", " ");
		CourseDetails cd = new CourseDetails();
		//ArrayList<String> cb= 
		output=cd.UserCourseApproval(user_id,course_id,rm_status,Reason);
		System.out.println("Jersey say ArrayList: "+output );
		
		return Response.status(200).entity(output).build();
 
	}

}