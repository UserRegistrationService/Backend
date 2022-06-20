package com.user.registration.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.registration.dto.LoginDTO;
import com.user.registration.dto.CredentialType;
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
	AdministratorService  adminService;
	@Autowired
	SuspendedUserRepository suspendedUserRepo;
	
	@Override
    public void register(UserDTO userDTO) throws UserLoginResgistrationException
    {
	Optional<SuspendedUser> suspendedUserFromPhoneNumber= suspendedUserRepo.findById(userDTO.getMobileNumber());
	if(suspendedUserFromPhoneNumber.isPresent())
	{
	    	 throw new UserLoginResgistrationException("Service.PHONE_NUMBER_SUSPENDED");
	}
	Optional<SuspendedUser> suspendedUserFromEmailId= suspendedUserRepo.findByEmailId(userDTO.getEmailId());
    if(suspendedUserFromEmailId.isPresent())
	{
	   throw new UserLoginResgistrationException("Service.EMAIL_SUSPENDED");
	 }
     Optional<User> userFromPhoneNumber= userRepo.findById(userDTO.getMobileNumber());
     if(userFromPhoneNumber.isPresent())
     {
    	 throw new UserLoginResgistrationException("Service.PHONE_NUMBER_ALREADY_USED");
     }
     Optional<User> userFromEmailId= userRepo.findByEmailId(userDTO.getEmailId());
     if(userFromEmailId.isPresent())
     {
    	 throw new UserLoginResgistrationException("Service.EMAIL_ALREADY_USED");
     }
     User newUser= UserDTO.prepareEntity(userDTO);
     userRepo.save(newUser);
    }
    
    @Override
    public UserDTO login(LoginDTO loginDTO) throws  UserLoginResgistrationException
    {
    Optional<User> userOptional=loginDTO.getEmailOrNumber().equals(CredentialType.PHONE)? userRepo.findById(loginDTO.getMobileNumber()):userRepo.findByEmailId(loginDTO.getEmailId());
    	 
    
    User user= userOptional.orElseThrow(() ->  new UserLoginResgistrationException("Service.NO_USER_FOUND") );
     if(user.getLocked()) 
     {
    	 throw new UserLoginResgistrationException("Service.ACCOUNT_LOCKED");
    	 
     }
    if(user.getEmailVerified().equals(false) || user.getNumberVerified().equals(false)) 
     {
    	 throw new UserLoginResgistrationException("Service.VERIFICATION_NOT_DONE");
    	 
     }
     if(user.getPassword().equals(loginDTO.getPassword()))
     {
    	user.setConsecutiveLoginFailure(0);
    	UserDTO userDTO = UserDTO.prepareDTO(user);
    	//Here its assumed that ADMINISTRATOR phone number is very first valid number.
        //There is only one ADMINISTRATOR
    	if( user.getMobileNumber().equals(5000000000L) && adminService.isAdministrator(user.getMobileNumber()) )
         {
    	userDTO.setRole("ADMIN");		 
         }
    	
    	return userDTO;
     }
     else
    	 
     {  //Here its assumed that ADMINISTRATOR phone number is very first valid number.
         //There is only one ADMINISTRATOR
    	 //If ADMINISTRATOR ACCOUNT IS LOCKED THEN WE CAN'T DO ANYTHING
    	 //IT IS AVOIDED ADMINISTRATOR CAN ENTER ANY NUMBER OF WRONG PASSWORD
    	 if(! user.getMobileNumber().equals(5000000000L) || !adminService.isAdministrator(user.getMobileNumber()))
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
       }
    	throw new UserLoginResgistrationException("Service.INVALID_CREDENTIAL");
     }
    }
  
}
