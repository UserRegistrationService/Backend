package com.user.registration.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import com.user.registration.entity.User;

public class UserDTO {
@NotNull(message = "{name.not_present}")
@Pattern(regexp = "[A-Z][a-z]+[ ][A-Z][a-z]+"  ,message="{name.invalid_pattern}")
@Length(max=15,message="{name.invalid_pattern}")
private String name;
@NotNull(message = "{address.not_present}")
@Pattern(regexp = ".{10,60}"  ,message="{address.invalid_pattern}")
private String address;
@NotNull(message = "{emailId.not_present}")
@Pattern(regexp = "[a-z0-9.-_]{1,20}@gmail[.]com"  ,message="{emailId.invalid_pattern}")
private String emailId;
@NotNull(message = "{mobileNumber.not_present}")
@Min(value = 5000000000L,message="{mobileNumber.invalid_pattern}")
@Max(value = 9999999999L,message="{mobileNumber.invalid_pattern}")
private Long mobileNumber;
@NotNull(message = "{password.not_present}")
@Pattern(regexp = "\\S{8,15}"  ,message="{password.invalid_pattern}")
private String password;

private String role;
	
public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
    
	public static User prepareEntity(UserDTO userDTO)
	{
	  User user = new User();
	  user.setName(userDTO.getName());
	  user.setAddress(userDTO.getAddress());
	  user.setEmailId(userDTO.getEmailId());
	  user.setMobileNumber(userDTO.getMobileNumber());
	  user.setPassword(userDTO.getPassword());
	  user.setEmailVerified(false);
	  user.setNumberVerified(false);
	  user.setLocked(false);
	  user.setConsecutiveLoginFailure(0);
	  user.setRegisteredAt(LocalDateTime.now());
	  return user;
		
	}
	public static UserDTO prepareDTO(User user)
	{
	  UserDTO userDTO = new UserDTO();
	  userDTO.setName(user.getName());
	  userDTO.setAddress(user.getAddress());
	  userDTO.setEmailId(user.getEmailId());
	  userDTO.setMobileNumber(user.getMobileNumber());
	  userDTO.setPassword("masked");
	  
	  return userDTO;
		
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}


}
