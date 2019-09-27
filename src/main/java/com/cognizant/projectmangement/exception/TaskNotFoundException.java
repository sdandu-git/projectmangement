/**
 * 
 */
package com.cognizant.projectmangement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskNotFoundException if Task with taskId not found
 * 
 * @authorCTS
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, code = HttpStatus.NOT_FOUND, reason = "TaskId Not Found")
public class TaskNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskNotFoundException(int taskId) {
		super(String.format("Task with taskId : '%s'", taskId, " Not found"));
	}

}
