package com.user.registration.api;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.registration.exception.AdministratorException;
import com.user.registration.service.AdministratorService;


@CrossOrigin
@RestController
@RequestMapping("admin-api")
public class AdministratorAPI {
@Autowired
AdministratorService adminService;

@Autowired
Environment environment;

@GetMapping("locked/users")
public ResponseEntity<List<Long>> getAllLocked() throws  AdministratorException
    {  
	
	return new ResponseEntity<>(adminService.findAllLockedUser(),HttpStatus.OK);
	
   }

@GetMapping("unlock/user/{phoneNumber}")
public ResponseEntity<String> unlockUser(
		@NotNull(message = "{mobileNumber.not_present}")
		@Min(value = 5000000000L,message="{mobileNumber.invalid_pattern}")
		@Max(value = 9999999999L,message="{mobileNumber.invalid_pattern}")
		@PathVariable Long phoneNumber) throws  AdministratorException
    {  
	adminService.unlockUser(phoneNumber);
	return new ResponseEntity<>(environment.getProperty("API.UNLOCK_SUCCESSFULL_ONE")+" "+phoneNumber
		+" "+	environment.getProperty("API.UNLOCK_SUCCESSFULL_TWO")
			,HttpStatus.OK);
	
   }

}
