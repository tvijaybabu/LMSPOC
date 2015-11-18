package com.hcl.util;
import java.io.IOException;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.hcl.learning.HomeController;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.auth.AuthDescriptor;

public class CacheUpdate {
	static MemcachedClient mc=null;
	static AuthDescriptor ad = null;

	private static final Logger logger = LoggerFactory.getLogger(CacheUpdate.class);
	/*	static{
		   String memcachier_servers = "";
		    String memcachier_username = "";
		    String memcachier_password = "";
		 
		   memcachier_servers = "mc5.dev.ec2.memcachier.com:11211";
		   AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },new PlainCallbackHandler(memcachier_username,memcachier_password));

		 try {
			 mc = new MemcachedClient(new ConnectionFactoryBuilder().setProtocol(ConnectionFactoryBuilder.Protocol.BINARY).setAuthDescriptor(ad).build(),AddrUtil.getAddresses(memcachier_servers));
			 System.out.println("mc value in STATIC............::"+mc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
*/	
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
	public ConcurrentHashMap<String, String> GetUserListfromCache(){
		ConcurrentHashMap<String, String> userlist	=	null;
		try {
		    	mc=getMCClient();
		     // if(operation==0)
		    	System.out.println("mc value............::"+mc);
		      userlist=  (ConcurrentHashMap<String, String>)mc.get("UserList");
		      System.out.println("userlist from cache* for admin......::"+mc.get("UserList"));
		      
		}
		catch(Exception e)
		{
			e.printStackTrace();
			 System.out.println("Exception while getting userlist from cache* for admin......::");
		}
		return userlist;
	}
	
	//public String updateUserListCache(ConcurrentHashMap<String, String> userlist,int operation)
	public String updateUserListCache(String username ,String password,String emailid)
	{String result="Success";
	 String vcap_services = System.getenv("VCAP_SERVICES");
	   String memcachier_servers = "";
	    String memcachier_username = "";
	    String memcachier_password = "";
	   ConcurrentHashMap<String, String> userlist;
	    if (vcap_services != null && vcap_services.length() > 0) {
	        // parsing memcachier credentials
	     /*   JsonRootNode root = new JdomParser().parse(vcap_services);
	        JsonNode memcachierNode = root.getNode("memcachier");
	        JsonNode credentials = memcachierNode.getNode(0).getNode("credentials");
	        memcachier_servers = credentials.getStringValue("servers");
	        memcachier_username = credentials.getStringValue("username");
	        memcachier_password = credentials.getStringValue("password");*/
	    	
	    }
	    /*memcachier_servers = "mc5.dev.ec2.memcachier.com:11211";
	    memcachier_username = "6bf938";
	    memcachier_password = "ef429855122cd0eff1257e4c0993182b";
	    AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
	        new PlainCallbackHandler(memcachier_username,
	            memcachier_password));*/

	    try {
	      /*MemcachedClient mc = new MemcachedClient(
	          new ConnectionFactoryBuilder()
	              .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
	              .setAuthDescriptor(ad).build(),
	          AddrUtil.getAddresses(memcachier_servers));*/
	    	mc=getMCClient();
	     // if(operation==0)
	    	System.out.println("mc value............::"+mc);
	      userlist=  (ConcurrentHashMap<String, String>)mc.get("UserList");
	      System.out.println("userlist from cache*::"+mc.get("UserList"));
	      if(userlist==null)
	    	  userlist	=	new ConcurrentHashMap<String, String>();
	      
	      userlist.put(username, emailid);
	      mc.set("UserList",864000, userlist);//userlist contains <username,emailid>
	      
	      
	      userlist=  (ConcurrentHashMap<String, String>)mc.get("User");
	      System.out.println("user from cache::"+userlist);
	      if(userlist==null)
	    	  userlist	=	new ConcurrentHashMap<String, String>();
	      userlist.put(username, password);
	      mc.set("User",864000, userlist);//userlist contains <username,password>
	      
	      //mc.set("Fool",2, "vijay");
	      System.out.println("userlist from cache after updating--"+mc.get("UserList"));
	      System.out.println("user from cache after updating--"+mc.get("User"));
	      //System.out.println(mc.get("Fool"));
	    } catch (Exception ioe) {
	    	result="Failure";
	      System.err.println("Couldn't create a connection to MemCachier: \nIOException "
	              + ioe.getMessage());
	    }
	 
	return result;
	}
  public static void updateUserListfromDB() {
	  String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_b2664ef2ceb2d68";
		String dbusername="b71a8affc1f50f";
		String dbpassword="ac84a196";
		String result="";
		//System.out.println("count of stock : "+dbURL);
		Connection dbCon = null;
		Statement stmt = null; 
		ResultSet rs = null;
		int rowcount=0;
		String query ="SELECT USER_NAME,EMAILID ,PASSWORD FROM ad_b2664ef2ceb2d68.USER_DET";
		//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
		logger.info("Welcome updateUserListfromDB checkuer query.. "+query);
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
		String username="";
		String email="";
		String password="";
		CacheUpdate cu = new CacheUpdate();
		while(rs.next()){
			rowcount++;
			username=rs.getString("USER_NAME");
			email=rs.getString("EMAILID");
			password=rs.getString("PASSWORD");
			cu.updateUserListCache(username ,password,email);
		}
		//SimpleMail sm	=	new SimpleMail();
		//sm.sendMail(email);
		/*if(rowcount>0)
		{
			result= username+"#"+role+"#"+email+"#"+userid;
		}
		else
			result= "User does not exists..";
		*/
		//result= "User reistered Successfully..."; 
		}
		catch (SQLException ex)
		{ 
		 ex.printStackTrace();
		//Logger.getLogger(CollectionTest.class.getName()).log(Level.SEVERE, null, ex);
		 result="updateUserListfromDB failed";
		}
		catch (Exception ex)
		{ 
		ex.printStackTrace();
		result="updateUserListfromDB failed";
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
		
  }

	  
    }