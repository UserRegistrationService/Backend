package com.user.registration.utility;

import org.springframework.stereotype.Component;

import com.twilio.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SendSMSUtil {
	private final static String ACCOUNT_SID = "AC8057efed00a02f30b17699f6e4fab8ad";
	public static final String AUTH_TOKEN = "b0afbe57f54aa70d911feef048e1ca25";

	static {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	}

	public boolean sendSMS(Long phoneNumber, Integer otp) {
		try {
			Message.creator(new PhoneNumber("+91"+phoneNumber), new PhoneNumber("+18574225310"),
					"Message from UserRegisterationService /n your otp is " + otp).create();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
