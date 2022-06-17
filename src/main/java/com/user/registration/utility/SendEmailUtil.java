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

import org.springframework.stereotype.Component;
@Component
public class SendEmailUtil {


	public Boolean sendMail() 
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
		         return new PasswordAuthentication("kamleshbanti.ahuja@gmail.com", "fpftvpjdhekjiyvq");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("kamleshbanti.ahuja@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kamleshbanti.ahuja@gmail.com"));
		   msg.setSubject("Tutorials point email");
		   msg.setContent("Tutorials point email", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Tutorials point email", "text/html");

		  
		  
		   Transport.send(msg);
		   return true;
		   }
		   catch(Exception e)
		   {   e.printStackTrace();
			   return false;  
		   }
		   
		}
}
