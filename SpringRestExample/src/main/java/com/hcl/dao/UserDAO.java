package com.hcl.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import net.codejava.spring.model.Contact;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;

import com.hcl.spring.controller.UserController;

import com.hcl.spring.model.User;
import com.hcl.util.SimpleMail;
public class UserDAO {
	private static JdbcTemplate jdbcTemplate;
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
public UserDAO(DataSource dataSource) {
	jdbcTemplate = new JdbcTemplate(dataSource);;
	
	System.out.println("dataSource==>"+dataSource);
	System.out.println("jdbcTemplate==>"+jdbcTemplate);
}
public static JdbcTemplate getjdbctemplate() {
	return jdbcTemplate; 
}
public static String CheckUser(String username,String password)
{

	String result="";
	
	int rowcount=0;
	String query ="SELECT USER_ID,USER_NAME,ROLE,EMAILID,RM_ID,RM_EMAILID FROM ad_467de3d0d776395.USER_DET WHERE USER_NAME='"+username.toLowerCase()+"' AND PASSWORD='"+password+"'";
	
	logger.info("Welcome checkuer query.. "+query);
	try { 
	
	System.out.println("before count of records using jdbctemplate.."+jdbcTemplate);
	List<User> users = new ArrayList<User>();
	int cnt=0;
	System.out.println("Welcome checkuer query.. "+query);
	List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
	System.out.println("list size before loop::"+rows.size());
	String role="";
	String email="";
	String userid="";
	String rmid="";
	String rmEmailid="";
	for (Map row : rows) {
		User user = new User();
		User us = new User();
		us.setUsername((String)row.get("USER_NAME"));
		System.out.println("username::"+us.getUsername());
		rowcount++;
		role	=	(String)row.get("ROLE");
		email	=	(String)row.get("EMAILID");
		userid	=	String.valueOf(""+row.get("USER_ID"));
		rmid	=	String.valueOf(""+row.get("RM_ID"));
		 rmEmailid=	(String)row.get("RM_EMAILID");
		//cnt++;
	}
	System.out.println("count of records using jdbctemplate************.."+rowcount);
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
	catch (DataAccessException ex)
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
	System.out.println("result before returning using jdbctemplate.."+result);
	return result;
	}
public static int insertUser(User user)
{
	int count=0;
	String result="";
	try { 
	//getting database connection to MySQL server 
	String query ="INSERT INTO ad_467de3d0d776395.USER_DET(USER_NAME,PASSWORD,EMAILID,ROLE,RM_ID,RM_EMAILID) VALUES(?,?,?,?,?,?)";
	//dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
	count=jdbcTemplate.update(query,new Object[]{user.getUsername(),user.getPassword(),user.getEmailid(),user.getRole(),user.getRm_id(),user.getRm_Mgr_email()});
	System.out.println("count in dao==>"+count);
	result= "User registered Successfully...";
	SimpleMail sm = new SimpleMail();
	sm.sendMail(user.getEmailid());
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
	return count;
}
public static String updateuserdet(User user){
	logger.info("  Welcome updateuserdet ");
	int count=0;
	String result="";
	try { 
	//getting database connection to MySQL server 
	//String query ="INSERT INTO ad_467de3d0d776395.USER_DET(USER_NAME,PASSWORD,EMAILID,ROLE,RM_ID,RM_EMAILID) VALUES(?,?,?,?,?,?)";
	String query ="UPDATE ad_467de3d0d776395.USER_DET SET PASSWORD='"+user.getPassword()+"',EMAILID='"+user.getEmailid()+"',ROLE='"+user.getRole()+"',RM_ID="+user.getRm_id()+",RM_EMAILID='"+user.getRm_Mgr_email()+"' WHERE USER_NAME='"+user.getUsername()+"'";
	//dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
	count=jdbcTemplate.update(query);//,new Object[]{user.getUsername(),user.getPassword(),user.getEmailid(),user.getRole(),user.getRm_id(),user.getRm_Mgr_email()});
	System.out.println("count in dao==>"+count);
	if(count==1)
	result= user.getUsername();
	
	}
	catch (DataAccessException ex)
	{ 
	 ex.printStackTrace();
	//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
	 result="User updation Failed";
	}
	catch (Exception ex)
	{ 
	ex.printStackTrace();
	result="User updation Failed";
	}
	return result;
	}
}
