/**
 * 
 */
package com.cognizant.projectmangement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ProjectCreationException
 * @author CTS
 *
 */
@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, code = HttpStatus.NOT_IMPLEMENTED, reason = "Project Creation or modification failed")
public class ProjectCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProjectCreationException(String projectField) {
		super(String.format("Project Creation error : '%s'", projectField, " cannot be empty"));
	}
}
