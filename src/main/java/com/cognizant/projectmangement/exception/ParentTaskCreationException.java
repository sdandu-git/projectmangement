/**
 * 
 */
package com.cognizant.projectmangement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Parent TaskCreation Exception for parent-task creation / update process
 * 
 * @author CTS
 *
 */
@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, code = HttpStatus.NOT_IMPLEMENTED, reason = "ParentTask Creation or modification failed")
public class ParentTaskCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParentTaskCreationException(String parentTaskField) {
		super(String.format("Parent Task Creation / Update error : '%s'", parentTaskField, " cannot be empty"));
	}

}
