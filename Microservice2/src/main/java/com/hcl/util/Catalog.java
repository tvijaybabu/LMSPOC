package com.hcl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import com.hcl.learning.HomeController;

public class Catalog {
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
public static void main(String args[])
{
	
}
public static HashMap getCatalogList()
{
	/*try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	HashMap<Integer, String> catmap= null;
//Local instance MySQL56
//String dbURL = "jdbc:mysql://localhost:3306/pivot_schema"; 
	/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_55f90d0a45a5495";
	String dbusername="bc7976fa98b14c";
	String dbpassword="5ace9996";
	*/							 
	/*String dbURL = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_55f90d0a45a5495";
	String dbusername="bc7976fa98b14c";
	String dbpassword="5ace9996";*/
	String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_9bac56037f9df2e";
	String dbusername="b4628615f5cc69";
	String dbpassword="8f91d31a";
	String result="";
//System.out.println("count of stock : "+dbURL);
Connection dbCon = null;
Statement stmt = null; 
ResultSet rs = null;
int rowcount=0;
String query ="SELECT CATALOG_ID,CATALOG_NAME FROM ad_9bac56037f9df2e.CATALOG ORDER BY CATALOG_NAME";
System.out.println("query for  catalog box ms2--"+query);
//String query ="INSERT INTO ad_b2664ef2ceb2d68.USER_DET(USER_NAME,PASSWORD,EMAILID) VALUES(?,?,?)";
//logger.info("catalog query.. "+query);
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
catmap= new HashMap<Integer, String>();
while(rs.next()){
	/*logger.info("CATNAME.. "+rs.getString("CATALOG_NAME"));
	logger.info("CATID.. "+rs.getString("CATALOG_ID"));
	logger.info("Welcome catalog query.. "+rs.getInt(1));
	*/
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

return catmap;
	}

}
