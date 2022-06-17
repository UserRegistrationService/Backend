package com.user.registration.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.registration.exception.UserPhoneEmailVerificationException;
import com.user.registration.service.UserVerificationService;

@CrossOrigin
@RestController
@RequestMapping("verify-api")
public class UserVerificationAPI {
	
@Autowired
UserVerificationService  userVerificationService;

@GetMapping("/phone/{phoneNumber}")
public ResponseEntity<String> verifyPhoneNumberRequest(@PathVariable Long phoneNumber) throws UserPhoneEmailVerificationException
{   System.out.println(phoneNumber);
	userVerificationService.verifyPhoneNumberRequest(phoneNumber);
	return new ResponseEntity<String>("OTP send",HttpStatus.OK);
	
}

@PutMapping("/phone/{phoneNumber}")
public ResponseEntity<Boolean> verifyPhoneNumberProcess(@PathVariable Long phoneNumber,@RequestBody int otp) throws UserPhoneEmailVerificationException
{  
	return new ResponseEntity<Boolean>(userVerificationService.verifyPhoneNumberProcess(phoneNumber, otp),HttpStatus.OK);
	
}



}
