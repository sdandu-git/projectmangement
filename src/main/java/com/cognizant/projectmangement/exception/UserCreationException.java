/**
 * 
 */
package com.cognizant.projectmangement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserCreationException
 * @author CTS
 *
 */
@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, code = HttpStatus.NOT_IMPLEMENTED, reason = "User Creation or modification failed")
public class UserCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserCreationException(String userField) {
		super(String.format("User Creation error : '%s'", userField, " cannot be empty"));
	}
}
