package com.user.registration.service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.registration.dto.CredentialType;
import com.user.registration.dto.LoginDTO;
import com.user.registration.entity.User;
import com.user.registration.exception.UserPhoneEmailVerificationException;
import com.user.registration.repository.UserRepository;
import com.user.registration.utility.HashingUtil;
import com.user.registration.utility.OtpUtil;
import com.user.registration.utility.SendEmailUtil;
import com.user.registration.utility.SendSMSUtil;

@Service("userVerificationService")
@Transactional
public class UserVerificationServiceImpl implements UserVerificationService{
    
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	OtpUtil otpUtil;

	@Autowired
	SendSMSUtil smsUtil;

	@Autowired
	SendEmailUtil emailUtil;
	
	@Autowired
	HashingUtil hashUtil;
	
	@Override
	public Map<CredentialType, Boolean> getVerificationStatus(LoginDTO loginDTO) throws UserPhoneEmailVerificationException {
		// TODO Auto-generated method stub
		Map<CredentialType,Boolean> credentialVerificationMap=new HashMap<>();
		Optional<User> userOptional=loginDTO.getEmailOrNumber().equals(CredentialType.PHONE)? userRepo.findById(loginDTO.getMobileNumber()):userRepo.findByEmailId(loginDTO.getEmailId());
	    User user= userOptional.orElseThrow(() ->  new UserPhoneEmailVerificationException("Service.NO_USER_FOUND") );
	    credentialVerificationMap.put(CredentialType.EMAIL, user.getEmailVerified());
	    credentialVerificationMap.put(CredentialType.PHONE, user.getNumberVerified());
	    return credentialVerificationMap;
	}

	@Override
	public void verifyPhoneNumberRequest(Long phoneNumber) throws UserPhoneEmailVerificationException {
		// TODO Auto-generated method stub
		
		Optional<User> userOptional=userRepo.findById(phoneNumber);
	    User user= userOptional.orElseThrow(() ->  new UserPhoneEmailVerificationException("Service.NO_USER_FOUND") );
	    if(user.getNumberVerified())
	    { 
	     throw	new UserPhoneEmailVerificationException("Service.PHONE_ALREADY_VERIFIED");
	    }
	    
	    int otp=otpUtil.generateOTP(phoneNumber.toString());
	    smsUtil.sendSMS(phoneNumber,otp);
	}

	@Override
	public Boolean verifyPhoneNumberProcess(Long phoneNumber,int otp) throws UserPhoneEmailVerificationException {
		// TODO Auto-generated method stub
		Optional<User> userOptional=userRepo.findById(phoneNumber);
	    User user= userOptional.orElseThrow(() ->  new UserPhoneEmailVerificationException("Service.NO_USER_FOUND") );
	    if(user.getNumberVerified())
	    {
	     throw	new UserPhoneEmailVerificationException("Service.PHONE_ALREADY_VERIFIED");
	    }
	    int savedOtp=otpUtil.getOtp(phoneNumber.toString());
	    user.setNumberVerified(otp==savedOtp);
	    return user.getNumberVerified();
	}

	@Override
	public void verifyEmailIdRequest(String emailId) throws UserPhoneEmailVerificationException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		Optional<User> userOptional=userRepo.findByEmailId(emailId);
	    User user= userOptional.orElseThrow(() ->  new UserPhoneEmailVerificationException("Service.NO_USER_FOUND") );
	    if(user.getEmailVerified())
	    { 
	     throw	new UserPhoneEmailVerificationException("Service.EMAIL_ALREADY_VERIFIED");
	    }
	    String hashedCode =hashUtil.getHashValue(user.getEmailId()+":"+user.getName());
	    emailUtil.sendMail(emailId, hashedCode);
	}

	@Override
	public Boolean verifyEmailIdProcess(String emailId,String hashCode) throws UserPhoneEmailVerificationException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		Optional<User> userOptional=userRepo.findByEmailId(emailId);
	    User user= userOptional.orElseThrow(() ->  new UserPhoneEmailVerificationException("Service.NO_USER_FOUND") );
	    if(user.getEmailVerified())
	    { 
	     throw	new UserPhoneEmailVerificationException("Service.EMAIL_ALREADY_VERIFIED");
	    }
	    String hashcodeFromRepo= hashUtil.getHashValue(user.getEmailId()+":"+user.getName());
		user.setEmailVerified(hashcodeFromRepo.equals(hashCode));
	    return user.getEmailVerified();
	}


	
}
