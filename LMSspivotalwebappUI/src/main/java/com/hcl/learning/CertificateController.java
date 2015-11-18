package com.hcl.learning;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hcl.util.CacheUpdate;
import com.hcl.util.SimpleMail;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
/**
 * Handles requests for the registering new user.
 */
@Controller

public class CertificateController {
	@Autowired 
	private HttpSession httpSession;
	public static final Font[] FONT = new Font[4];
    static {
        FONT[0] = new Font(FontFamily.HELVETICA, 24);
        FONT[1] = new Font(FontFamily.HELVETICA, 18);
        FONT[2] = new Font(FontFamily.HELVETICA, 14);
        FONT[3] = new Font(FontFamily.HELVETICA, 50, Font.BOLD);
    }
 
private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/user/cert", method = RequestMethod.GET)
	public void printCertificate(HttpServletRequest request,HttpServletResponse response,Model model) {
try{
		logger.info("Welcome printCertificate ");
		String catname=request.getParameter("catname");
		String coursename=request.getParameter("coursename");
		String compdate=request.getParameter("compdate");
		String user=request.getParameter("user");
        logger.info("Welcome printCertificate catname"+catname);
        logger.info("Welcome printCertificate coursename"+coursename);
        logger.info("Welcome printCertificate compdate"+compdate);
        logger.info("Welcome printCertificate user"+user);
		/*ApplicationContext appContext = 
		    	   new ClassPathXmlApplicationContext(new String[] {"If-you-have-any.xml"});
<img src="/resources/images/logo.jpg" />

		    	Resource resource = 
		           appContext.getResource("classpath:course.jpg");
    	logger.info("image... "+resource.getURI());*/
		  // step 1
        Document document = new Document();
        Paragraph title = null;
        logger.info("Welcome printCertificate 1");
        // step 2
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        logger.info("Welcome printCertificate 2");
        // step 3
        document.open();
        logger.info("Welcome printCertificate 3");
        // step 4
  //   document.add(new Paragraph(String.format("You have submitted the following text using the %s method:",request.getMethod())));
    //    document.add(new Paragraph("Welocme vijay"));
        logger.info("Welcome printCertificate 3.1");
     //   Image image1 = Image.getInstance("/SpringWEBMVCLearning/src/main/resources/course.jpg");
       // document.add(image1);
        String imageUrl = "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTVk9kJKqitRnehX1BdIrtlO-CFs_QpPi4mb3FAVVyQO1QlvGte6Q";

                Image image2 = Image.getInstance(new URL(imageUrl));
            document.add(image2);
            image2.setAlignment(Element.ALIGN_CENTER);
        title= new Paragraph(" Course Completion Certificate",FONT[3]);
        title.setAlignment(Element.ALIGN_CENTER);

        logger.info("Welcome printCertificate 4");
        //document.add(image1);
        document.add(title);
        document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------- "));
        document.add(new Paragraph(" This is to certify that "+user+" has completed",FONT[0]));
        document.add(new Paragraph(""));
        document.add(new Paragraph(" the course "+coursename+" under catalog "+catname+" Successfully",FONT[0]));
        document.add(new Paragraph("                   "));
        document.add(new Paragraph("                    "));
        document.add(new Paragraph("Date:"+compdate));
        logger.info("Welcome printCertificate 5");
        // step 5
        document.close();
        logger.info("Welcome printCertificate 6");
        // setting some response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
            "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the contentlength
        response.setContentLength(baos.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        logger.info("Welcome printCertificate before flush");
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
    	os.flush();
    	//logger.info("Welcome printCertificate after f");
//        os.close();
		 /*Document document = new Document();
	      try
	      {
	    	   //Image image1 = Image.getInstance("course.jpg");
	    	   //image1.setAbsolutePosition(500f, 650f);
	    	  Paragraph title = null;
	    		response.setHeader("Content-Disposition", "attachment; filename=HelloWorld7.pdf");
				response.setContentType("application/pdf"); 
				//response.setCharacterEncoding("UTF-8");
				 OutputStream out1 = response.getOutputStream();
				 
	         //PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld7.pdf"));
	         document.open();
	         title= new Paragraph("Course Completion Certification",FONT[3]);
	         //document.add(image1);
	         document.add(title);
	         document.add(new Paragraph("This is to certify that T.VIJAYA BABU has"));
	         document.add(new Paragraph(" completed the course CST-001 Successfully."));
	         PdfReader reader = new PdfReader("HelloWorld7.pdf");
//reader.
	         document.close();
	         logger.info("Welcome printCertificate.............. ");
	        // out1.write(document.toString().getBytes());
	        // out1.write("Course Completion Certification".getBytes());
	         //owriter.close();
	         out1.flush();
	       //  logger.info("Welcome printCertificate end");*/
	      } catch (DocumentException e)
	      {
	         e.printStackTrace();
	      } catch (FileNotFoundException e)
	      {
	         e.printStackTrace();
	      }
	      catch(Exception e){}
	
	//return "cert";
	}
	
	
}
