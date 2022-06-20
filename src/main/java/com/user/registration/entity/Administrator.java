package com.user.registration.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Administrator {

@Id
private Long mobileNumber;

public Long getMobileNumber() {
	return mobileNumber;
}

public void setMobileNumber(Long mobileNumber) {
	this.mobileNumber = mobileNumber;
}


}
