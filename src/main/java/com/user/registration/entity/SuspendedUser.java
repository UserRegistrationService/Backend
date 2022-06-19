package com.user.registration.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SuspendedUser {

	@Id
	private Long mobileNumber;
	private String emailId;
	private LocalDate suspendedOn;
	public Long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public LocalDate getSuspendedOn() {
		return suspendedOn;
	}
	public void setSuspendedOn(LocalDate suspendedOn) {
		this.suspendedOn = suspendedOn;
	}
	
	
}
