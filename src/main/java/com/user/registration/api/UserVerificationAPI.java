package com.user.registration.api;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.registration.customvalidation.EmailUsed;
import com.user.registration.customvalidation.PasswordNotRequired;
import com.user.registration.customvalidation.PhoneUsed;
import com.user.registration.dto.CredentialType;
import com.user.registration.dto.LoginDTO;
import com.user.registration.exception.UserPhoneEmailVerificationException;
import com.user.registration.service.UserVerificationService;

@CrossOrigin
@RestController
@RequestMapping("verify-api")
@Validated
public class UserVerificationAPI {
	
@Autowired
UserVerificationService  userVerificationService;


@Autowired
Environment environment;

@PostMapping("/status/phone")
public ResponseEntity<Map<CredentialType,Boolean>> getVerifyStatusUsingPhone(@Validated({PhoneUsed.class,PasswordNotRequired.class}) @RequestBody LoginDTO loginDTO) throws UserPhoneEmailVerificationException
{  
	
	return new ResponseEntity<>(userVerificationService.getVerificationStatus(loginDTO),HttpStatus.OK);
	
}
@PostMapping("/status/email")
public ResponseEntity<Map<CredentialType,Boolean>> getVerifyStatusUsingEmail(@Validated({EmailUsed.class,PasswordNotRequired.class}) @RequestBody LoginDTO loginDTO) throws UserPhoneEmailVerificationException
{  
	
	return new ResponseEntity<>(userVerificationService.getVerificationStatus(loginDTO),HttpStatus.OK);
	
}
@GetMapping("/phone/{phoneNumber}")
public ResponseEntity<String> verifyPhoneNumberRequest(
		@NotNull(message = "{mobileNumber.not_present}")
		@Min(value = 5000000000L,message="{mobileNumber.invalid_pattern}")
		@Max(value = 9999999999L,message="{mobileNumber.invalid_pattern}")
		@PathVariable Long phoneNumber) throws UserPhoneEmailVerificationException
{  
	userVerificationService.verifyPhoneNumberRequest(phoneNumber);
	return new ResponseEntity<>(environment.getProperty("API.PHONE_VERIFICATION_SENT")+" "+phoneNumber,HttpStatus.OK);
	
}

@PutMapping("/phone/{phoneNumber}")
public ResponseEntity<String> verifyPhoneNumberProcess(
		@NotNull(message = "{mobileNumber.not_present}")
		@Min(value = 5000000000L,message="{mobileNumber.invalid_pattern}")
		@Max(value = 9999999999L,message="{mobileNumber.invalid_pattern}")
		@PathVariable Long phoneNumber,
		@NotNull(message= "{validationData.absent}")
		@Pattern(regexp = "[0-9]+",message="{validationData.invalid}")
		@RequestBody String otp) throws UserPhoneEmailVerificationException
{  
	return new ResponseEntity<>(userVerificationService.verifyPhoneNumberProcess(phoneNumber, Integer.parseInt(otp))?phoneNumber+environment.getProperty("API.VERIFICATION_SUCCESS"):environment.getProperty("API.VERIFICATION_FAILURE"),HttpStatus.OK);
	
}

@GetMapping("/email/{emailId}")
public ResponseEntity<String> verifyEmailIdRequest(
		@NotNull(message = "{emailId.not_present}")
		@Pattern(regexp = "[a-z0-9.-_]{1,20}@gmail.com"  ,message="{emailId.invalid_pattern}")
		@PathVariable String emailId) throws UserPhoneEmailVerificationException, NoSuchAlgorithmException
{   
	userVerificationService.verifyEmailIdRequest(emailId);
	return new ResponseEntity<>(environment.getProperty("API.EMAIL_VERIFICATION_SENT")+" "+emailId,HttpStatus.OK);
	
}

@GetMapping("email/{emailId}/hash/{hash}")
public ResponseEntity<String> verifyEmailIdProcess(
		@NotNull(message = "{emailId.not_present}")
		@Pattern(regexp = "[a-z0-9.-_]{1,20}@gmail.com"  ,message="{emailId.invalid_pattern}")
		@PathVariable String emailId,
		@NotNull(message= "{validationData.absent}")
		@PathVariable String hash) throws UserPhoneEmailVerificationException, NoSuchAlgorithmException
{   
	
	return new ResponseEntity<>(userVerificationService.verifyEmailIdProcess(emailId, hash)?emailId+environment.getProperty("API.VERIFICATION_SUCCESS"):environment.getProperty("API.VERIFICATION_FAILURE"),HttpStatus.OK);
}
}

