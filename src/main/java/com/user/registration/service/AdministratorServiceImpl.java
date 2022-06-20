package com.user.registration.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.registration.entity.User;
import com.user.registration.exception.AdministratorException;
import com.user.registration.repository.AdministratorRepository;
import com.user.registration.repository.UserRepository;
@Service("adminService")
@Transactional
public class AdministratorServiceImpl implements AdministratorService {
    
	Log logger = LogFactory.getLog(UserServiceImpl.class);

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AdministratorRepository adminRepo;
	@Override
	public Boolean isAdministrator(Long phoneNumber) {
		// TODO Auto-generated method stub
		return adminRepo.findById(phoneNumber).isPresent();
	}

	@Override
	public List<Long> findAllLockedUser() throws AdministratorException {
		// TODO Auto-generated method stub
		List<Long> numbers= userRepo.findAllLockedUser();
		if(numbers.isEmpty())
		{
			throw new AdministratorException("Service.NO_LOCKED_USER");
		}
		return numbers;
	}

	@Override
	public void unlockUser(Long phoneNumber) throws AdministratorException {
		// TODO Auto-generated method stub
		Optional<User> userOptional=userRepo.findById(phoneNumber);
	    User user= userOptional.orElseThrow(() ->  new AdministratorException("Service.NO_USER_FOUND") );
        if(user.getLocked())
        {
        	user.setLocked(false);
        }
        else
        {
          throw	new AdministratorException("Service.NOT_LOCKED_USER");
        }
	}

	

}
