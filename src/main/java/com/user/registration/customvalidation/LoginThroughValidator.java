package com.user.registration.customvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.user.registration.dto.LoginThrough;



public class LoginThroughValidator implements 
ConstraintValidator<EmailOrNumberConstraits, LoginThrough> {
	Class<?>[] groups; 
	
	@Override
    public void initialize(EmailOrNumberConstraits phoneOrEmailContraints) {
		this.groups= phoneOrEmailContraints.groups();
	}
 
	@Override
	public boolean isValid(LoginThrough value, ConstraintValidatorContext context) {
		
		// TODO Auto-generated method stub
       if(groups.length==1 && groups[0]== LoginThroughEmail.class)
		return value.equals(LoginThrough.EMAIL);
       else  if(groups.length==1 && groups[0]== LoginThroughPhone.class)
       {return value.equals(LoginThrough.PHONE);
    	   
       }
       else
       {
    	   return false;
       }
		
	}

}
