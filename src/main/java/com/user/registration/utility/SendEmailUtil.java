package com.user.registration.utility;


import java.util.Date;
import java.util.Properties;

import javax.mail.Message;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Component
public class SendEmailUtil {
     
	@Autowired
	Environment environment;

	public Boolean sendMail(String recipientEmail,String hashCodeData) 
	{
		   try
		   {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		  
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication(environment.getProperty("EMAILUtil.ACCOUNT_OWNER"), environment.getProperty("EMAILUtil.ACCOUNT_PASSWORD"));
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress( environment.getProperty("EMAILUtil.ACCOUNT_SENDER_MAIL"), false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
		   msg.setSubject("UserRegistrationService-Email Verification");
		   msg.setContent("Please click on below link <br><br> <link href='https://www.google.com/'>http://localhost:8765/verify-api/email/"+recipientEmail+"/hash/"+hashCodeData+"<link/>", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Tutorials point email", "text/html");

		  
		  
		   Transport.send(msg);
		   return true;
		   }
		   catch(Exception e)
		   {   e.printStackTrace();
		       //Temp Solution
		       System.out.println("use link - <link href='https://www.google.com/'>http://localhost:8765/verify-api/email/"+recipientEmail+"/hash/"+hashCodeData+"<link/>");
			   return false;  
		   }
		   
		}
}
