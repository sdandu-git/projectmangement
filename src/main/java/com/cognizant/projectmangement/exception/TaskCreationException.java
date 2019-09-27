/**
 * 
 */
package com.cognizant.projectmangement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskCreation Exception for task creation / update process
 * 
 * @author CTS
 *
 */
@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, code = HttpStatus.NOT_IMPLEMENTED, reason = "Task Creation or modification failed")
public class TaskCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskCreationException(String taskField) {
		super(String.format("Task Creation / Update error : '%s'", taskField, " cannot be empty"));
	}

}
