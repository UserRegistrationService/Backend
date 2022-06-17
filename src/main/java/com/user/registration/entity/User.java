package com.user.registration.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

@Id
private Long mobileNumber;
private String name;
private String address;
private String emailId;
private String password;
private Boolean emailVerified;
private Boolean numberVerified;
private Boolean locked;
private Integer consecutiveLoginFailure;
private LocalDateTime registeredAt;


public Boolean getEmailVerified() {
	return emailVerified;
}
public void setEmailVerified(Boolean emailVerified) {
	this.emailVerified = emailVerified;
}
public Boolean getNumberVerified() {
	return numberVerified;
}
public void setNumberVerified(Boolean numberVerified) {
	this.numberVerified = numberVerified;
}
public Boolean getLocked() {
	return locked;
}
public void setLocked(Boolean locked) {
	this.locked = locked;
}
public Integer getConsecutiveLoginFailure() {
	return consecutiveLoginFailure;
}
public void setConsecutiveLoginFailure(Integer consecutiveLoginFailure) {
	this.consecutiveLoginFailure = consecutiveLoginFailure;
}
public LocalDateTime getRegisteredAt() {
	return registeredAt;
}
public void setRegisteredAt(LocalDateTime registeredAt) {
	this.registeredAt = registeredAt;
}
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

}
