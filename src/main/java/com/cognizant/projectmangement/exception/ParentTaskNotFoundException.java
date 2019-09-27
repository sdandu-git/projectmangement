/**
 * 
 */
package com.cognizant.projectmangement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ParentTaskNotFoundException if parentTask with parentTaskId not found
 * 
 * @author CTS
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, code = HttpStatus.NOT_FOUND, reason = "ParentTaskId Not Found")
public class ParentTaskNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParentTaskNotFoundException(int parentTaskId) {
		super(String.format("Parent Task with parentTaskId : '%s'", parentTaskId, " Not found"));
	}

}
