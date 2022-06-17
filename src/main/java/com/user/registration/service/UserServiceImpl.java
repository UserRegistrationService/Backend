package com.user.registration.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.registration.dto.LoginDTO;
import com.user.registration.dto.LoginThrough;
import com.user.registration.dto.UserDTO;
import com.user.registration.entity.SuspendedUser;
import com.user.registration.entity.User;
import com.user.registration.exception.UserLoginResgistrationException;
import com.user.registration.repository.SuspendedUserRepository;
import com.user.registration.repository.UserRepository;
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	Log logger = LogFactory.getLog(UserServiceImpl.class);

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	SuspendedUserRepository suspendedUserRepo;
	
	@Override
    public void register(UserDTO userDTO) throws UserLoginResgistrationException
    {
	Optional<SuspendedUser> suspendedUserFromPhoneNumber= suspendedUserRepo.findById(userDTO.getMobileNumber());
	if(suspendedUserFromPhoneNumber.isPresent())
	{
	    	 throw new UserLoginResgistrationException("UserService.PHONE_NUMBER_SUSPENDED");
	}
	Optional<SuspendedUser> suspendedUserFromEmailId= suspendedUserRepo.findByEmailId(userDTO.getEmailId());
    if(suspendedUserFromEmailId.isPresent())
	{
	   throw new UserLoginResgistrationException("UserService.EMAIL_SUSPENDED");
	 }
     Optional<User> userFromPhoneNumber= userRepo.findById(userDTO.getMobileNumber());
     if(userFromPhoneNumber.isPresent())
     {
    	 throw new UserLoginResgistrationException("UserService.PHONE_NUMBER_ALREADY_USED");
     }
     Optional<User> userFromEmailId= userRepo.findByEmailId(userDTO.getEmailId());
     if(userFromEmailId.isPresent())
     {
    	 throw new UserLoginResgistrationException("UserService.EMAIL_ALREADY_USED");
     }
     User newUser= UserDTO.prepareEntity(userDTO);
     userRepo.save(newUser);
    }
    
    @Override
    public Boolean login(LoginDTO loginDTO) throws  UserLoginResgistrationException
    {
    Optional<User> userOptional=loginDTO.getEmailOrNumber().equals(LoginThrough.PHONE)? userRepo.findById(loginDTO.getMobileNumber()):userRepo.findByEmailId(loginDTO.getEmailId());
    	 
    
    User user= userOptional.orElseThrow(() ->  new UserLoginResgistrationException("UserService.NO_USER_FOUND") );
     if(user.getLocked()) 
     {
    	 throw new UserLoginResgistrationException("UserService.ACCOUNT_LOCKED");
    	 
     }
    if(user.getEmailVerified().equals(false) || user.getNumberVerified().equals(false)) 
     {
    	 throw new UserLoginResgistrationException("UserService.VERIFICATION_NOT_DONE");
    	 
     }
     if(user.getPassword().equals(loginDTO.getPassword()))
     {
    	user.setConsecutiveLoginFailure(0);
    	return true;
     }
     else
     {
    	if(user.getConsecutiveLoginFailure().equals(2)) 
    	{
    		user.setLocked(true);
    		user.setConsecutiveLoginFailure(0);
    	}
    	else
    	{
    		user.setConsecutiveLoginFailure(user.getConsecutiveLoginFailure()+1);
    	}
    	return false;
     }
    }
  
}
