package com.tech.identity.management.exception;

import org.springframework.security.core.AuthenticationException;

public class UserDisabledException extends AuthenticationException{

	public UserDisabledException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}
	
	public UserDisabledException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
