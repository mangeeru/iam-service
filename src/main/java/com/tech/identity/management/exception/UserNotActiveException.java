package com.tech.identity.management.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an user status is in-active/Pre-active by
 * its username.
 *
 * @author Anji
 */
public class UserNotActiveException extends AuthenticationException{

	public UserNotActiveException(String msg) {
		super(msg);
	}
	
	public UserNotActiveException(String msg,Throwable cause) {
		super(msg, cause);
	}

}
