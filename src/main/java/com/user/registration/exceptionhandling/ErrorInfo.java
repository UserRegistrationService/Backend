package com.user.registration.exceptionhandling;

public class ErrorInfo {
	private String errorMessage;
	private Integer errorCode;
    private String redirectTo;
    ErrorInfo()
    {
    	this.redirectTo="";
    }
	public String getErrorMessage() {
		return errorMessage;
	}

	public String getRedirectTo() {
		return redirectTo;
	}

	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
