/**
 * 
 */
package com.cognizant.projectmangement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ProjectNotFoundException if Project with projectId not found
 * @author CTS
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, code = HttpStatus.NOT_FOUND, reason = "ProjectId Not Found")
public class ProjectNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProjectNotFoundException(int projectId) {
		super(String.format("Project with ProjectId : '%s'", projectId, " Not found"));
	}

}
