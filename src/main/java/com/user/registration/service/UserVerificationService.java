package com.user.registration.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.user.registration.dto.CredentialType;
import com.user.registration.dto.LoginDTO;
import com.user.registration.exception.UserPhoneEmailVerificationException;

public interface UserVerificationService {

public Map<CredentialType,String> getVerificationStatus(LoginDTO loginDTO) throws UserPhoneEmailVerificationException;
public void verifyPhoneNumberRequest(Long phoneNumber)  throws UserPhoneEmailVerificationException;
public Boolean verifyPhoneNumberProcess(Long phoneNumber ,int otp) throws UserPhoneEmailVerificationException;
public void verifyEmailIdRequest(String emailId) throws UserPhoneEmailVerificationException, NoSuchAlgorithmException ;
public Boolean verifyEmailIdProcess(String emailId,String hashCode) throws UserPhoneEmailVerificationException, NoSuchAlgorithmException ;
}
