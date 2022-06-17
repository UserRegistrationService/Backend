package com.user.registration.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.registration.customvalidation.LoginThroughEmail;
import com.user.registration.customvalidation.LoginThroughPhone;
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
@PostMapping("/register")
public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) throws UserLoginResgistrationException
{   
	userService.register(userDTO);
	return new ResponseEntity<>("Registered",HttpStatus.CREATED);
}
@PostMapping("/login/phone")
public ResponseEntity<Boolean> loginPhone(@Validated(LoginThroughPhone.class) @RequestBody LoginDTO loginDTO) throws  UserLoginResgistrationException
{  Boolean isLogged= userService.login(loginDTO);
	return new ResponseEntity<>(isLogged,HttpStatus.CREATED);
}
@PostMapping("/login/email")
public ResponseEntity<Boolean> loginEmail(@Validated(LoginThroughEmail.class) @RequestBody LoginDTO loginDTO) throws  UserLoginResgistrationException
{  Boolean isLogged= userService.login(loginDTO);
	return new ResponseEntity<>(isLogged,HttpStatus.CREATED);
}
}
