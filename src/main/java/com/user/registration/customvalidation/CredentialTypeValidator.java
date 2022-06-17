package com.user.registration.customvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.user.registration.dto.CredentialType;

public class CredentialTypeValidator implements ConstraintValidator<EmailOrNumberConstraits, CredentialType> {
	Class<?>[] groups;

	@Override
	public void initialize(EmailOrNumberConstraits phoneOrEmailContraints) {
		this.groups = phoneOrEmailContraints.groups();
	}

	@Override
	public boolean isValid(CredentialType value, ConstraintValidatorContext context) {

		// TODO Auto-generated method stub
		if (groups.length == 1) {
			if (groups[0] == EmailUsed.class)
				return value.equals(CredentialType.EMAIL);
			else if (groups[0] == PhoneUsed.class)
				return value.equals(CredentialType.PHONE);
			else
				return false;

		} else {
			return false;
		}

	}

}
