package com.hcl.learning;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.hcl.integration.ReportStatus;
//import com.hcl.services.ReportManager;

import com.hcl.services.ReportManager;
import com.hcl.util.CacheUpdate;
import com.hcl.util.Catalog;
import com.hcl.util.CourseBean;
import com.hcl.util.CourseDetails;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.jetty.server.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

/**
 * Created by Nicolas Lejeune on 16/06/15.
 */
@Controller
@SuppressWarnings("unused")
public class ReportController {

    @Autowired
    ReportManager reportManager;

    @RequestMapping(value = "/report/new", method = {RequestMethod.GET, RequestMethod.POST})
    public String requestReportGeneration(Model model) {
        //String uuid = reportManager.generateReport();
    	String uuid="";

        String uu="http://adminservices.cfapps.io/rest/admin/getUserReportFromCache";
		URI cc=null;
		try {
			cc = new URI(uu);
			System.out.println("cc--"+cc);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //HttpGet request1 = new HttpGet(cc);
		HttpPost request1 = new HttpPost(cc);
		HttpClient client = new DefaultHttpClient();
	    System.out.println("resst Req--"+request1);
	    try {
			HttpResponse response = client.execute(request1);
			System.out.println("resst Response--"+response.getEntity().getContent());
			BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			  String line = "";
			  //userlist = new ConcurrentHashMap<String, String>();
			  while ((line = rd.readLine()) != null) {
				  System.out.println("__1st element__"+line);
				  //nn.put(aa., value)
				  uuid=line;
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


        System.out.println("uuid in requestReportGeneration::"+uuid);

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodName(ReportController.class, "viewReportStatus", uuid).build();

        model.addAttribute("REPORT_UUID", uuid);
        model.addAttribute("REPORT_STATUS_URL", uriComponents.encode().toUri().toString());
        model.addAttribute("REPORT_DOWNLOAD_URL", uriComponents.encode().toUri().toString() + "/download");
        ConcurrentHashMap<String, String> userlist	=	null;
    	CacheUpdate cu = new CacheUpdate();
		userlist=cu.GetUserListfromCache();	
		System.out.println("after getting userlist  from  cache Report creation....: "+userlist);
		//Strin user = 
		model.addAttribute("userList", userlist );
		model.addAttribute("status", "Report created succesfully" );
		model.addAttribute("uuid", uuid );
		model.addAttribute("user", "Admin" );
		
		CourseDetails cd = new CourseDetails();
		ArrayList<CourseBean> cb = new ArrayList<CourseBean>();
		cb	=	cd.getCourseList();
		System.out.println("cb in controller "+cb);
		model.addAttribute("courseInfoList", cb );
		
		Catalog c = new Catalog();
		HashMap<Integer, String> catmap= null;
		catmap	=	c.getCatoryList();
		System.out.println("catmap from util...: "+catmap);
		model.addAttribute("catList", catmap );
		//return "created";
        return "AdminPage";
    }
    @RequestMapping(value = "/report/cert", method = {RequestMethod.GET, RequestMethod.POST})
    public String requestCertGeneration(HttpServletRequest request,Model model) {
    	String user=request.getParameter("UserName");
		String pwd=request.getParameter("password");
		String role=request.getParameter("Role");
		String email=request.getParameter("Email");
    	System.out.println("uuid in requestCertGeneration::"+user);
    	System.out.println("uuid in requestCertGeneration pwd::"+pwd);
    	System.out.println("uuid in requestCertGeneration rolw::"+role);
    	System.out.println("uuid in requestCertGeneration email::"+email);
        String uuid = reportManager.generateCertReport("Categ1ory","Course1","12-8-2015","BABU");
        System.out.println("uuid in requestCertGeneration::"+uuid);

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodName(ReportController.class, "viewReportStatus", uuid).build();

        model.addAttribute("REPORT_UUID", uuid);
        model.addAttribute("REPORT_STATUS_URL", uriComponents.encode().toUri().toString());
        model.addAttribute("REPORT_DOWNLOAD_URL", uriComponents.encode().toUri().toString() + "/download");
        
        HashMap<Integer, String> catmap=null;
        Catalog c = new Catalog();
        catmap= 	c.getCatoryList();
		//tmap=getCatoryList();
				model.addAttribute("catList", catmap );
				
				model.addAttribute("role", role );
				model.addAttribute("user", user );
				model.addAttribute("email", email );
				model.addAttribute("pwd", pwd );
			//	model.addAttribute("result", result );
        /*ConcurrentHashMap<String, String> userlist	=	null;
    	CacheUpdate cu = new CacheUpdate();
		userlist=cu.GetUserListfromCache();	
		System.out.println("after getting userlist  from  cache Report creation....: "+userlist);
		model.addAttribute("userList", userlist );
		model.addAttribute("status", "Report created succesfully" );*/
		//model.addAttribute("uuid", uuid );
		//return "created";
				return "LearningHome";
    }

    @RequestMapping("/report/{uuid}")
    @ResponseBody
    public ReportStatus viewReportStatus(@PathVariable String uuid) {
        return reportManager.getReportStatus(uuid);
    }

    @RequestMapping("/report/{uuid}/download")
    @ResponseBody
    public ModelAndView downloadReport(@PathVariable String uuid) {
        return new ModelAndView("redirect:" + reportManager.getStorageUrl(uuid));
    }

}