package com.hcl.util;
    import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.learning.HomeController;

import java.util.ArrayList;
import java.util.Properties;

    public class SimpleMail {

        private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
        private static final String SMTP_AUTH_USER = "h1skyag72f";//"ahyMTxYuLk";//"sphBdsfo4h";h1skyag72f
        private static final String SMTP_AUTH_PWD  = "XQlSXPtGWCod7986";//TdbduJEVzazl6985";//"dtbBg9sVtSKx4549";
        private static final Logger logger = LoggerFactory.getLogger(SimpleMail.class);
        public static void main(String[] args) throws Exception{
           new SimpleMail().sendMail("arrounapivotal@gmail.com");
        }

        public void sendMail(String tomail) throws Exception{
        	logger.info("Entered simple Mail ");
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", SMTP_HOST_NAME);
            props.put("mail.smtp.port", 587);
            props.put("mail.smtp.auth", "true");

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
        }
        public void sendRMMail(String User,String repMgr,ArrayList usercourselist) throws Exception{
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

        private class SMTPAuthenticator extends javax.mail.Authenticator {
            public PasswordAuthentication getPasswordAuthentication() {
               String username = SMTP_AUTH_USER;
               String password = SMTP_AUTH_PWD;
               return new PasswordAuthentication(username, password);
            }
        }
    }