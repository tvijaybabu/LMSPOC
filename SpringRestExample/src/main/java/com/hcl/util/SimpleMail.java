package com.hcl.util;
    import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.hcl.learning.HomeController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Properties;
import com.sendgrid.*;
//import com.github.scottmotte.Vcapenv;
    public class SimpleMail {

        private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
        private static String SMTP_AUTH_USER ="";// "h1skyag72f";//"sphBdsfo4h";
        private static String SMTP_AUTH_PWD  = "";//"XQlSXPtGWCod7986";//"dtbBg9sVtSKx4549";
        private static final Logger logger = LoggerFactory.getLogger(SimpleMail.class);
        public static void main(String[] args) throws Exception{
           //new SimpleMail().sendMail("vijaya.babu0607@gmail.com");
        }

        public void sendMail(String tomail) throws Exception{
        	logger.info("Entered simple Mail ");
            /*Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", SMTP_HOST_NAME);
            props.put("mail.smtp.port", 587);
            props.put("mail.smtp.auth", "true");
            *///SendGrid sendgrid = new SendGrid(api_key);
            //SendGrid sendgrid = new SendGrid("ahyMTxYuLk", "TdbduJEVzazl6985");
         	
         	String vcap_services = System.getenv("VCAP_SERVICES");
         	ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(vcap_services, JsonNode.class);
            SMTP_AUTH_USER = jsonNode.get("sendgrid").get(0).get("credentials").get("username").asText();
            SMTP_AUTH_PWD = jsonNode.get("sendgrid").get(0).get("credentials").get("password").asText();
            
            System.out.println("SMTP_AUTH_USER from sendgrid::"+SMTP_AUTH_USER);
            System.out.println("SMTP_AUTH_PWD from sendgrid::"+SMTP_AUTH_PWD);
//            SendGrid sendgrid = new SendGrid("h1skyag72f", "XQlSXPtGWCod7986");
            SendGrid sendgrid = new SendGrid(SMTP_AUTH_USER, SMTP_AUTH_PWD);
            SendGrid.Email email = new SendGrid.Email();
            email.addTo(tomail);
            email.setFrom("arrounapivotal@gmail.com");
            email.setSubject("User Registration");
            email.setText("User Registration Successfully...using SendGrid Java!");

            try {
            	
              SendGrid.Response response = sendgrid.send(email);
              System.out.println("response from sendgrid::"+response.getMessage());
            }
            catch (SendGridException e) {
              System.err.println("Exception while sending mail...."+e.getMessage());
            }
/*
            Authenticator auth = new SMTPAuthenticator();
            logger.info("Entered simple Mail after Authenticator.. ");
            Session mailSession = Session.getDefaultInstance(props, auth);
            // uncomment for debugging infos to stdout
            // mailSession.setDebug(true);
            Transport transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);

            Multipart multipart = new MimeMultipart("alternative");

            BodyPart part1 = new MimeBodyPart();
            part1.setText("This is multipart mail and u read part1……");

            BodyPart part2 = new MimeBodyPart();
            //String s="User registered succesfully.<br>Please click here to login.https://Lmspivotalwebapp.cfapps.io/";
            String s="<b>User registered succesfully.<b>";
            part2.setContent(s, "text/html");

            multipart.addBodyPart(part1);
            multipart.addBodyPart(part2);

            message.setContent(multipart);
            message.setFrom(new InternetAddress("LMSApplication-Registration"));
            message.setSubject("Mail From Cloud Server settings..");
            message.addRecipient(Message.RecipientType.TO,
                 new InternetAddress(tomail));

            transport.connect();
            logger.info("Entered simple Mail before sending message.. ");
            transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
            transport.close();
*/        }
        /*public void sendRMMail(String User,String repMgr,ArrayList usercourselist) throws Exception{
        	logger.info("Entered simple Mail ");
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", SMTP_HOST_NAME);
            props.put("mail.smtp.port", 587);
            props.put("mail.smtp.auth", "true");
            System.out.println("Entered simple Mail after Authenticator.. "+User+"--"+repMgr+"--"+usercourselist);
            Authenticator auth = new SMTPAuthenticator();
            logger.info("Entered simple Mail after Authenticator.. ");
            Session mailSession = Session.getDefaultInstance(props, auth);
            // uncomment for debugging infos to stdout
            // mailSession.setDebug(true);
            String courses="";
        	if(usercourselist!=null){
    			UserCourse uc =null;
    		
    			for(int i=0;i<usercourselist.size();i++)
    			{
    				uc =(UserCourse)usercourselist.get(i);
    				courses=courses+uc.getCourse_name()+"<br>";
    			}
        	}
            Transport transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);

            Multipart multipart = new MimeMultipart("alternative");

            BodyPart part1 = new MimeBodyPart();
            part1.setText("This is multipart mail and u read part1……");

            BodyPart part2 = new MimeBodyPart();
            String s=User+" selected below Courses<BR>"
            		+"<h3>"+courses+"</h3><BR>"
            		+ "Please approve/reject by login into lms application";
            		//+ "Please approve/reject by login into .https://Lmspivotalwebapp.cfapps.io/";
            part2.setContent(s, "text/html");

            multipart.addBodyPart(part1);
            multipart.addBodyPart(part2);

            message.setContent(multipart);
            message.setFrom(new InternetAddress("LMSApplication-Registration"));
            message.setSubject("Mail From Cloud Server settings..");
            message.addRecipient(Message.RecipientType.TO,
                 new InternetAddress(repMgr));

            transport.connect();
            logger.info("Entered simple Mail before sending message.. ");
            transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
            transport.close();
        }
*/
        private class SMTPAuthenticator extends javax.mail.Authenticator {
            public PasswordAuthentication getPasswordAuthentication() {
               String username = SMTP_AUTH_USER;
               String password = SMTP_AUTH_PWD;
               return new PasswordAuthentication(username, password);
            }
        }
    }