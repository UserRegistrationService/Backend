package com.user.registration.exceptionhandling;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.user.registration.exception.AdministratorException;
import com.user.registration.exception.UserLoginResgistrationException;
import com.user.registration.exception.UserPhoneEmailVerificationException;


@RestControllerAdvice
@Component
public class ExceptionControllerAdvice
{

    private static final Log LOGGER = LogFactory.getLog(ExceptionControllerAdvice.class);
   
    @Autowired
    private Environment environment;

    // add appropriate annotation
    @ExceptionHandler(UserLoginResgistrationException.class)
    public ResponseEntity<ErrorInfo> userLoginResgistrationExceptionHandler(UserLoginResgistrationException exception)
    {
	LOGGER.error(exception.getMessage(), exception);
	ErrorInfo errorInfo = new ErrorInfo();
	errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
	String message= environment.getProperty(exception.getMessage());
	
	if(message.contains("#Redirect"))
	{  
		errorInfo.setRedirectTo(message.split("#")[1].split(":")[1]);
		message=message.split("#")[0];
	}
	errorInfo.setErrorMessage("LOGIN/REGISTRATION FAILED- "+message);
	return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UserPhoneEmailVerificationException.class)
    public ResponseEntity<ErrorInfo> userPhoneEmailVerificationException(UserPhoneEmailVerificationException exception)
    {
	LOGGER.error(exception.getMessage(), exception);
	ErrorInfo errorInfo = new ErrorInfo();
	errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
    String message= environment.getProperty(exception.getMessage());
	
	if(message.contains("#Redirect"))
	{  
		errorInfo.setRedirectTo(message.split("#")[1].split(":")[1]);
		message=message.split("#")[0];
	}
	
	errorInfo.setErrorMessage("PHONE/EMAIL VERIFICATION FAILED- "+message);
	return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(AdministratorException.class)
    public ResponseEntity<ErrorInfo> administratorException(AdministratorException exception)
    {
	LOGGER.error(exception.getMessage(), exception);
	ErrorInfo errorInfo = new ErrorInfo();
	errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
    String message= environment.getProperty(exception.getMessage());
	
	if(message.contains("#Redirect"))
	{  
		errorInfo.setRedirectTo(message.split("#")[1].split(":")[1]);
		message=message.split("#")[0];
	}
	
	errorInfo.setErrorMessage("ADMINISTRATOR - "+message);
	return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    // add appropriate annotation
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception)
    {
	LOGGER.error(exception.getMessage(), exception);
	ErrorInfo errorInfo = new ErrorInfo();
	errorInfo.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	errorInfo.setErrorMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));
	return new ResponseEntity<>(errorInfo,
				    HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // add appropriate annotation
    @ExceptionHandler({MethodArgumentNotValidException.class,ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception)
    {
	LOGGER.error(exception.getMessage(), exception);
	String errorMsg;
	if (exception instanceof MethodArgumentNotValidException)
	{
	    MethodArgumentNotValidException manvException = (MethodArgumentNotValidException) exception;
	    errorMsg = manvException.getBindingResult()
				    .getAllErrors()
				    .stream()
				    .map(ObjectError::getDefaultMessage)
				    .collect(Collectors.joining(", "));

	}
	else
	{
	    ConstraintViolationException cvException = (ConstraintViolationException) exception;
	    errorMsg = cvException.getConstraintViolations()
				  .stream()
				  .map(ConstraintViolation::getMessage)
				  .collect(Collectors.joining(", "));

	}
	ErrorInfo errorInfo = new ErrorInfo();
	errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
	errorInfo.setErrorMessage(errorMsg);
	return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
