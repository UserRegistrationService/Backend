package com.user.registration.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import com.user.registration.customvalidation.EmailOrNumberConstraits;
import com.user.registration.customvalidation.LoginThroughEmail;
import com.user.registration.customvalidation.LoginThroughPhone;

public class LoginDTO {
	@NotNull(groups = {LoginThroughEmail.class},message="{emailId.not_present}")
	@Pattern(groups = {LoginThroughEmail.class},regexp = "[a-z0-9.-_]{1,10}@gmail.com"  ,message="{emailId.invalid_pattern}")
	@Null(groups = {LoginThroughPhone.class},message="{emailId.present}")
	private String emailId;
	@NotNull(groups = {LoginThroughPhone.class},message="{mobileNumber.not_present}")
	@Min(groups = {LoginThroughPhone.class},value = 5000000000L,message="{mobileNumber.invalid_pattern}")
	@Max(groups = {LoginThroughPhone.class},value = 9999999999L,message="{mobileNumber.invalid_pattern}")
	@Null(groups = {LoginThroughEmail.class},message="{mobileNumber.present}")
	private Long mobileNumber;
	@NotNull(groups = {LoginThroughPhone.class,LoginThroughEmail.class},message = "{password.not_present}")
	@Pattern(groups = {LoginThroughPhone.class,LoginThroughEmail.class},regexp = "\\S{8,15}"  ,message="{password.invalid_pattern}")
	private String password;
	@NotNull(groups = {LoginThroughPhone.class,LoginThroughEmail.class},message = "{emailOrNumber.not_present}")
	@EmailOrNumberConstraits(groups = {LoginThroughEmail.class},message = "{emailOrNumber.invalid_for_email_login}")
	@EmailOrNumberConstraits(groups = {LoginThroughPhone.class},message = "{emailOrNumber.invalid_for_phone_login}")
	private LoginThrough emailOrNumber;
	
	public LoginThrough getEmailOrNumber() {
		return emailOrNumber;
	}
	public void setEmailOrNumber(LoginThrough emailOrNumber) {
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
