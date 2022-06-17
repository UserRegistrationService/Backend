package com.user.registration.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.twilio.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SendSMSUtil {
	private static Properties properties;
	

	static {
		properties= new Properties();
		URL url = new SendSMSUtil().getClass().getClassLoader().getResource("application.properties");
		try {
			properties.load(new FileInputStream(url.getPath()));
			
			Twilio.init(properties.getProperty("SMSUtil.TWILLO_SID"),properties.getProperty("SMSUtil.TWILLO_AUTH_ID") );
		}
		catch(FileNotFoundException e)
		{e.printStackTrace();
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean sendSMS(Long phoneNumber, Integer otp) {
		try {
			//Here Country Code is Taken as Indian
			Message.creator(new PhoneNumber("+91"+phoneNumber), new PhoneNumber(properties.getProperty("SMSUtil.TWILLO_SENDER_NUMBER")),
					"Message from UserRegisterationService \n your otp is " + otp).create();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			 //Temp Solution
			System.out.println("use otp "+otp);
			return false;
		}
	}

}
