/**
 * 
 */
package com.cognizant.projectmangement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserNotFoundException if User with userId not found
 * 
 * @author CTS
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, code = HttpStatus.NOT_FOUND, reason = "UserId Not Found")
public class UserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(int userId) {
		super(String.format("User with UserId : '%s'", userId, " Not found"));
	}

}
