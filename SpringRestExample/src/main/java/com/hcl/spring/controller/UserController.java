package com.hcl.spring.controller;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hcl.dao.UserDAO;
import com.hcl.spring.model.User;
import com.hcl.util.SimpleMail;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
/*import com.hcl.util.CacheUpdate;
import com.hcl.util.SimpleMail;
import com.journaldev.spring.model.Employee;
import com.journaldev.spring.model.User;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	//Map to store employees, ideally we should use database
	Map<Integer, User> empData = new HashMap<Integer, User>();
	
	@RequestMapping(value = UserRestURIConstants.DUMMY_USER, method = RequestMethod.GET)
	public @ResponseBody User getDummyEmployee() {
		logger.info("Start getDummyEmployee");
		User emp = new User();
		emp.setId(9999);
		//emp.setName("Dummy");
		emp.setCreatedDate(new Date());
		empData.put(9999, emp);
		return emp;
	}
	
/*	@RequestMapping(value = EmpRestURIConstants.GET_EMP, method = RequestMethod.GET)
	public @ResponseBody Employee getEmployee(@PathVariable("id") int empId) {
		logger.info("Start getEmployee. ID="+empId);
		
		return empData.get(empId);
	}
	
*//*	@RequestMapping(value = EmpRestURIConstants.GET_ALL_EMP, method = RequestMethod.GET)
	public @ResponseBody List<Employee> getAllEmployees() {
		logger.info("Start getAllEmployees.");
		List<Employee> emps = new ArrayList<Employee>();
		Set<Integer> empIdKeys = empData.keySet();
		for(Integer i : empIdKeys){
			emps.add(empData.get(i));
		}
		return emps;
	}

*/	
	@RequestMapping(value = UserRestURIConstants.UPDATE_USER, method = RequestMethod.POST)
	//public @ResponseBody Employee createEmployee(@RequestBody Employee emp) {
	public @ResponseBody String updateUser(@RequestBody User user) {
		logger.info("Start createEmployee.");
		String result="";
		int count=0;
		result=UserDAO.updateuserdet(user);
		return result;
	}
	@RequestMapping(value = UserRestURIConstants.CREATE_USER, method = RequestMethod.POST)
	//public @ResponseBody Employee createEmployee(@RequestBody Employee emp) {
	public @ResponseBody String createUser(@RequestBody User user) {
		logger.info("Start createEmployee.");
		String result="";
		int count=0;
		String usercheck=UserDAO.CheckUser(user.getUsername(),user.getPassword());
		String userid	= usercheck.split("#")[0];
		//UserDAO ud= new UserDAO();
		System.out.println("usercheck---==>"+usercheck);
		//result	= result.split("#")[0];
		if(userid.equalsIgnoreCase(user.getUsername()))
		{
			result	="User Already Exists..";
		}
		else{
			count=UserDAO.insertUser(user);
			System.out.println("count frm insert user==>"+count);
			if(count==1){
				result= "User registered Successfully...";
			}
			else
				result= "User registeration failed";
			}
		System.out.println("result==>"+result);
		return result;
		//return emp;
	}
	/*@RequestMapping(value = EmpRestURIConstants.CREATE_EMP, method = RequestMethod.POST)
	//public @ResponseBody Employee createEmployee(@RequestBody Employee emp) {
	public @ResponseBody String createEmployee(@RequestBody Employee emp) {
		logger.info("Start createEmployee.");
		String result="GGG";
		return result;
		//return emp;
	}*/
/*	@RequestMapping(value = EmpRestURIConstants.DELETE_EMP, method = RequestMethod.PUT)
	public @ResponseBody Employee deleteEmployee(@PathVariable("id") int empId) {
		logger.info("Start deleteEmployee.");
		Employee emp = empData.get(empId);
		empData.remove(empId);
		return emp;
	} 
*/	/*@RequestMapping(value = EmpRestURIConstants.CREATE_EMP1, method = RequestMethod.POST)
	public @ResponseBody String InsertUser(@RequestBody Employee emp)
	//public @ResponseBody String InsertUser(@PathVariable("param1") String user,@PathVariable("param2") String pwd,@PathVariable("param3") String email,@PathVariable("param4") String role,@PathVariable("param5") String rmid,@PathVariable("param6") String reportingManager)
	//public static String InsertUser(String username,String password,String email,String role,String rmid,String reportingManager)
	{
		System.out.println("entered insert user: ");
		String result="";
		//String usercheck=ChecktUser(username,password);
		String usercheck="";
	//	 user	= usercheck.split("#")[0];	
		//result	= result.split("#")[0];
		//if(user.equalsIgnoreCase(username))
		{
			result	="User Already Exists..";
		}
		//else
		{
			//Local instance MySQL56
			//String dbURL = "jdbc:mysql://localhost:3306/pivot_schema"; 
				String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
				String dbusername="b71a8affc1f50f";
				String dbpassword="ac84a196";
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
			stmt.setString(1, "USER");
			stmt.setString(2, "pwd");
			stmt.setString(3, "email");
			stmt.setString(4, "role");
			stmt.setString(5, "rmid");
			stmt.setString(6, "vIJAY");
			stmt.executeUpdate(); 
			System.out.println("Before updating cache...: ");
			//updating the data into memcache.************
			CacheUpdate cu = new CacheUpdate(); 
			cu.updateUserListCache(username.toLowerCase(),password,email);
			
			//updating the data into memcache.**********
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
*
*/
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
}
