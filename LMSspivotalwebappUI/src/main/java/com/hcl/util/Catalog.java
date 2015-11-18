package com.hcl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.learning.HomeController;

public class Catalog {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
public static void main(String args[])
{
	
}
public static HashMap getCatoryList()
{
	HashMap<Integer, String> catmap= null;
	HttpClient client1 = new DefaultHttpClient();
	String uu1="http://usercourseassignment.cfapps.io/rest/ms2/getCatalogList";
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
		  catmap = new HashMap<Integer, String>();
		  while ((line = rd.readLine()) != null) {
			  System.out.println("__catmap from MS2__"+line);
			  line=line.replace("{", "");
			  line=line.replace("}", "");
			  String[] aa = line.split(",");
			  System.out.println("__length__"+aa.length);
			//  System.out.println("__o element__"+aa[0]);
			  //System.out.println("__1st element__"+aa[1]);
			  //nn.put(aa., value)
			  for(int i=0;i<aa.length;i++)
			  {
				  System.out.println("__catmap from MS2_aa[...]_"+aa[i]);
				  catmap.put(Integer.parseInt((aa[i].split("=")[0]).trim()), aa[i].split("=")[1]);
			  }
		    System.out.println("__catmap from MS2__"+line);
		  }

	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    catch (Exception ex)
    { 
    ex.printStackTrace();
//    result="Login Failed";
    }
        return catmap;
}
public static HashMap getCatoryList1()
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
logger.info("catalog query.. "+query);
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
	logger.info("CATNAME.. "+rs.getString("CATALOG_NAME"));
	logger.info("CATID.. "+rs.getString("CATALOG_ID"));
	logger.info("Welcome catalog query.. "+rs.getInt(1));
	
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
