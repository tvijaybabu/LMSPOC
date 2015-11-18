package com.hcl.integration;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
//import org.springframework.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

/**;
 * Simple client to send report generation requests to flashreport.io, as well as getting information
 * about a report request.
 * <p>
 * The flashreport api key is extracted from the VCAP_SERVICES environment variable, which is set by Cloud Foundry.
 * <p>
 * Created by Nicolas Lejeune on 18/06/15.
 */

public class FlashreportClient {

    private static final String CREATE_URL = "https://gateway.flashreport.io/api/v1/report/new";
    private static final String BASE_URL = "https://gateway.flashreport.io/api/v1/report/";

    private String API_KEY;


    /**
     * Request the generation of a report with the specified content.
     *
     * @param content json or xml data
     * @return the URI representing the report
     */
    public URI generateReport(String content) {
        return generateReport(content, null, null);
    }

    /**
     * Request the generation of a report with the specified content and title.
     *
     * @param content json or xml data
     * @param title   a title for your report
     * @return the URI representing the report
     */
    public URI generateReport(String content, String title) {
        return generateReport(content, title, null);
    }
    public static void main(String args[]){
    	FlashreportClient bb = new FlashreportClient();
    	
    	JSONObject obj = new JSONObject();

        obj.put("name", "foo");
        obj.put("num", new Integer(100));
        obj.put("balance", new Double(1000.21));
        obj.put("is_vip", new Boolean(true));
        String tt	= obj.toJSONString();
        JSONObject obj1 = new JSONObject();
        obj1.put("name", "foo");
        obj1.put("num", new Integer(100));
        obj1.put("balance", new Double(1000.21));
        obj1.put("is_vip", new Boolean(true));
        JSONArray array = new JSONArray();
        array.add(obj);
        array.add(obj1);
        
        URI v=bb.generateReport(array.toJSONString());//,"Title..","template");
        System.out.print(obj);
    	System.out.println(bb.getReportStatus(v).getDownloadUrl());
    	System.out.println("URI::"+v);
    	System.out.println("headers::"+bb.getHeaders()); 
    }

    /**
     * Request the generation of a report with the specified content, title and template.
     * The template must have been previously registered in the dashboard.
     *
     * @param content json or xml data
     * @param title   a title for your report
     * @return the URI representing the report
     */
    public URI generateReport(String content, String title, String template) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entityWithBody = new HttpEntity<>(content, getHeaders());

        String url = CREATE_URL;
        if (title != null) url += "?title=" + title;
        if (title != null && template != null) url += "&template=" + template;
        if (title == null && template != null) url += "?template=" + template;

     //ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entityWithBody, String.class);
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entityWithBody, String.class);//,null);

        if (response.getStatusCode().equals(HttpStatus.CREATED)) {
            return response.getHeaders().getLocation();
        } else {
            throw new RuntimeException("Received unexpected response code : " + response.getStatusCode());
        }
    }
    public String getStorageUrl(String uuid) {
        HttpEntity entity = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + uuid + "/storage", HttpMethod.GET, entity, String.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        } else {
            throw new RuntimeException("Received unexpected response code : " + response.getStatusCode());
        }
    }

    public ReportStatus getReportStatus(String uuid) {
        try {
            return getReportStatus(new URI(BASE_URL + uuid));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Unable to construct a uri with report uuid [" + uuid + "]");
        }
    }

    public ReportStatus getReportStatus(URI reportUri) {
        HttpEntity entity = new HttpEntity<>(getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(reportUri, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            ObjectMapper mapper = new ObjectMapper();
            ReportStatus status;
            try {
                status = mapper.readValue(response.getBody(), ReportStatus.class);
            } catch (IOException e) {
                throw new RuntimeException("Could not parse response body : " + response.getBody());
            }
            return status;
        } else {
            throw new RuntimeException("Received unexpected response code : " + response.getStatusCode());
        }
    }

    private String extractApiKey() throws IllegalStateException {

        if (API_KEY != null) return API_KEY;

        String vcap_services = System.getenv("VCAP_SERVICES");
        if (vcap_services == null) {
         //   throw new IllegalStateException("Unable to get VCAP_SERVICES from environment.");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(vcap_services, JsonNode.class);
            System.out.println("API KEY before in MS3 *****"+API_KEY);
            //API_KEY = jsonNode.get("flashreport").get(0).get("credentials").get("apiKey").asText();
            //API_KEY="4f3bf88a-c4e7-49b0-9350-95790f0f3fc5";
            API_KEY="ab84ad02-abc5-431a-851d-1c229c300666"; 
            System.out.println("API KEY***new**"+API_KEY);
            //working .......API_KEY="50f5dcdb-9255-4750-8c3d-d51efacb9683";
            
        //} catch (IOException | NullPointerException e) {
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to extract api key from VCAP_SERVICES. Did you bind a flashreport service to your app?");
        }
        return API_KEY;
    } 


    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String basicHeader = "Basic " + Base64.getEncoder().encodeToString((extractApiKey() + ":").getBytes());
        httpHeaders.add("Authorization", basicHeader);
        return httpHeaders;
    }


}