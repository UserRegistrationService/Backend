package com.user.registration.service;

import com.user.registration.dto.LoginDTO;
import com.user.registration.dto.UserDTO;
import com.user.registration.exception.UserLoginResgistrationException;


public interface UserService {
	  public void register(UserDTO userDTO) throws UserLoginResgistrationException;
	  public Boolean login(LoginDTO loginDTO) throws  UserLoginResgistrationException;
}
