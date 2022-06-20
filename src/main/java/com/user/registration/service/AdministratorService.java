package com.user.registration.service;

import java.util.List;

import com.user.registration.exception.AdministratorException;


public interface AdministratorService {


Boolean isAdministrator(Long phoneNumber);
 
List<Long>  findAllLockedUser() throws AdministratorException;

void unlockUser(Long phoneNumber) throws AdministratorException;
}
