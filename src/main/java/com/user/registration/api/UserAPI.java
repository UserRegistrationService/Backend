package com.user.registration.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.registration.customvalidation.EmailUsed;
import com.user.registration.customvalidation.PasswordRequired;
import com.user.registration.customvalidation.PhoneUsed;
import com.user.registration.dto.LoginDTO;
import com.user.registration.dto.UserDTO;
import com.user.registration.exception.UserLoginResgistrationException;

import com.user.registration.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("user-api")
public class UserAPI {
@Autowired
UserService userService;

@Autowired
Environment environment;

@PostMapping("/register")
public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) throws UserLoginResgistrationException
{   
	userService.register(userDTO);
	return new ResponseEntity<>(environment.getProperty("API.REGISTRATION_SUCCESS"),HttpStatus.CREATED);
}
@PostMapping("/login/phone")
public ResponseEntity<String> loginPhone(@Validated({PhoneUsed.class,PasswordRequired.class}) @RequestBody LoginDTO loginDTO) throws  UserLoginResgistrationException
{  Boolean isLogged= userService.login(loginDTO);
	return new ResponseEntity<>(isLogged?environment.getProperty("API.LOGIN_SUCCESS"):environment.getProperty("API.LOGIN_FAILURE"),HttpStatus.CREATED);
}
@PostMapping("/login/email")
public ResponseEntity<String> loginEmail(@Validated({EmailUsed.class,PasswordRequired.class}) @RequestBody LoginDTO loginDTO) throws  UserLoginResgistrationException
{  Boolean isLogged= userService.login(loginDTO);
	return new ResponseEntity<>(isLogged?environment.getProperty("API.LOGIN_SUCCESS"):environment.getProperty("API.LOGIN_FAILURE"),HttpStatus.CREATED);
}
}
