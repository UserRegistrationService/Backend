package com.user.registration.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import com.user.registration.customvalidation.EmailOrNumberConstraits;
import com.user.registration.customvalidation.EmailUsed;
import com.user.registration.customvalidation.PasswordNotRequired;
import com.user.registration.customvalidation.PasswordRequired;
import com.user.registration.customvalidation.PhoneUsed;

public class LoginDTO {
	@NotNull(groups = {EmailUsed.class},message="{emailId.not_present}")
	@Pattern(groups = {EmailUsed.class},regexp = "[a-z0-9.-_]{1,20}@gmail[.]com"  ,message="{emailId.invalid_pattern}")
	@Null(groups = {PhoneUsed.class},message="{emailId.present}")
	private String emailId;
	@NotNull(groups = {PhoneUsed.class},message="{mobileNumber.not_present}")
	@Min(groups = {PhoneUsed.class},value = 5000000000L,message="{mobileNumber.invalid_pattern}")
	@Max(groups = {PhoneUsed.class},value = 9999999999L,message="{mobileNumber.invalid_pattern}")
	@Null(groups = {EmailUsed.class},message="{mobileNumber.present}")
	private Long mobileNumber;
	@NotNull(groups = {PasswordRequired.class},message = "{password.not_present}")
	@Pattern(groups = {PasswordRequired.class},regexp = "\\S{8,15}"  ,message="{password.invalid_pattern}")
	@Null(groups = {PasswordNotRequired.class},message = "{password.present}")
	private String password;
	@NotNull(groups = {PhoneUsed.class,EmailUsed.class},message = "{emailOrNumber.not_present}")
	@EmailOrNumberConstraits(groups = {EmailUsed.class},message = "{emailOrNumber.invalid_for_email_login}")
	@EmailOrNumberConstraits(groups = {PhoneUsed.class},message = "{emailOrNumber.invalid_for_phone_login}")
	private CredentialType emailOrNumber;
	
	public CredentialType getEmailOrNumber() {
		return emailOrNumber;
	}
	public void setEmailOrNumber(CredentialType emailOrNumber) {
		this.emailOrNumber = emailOrNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
