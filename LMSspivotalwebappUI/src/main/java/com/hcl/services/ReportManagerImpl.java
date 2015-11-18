package com.hcl.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.integration.FlashreportClient;
import com.hcl.integration.ReportStatus;
import com.hcl.util.CacheUpdate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URI;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Nicolas Lejeune on 16/06/15.
 */
@Service
@SuppressWarnings("unused")
public class ReportManagerImpl implements ReportManager {
	private static final String CREATE_URL = "https://gateway.flashreport.io/api/v1/report/new";
    private static final String BASE_URL = "https://gateway.flashreport.io/api/v1/report/";
    private String API_KEY;
    private final FlashreportClient client = new FlashreportClient();

    @Override
    public String generateReport() {
       // URI reportUri = client.generateReport(readTestMessage("orders-smallest.json"));
    	/*JSONObject obj = new JSONObject();

        obj.put("Username", "vijay");
        obj.put("Email-id", "tvijaya.b@hcl.com");
        obj.put("balance", new Double(1000.21));
        obj.put("is_vip", new Boolean(true));
        String tt	= obj.toJSONString();
        JSONObject obj1 = new JSONObject();
        obj.put("Username", "Arun");
        obj.put("Email-id", "ArunkumarReddy.h@hcl.com");
        obj1.put("balance", new Double(1000.21));
        obj1.put("is_vip", new Boolean(true));
        JSONArray array = new JSONArray();
        array.add(obj);
        array.add(obj1);
*/  CacheUpdate cu = new CacheUpdate();
        ConcurrentHashMap<String, String> userlist	=	null;
		userlist=cu.GetUserListfromCache();
		Enumeration e = userlist.keys();
		JSONArray array = new JSONArray();
		  while (e.hasMoreElements())
		  {
			  Object k=e.nextElement();
			  Object v=userlist.get(k);
			  
			  JSONObject obj= new JSONObject();
			  System.out.println("userlist elements in jsp.."+k+"::"+userlist.get(k));
			  
			  obj.put("UserName", k);
		      obj.put("Email-ID", v);
		       array.add(obj);
		  }
		  //JSONObject obj= new JSONObject();
		  /*obj.put("UserName","SampleUsertest");
	      obj.put("Email-ID", "Sample@s.com");*/
//	      array.add(obj);
		  System.out.println("userlist in JSON Array .............."+array);
//        URI v=bb.generateReport(array.toJSONString());//,"Title..","template");

    	 URI reportUri = client.generateReport(array.toJSONString());
        String[] pathElements = reportUri.getPath().split("/");

        // return the last part which contains the report UUID
        return pathElements[pathElements.length - 1];

    }
    @Override
    public String generateCertReport(String cat,String course,String dat,String user) {
        // URI reportUri = client.generateReport(readTestMessage("orders-smallest.json"));
     	/*JSONObject obj = new JSONObject();

         obj.put("Username", "vijay");
         obj.put("Email-id", "tvijaya.b@hcl.com");
         obj.put("balance", new Double(1000.21));
         obj.put("is_vip", new Boolean(true));
         String tt	= obj.toJSONString();
         JSONObject obj1 = new JSONObject();
         obj.put("Username", "Arun");
         obj.put("Email-id", "ArunkumarReddy.h@hcl.com");
         obj1.put("balance", new Double(1000.21));
         obj1.put("is_vip", new Boolean(true));
         JSONArray array = new JSONArray();
         array.add(obj);
         array.add(obj1);
 */ /* CacheUpdate cu = new CacheUpdate();
         ConcurrentHashMap<String, String> userlist	=	null;
 		userlist=cu.GetUserListfromCache();
 		Enumeration e = userlist.keys();
 		JSONArray array = new JSONArray();
 		  while (e.hasMoreElements())
 		  {
 			  Object k=e.nextElement();
 			  Object v=userlist.get(k);
 			  
 			  JSONObject obj= new JSONObject();
 			  System.out.println("userlist elements in jsp.."+k+"::"+userlist.get(k));
 			  
 			  obj.put("UserName", k);
 		      obj.put("Email-ID", v);
 		       array.add(obj);
 		  }
 	*/	  JSONObject obj= new JSONObject();
 		  obj.put("UserName",user);
 	      obj.put("Category", cat);
 	     obj.put("Course", course);
 	     obj.put("Course Completed Date", dat);
 	      //array.add(obj);
 		  System.out.println("cert info  in JSON Array .............."+obj);
//         URI v=bb.generateReport(array.toJSONString());//,"Title..","template");

     	 URI reportUri = client.generateReport(obj.toJSONString());
         String[] pathElements = reportUri.getPath().split("/");

         // return the last part which contains the report UUID
         return pathElements[pathElements.length - 1];

     }
    /*private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String basicHeader = "Basic " + Base64.getEncoder().encodeToString((extractApiKey() + ":").getBytes());
        httpHeaders.add("Authorization", basicHeader);
        return httpHeaders;
    }
    private String extractApiKey() throws IllegalStateException {

        if (API_KEY != null) return API_KEY;

        String vcap_services = System.getenv("VCAP_SERVICES");
        if (vcap_services == null) {
         //   throw new IllegalStateException("Unable to get VCAP_SERVICES from environment.");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
           // JsonNode jsonNode = objectMapper.readValue(vcap_services, JsonNode.class);
           // API_KEYAPI_KEY = jsonNode.get("flashreport").get(0).get("credentials").get("apiKey").asText();
            API_KEY="fb614491-22a1-4e00-a457-3b00142431ff";
        //} catch (IOException | NullPointerException e) {
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to extract api key from VCAP_SERVICES. Did you bind a flashreport service to your app?");
        }

        return API_KEY;
    }

    public URI generateReport(String content, String title, String template) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entityWithBody = new HttpEntity<>(content, getHeaders());

        String url = CREATE_URL;
        if (title != null) url += "?title=" + title;
        if (title != null && template != null) url += "&template=" + template;
        if (title == null && template != null) url += "?template=" + template;

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entityWithBody, String.class);

        if (response.getStatusCode().equals(HttpStatus.CREATED)) {
            return response.getHeaders().getLocation();
        } else {
            throw new RuntimeException("Received unexpected response code : " + response.getStatusCode());
        }
    }

*/    @Override
    public ReportStatus getReportStatus(String uuid) {
        return client.getReportStatus(uuid);
    }

    @Override
    public String getStorageUrl(String uuid) {
        return client.getStorageUrl(uuid);
    }

    private String readTestMessage(String fileName) {
        InputStream is = this.getClass().getResourceAsStream("/" + fileName);
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(is, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stringBuilder.append(line).append("\n");
            }
            scanner.close();
        }
        return stringBuilder.toString();
    }
}