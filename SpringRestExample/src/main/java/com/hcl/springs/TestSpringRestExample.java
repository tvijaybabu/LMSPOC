package com.hcl.springs;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
 



import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
 



import com.hcl.spring.controller.UserRestURIConstants;
import com.hcl.spring.model.Employee;
 
public class TestSpringRestExample {
 
    public static final String SERVER_URI = "http://localhost:8081/SpringRestExample";
     
    public static void main(String args[]){
         
/*        testGetDummyEmployee();
        System.out.println("*****");
        testCreateEmployee();
        System.out.println("*****");
        testGetEmployee();
        System.out.println("*****");
        testGetAllEmployee();
*/
    	//testCreateEmployee();
    	Employee emp = new Employee();
        emp.setId(1);emp.setName("Pankaj Kumar");
    	createEmployee(emp);
    }
 
    private static void testGetAllEmployee() {
        RestTemplate restTemplate = new RestTemplate();
        //we can't get List<Employee> because JSON convertor doesn't know the type of 
        //object in the list and hence convert it to default JSON object type LinkedHashMap
        List<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI+UserRestURIConstants.GET_ALL_USER, List.class);
        System.out.println(emps.size());
        for(LinkedHashMap map : emps){
            System.out.println("ID="+map.get("id")+",Name="+map.get("name")+",CreatedDate="+map.get("createdDate"));;
        }
    }
 
    private static void testCreateEmployee() {
        RestTemplate restTemplate = new RestTemplate();
        Employee emp = new Employee();
        emp.setId(1);emp.setName("Pankaj Kumar");
       // Employee response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP, emp, Employee.class);
        String response = restTemplate.postForObject(SERVER_URI+UserRestURIConstants.CREATE_USER, emp, String.class);
        System.out.println("response==ID="+response);
      //  printEmpData(response);
    }
    /*private static void testCreateEmployee1() {
        RestTemplate restTemplate = new RestTemplate();
        Employee emp = new Employee();
        emp.setId(1);emp.setName("Pankaj Kumar");
       // Employee response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP, emp, Employee.class);
        //String response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP1+"/restuser/pwd/email/role/11/vijay", emp, String.class);
        String response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP1, emp, String.class);
        System.out.println("response==ID="+response);
      //  printEmpData(response);
    }
 
 */   private static void testGetEmployee() {
        RestTemplate restTemplate  = new RestTemplate();
        Employee emp = restTemplate.getForObject(SERVER_URI+"/rest/emp/1", Employee.class);
        printEmpData(emp);
    }
 
    private static void testGetDummyEmployee() {
        RestTemplate restTemplate = new RestTemplate();
        Employee emp = restTemplate.getForObject(SERVER_URI+UserRestURIConstants.DUMMY_USER, Employee.class);
        printEmpData(emp);
    }
     
    public static void printEmpData(Employee emp){
        System.out.println("ID="+emp.getId()+",Name="+emp.getName()+",CreatedDate="+emp.getCreatedDate());
    }
    public static String createEmployee(Employee emp) {
	//	logger.info("Start createEmployee.");
		String result="GGG***";
		String dbURL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/ad_467de3d0d776395";
		String dbusername="b73452b3df25cc";
		String dbpassword="7b2fbfb7";
		Connection dbCon = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null; 
		try { 
		//getting database connection to MySQL server 
		String query ="INSERT INTO ad_467de3d0d776395.USER_DET(USER_NAME,PASSWORD,EMAILID,ROLE,RM_ID,RM_EMAILID) VALUES(?,?,?,?,?,?)";
		dbCon = DriverManager.getConnection(dbURL, dbusername, dbpassword);
		stmt = dbCon.prepareStatement(query);
		stmt.setString(1, "USER");
		stmt.setString(2, "pwd");
		stmt.setString(3, "email");
		stmt.setString(4, "role");
		stmt.setInt(5, 333);
		stmt.setString(6, "vIJAY");
		stmt.executeUpdate(); 
		System.out.println("count of stock : "+dbCon);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
		//return emp;
	}
}