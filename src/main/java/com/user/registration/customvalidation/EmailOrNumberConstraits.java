package com.user.registration.customvalidation;


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;


import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;


import javax.validation.Constraint;
import javax.validation.Payload;






@Documented
@Constraint(validatedBy = CredentialTypeValidator.class)
@Target( { ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value=com.user.registration.customvalidation.EmailOrNumberConstraits.List.class)
public  @interface EmailOrNumberConstraits {
	
    String message() default "{emailOrNumber.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    
    @Target({ METHOD, FIELD })
	@Retention(RUNTIME)
	@Documented
	@interface List {

    	EmailOrNumberConstraits[] value();
	}
}

   