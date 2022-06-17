package com.user.registration.service;

import java.util.Map;

import com.user.registration.dto.CredentialType;
import com.user.registration.dto.LoginDTO;
import com.user.registration.exception.UserPhoneEmailVerificationException;

public interface UserVerificationService {

public Map<CredentialType,Boolean> getVerificationStatus(LoginDTO loginDTO) throws UserPhoneEmailVerificationException;
public void verifyPhoneNumberRequest(Long phoneNumber)  throws UserPhoneEmailVerificationException;
public Boolean verifyPhoneNumberProcess(Long phoneNumber ,int otp) throws UserPhoneEmailVerificationException;
public void verifyEmailIdRequest(String emailId) throws UserPhoneEmailVerificationException;
public Boolean verifyEmailIdProcess(String emailId) throws UserPhoneEmailVerificationException;
}
