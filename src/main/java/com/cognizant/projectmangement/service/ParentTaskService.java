/**
 * 
 */
package com.cognizant.projectmangement.service;

import java.util.List;

import com.cognizant.projectmangement.exception.ParentTaskCreationException;
import com.cognizant.projectmangement.exception.ParentTaskNotFoundException;
import com.cognizant.projectmangement.model.ParentTaskDetails;

/**
 * ParentTask Service Interface
 * 
 * @author CTS
 *
 */
public interface ParentTaskService {

	/**
	 * Creates Parent Task
	 * 
	 * @param parentTaskDetailsRequest
	 * @return parentTaskDetailsResponse
	 * @throws ParentTaskCreationException
	 */
	ParentTaskDetails createParentTask(ParentTaskDetails parentTaskDetailsRequest) throws ParentTaskCreationException;

	/**
	 * Updates Parent Task
	 * 
	 * @param parentTaskDetailsRequest
	 * @return parentTaskDetailsResponse
	 * @throws ParentTaskCreationException
	 * @throws ParentTaskNotFoundException
	 */
	ParentTaskDetails updateParentTask(ParentTaskDetails parentTaskDetailsRequest)
			throws ParentTaskCreationException, ParentTaskNotFoundException;

	/**
	 * Finds ParentTask by parentTaskId
	 * 
	 * @param parentTaskId
	 * @return parentTaskDetails
	 * @throws ParentTaskNotFoundException
	 */
	ParentTaskDetails findParentTaskDetailsById(int parentTaskId) throws ParentTaskNotFoundException;

	/**
	 * Finds all Parent Task Details
	 * 
	 * @return parentTaskDetails List
	 */
	List<ParentTaskDetails> findAllParentTaskDetails();

	/**
	 * Deletes Parent Task
	 * 
	 * @param parentTaskDetailsRequest
	 * @throws ParentTaskNotFoundException
	 */
	void deleteParentTask(ParentTaskDetails parentTaskDetailsRequest) throws ParentTaskNotFoundException;

	/**
	 * Deletes Parent Task by parentTaskId
	 * 
	 * @param parentTaskId
	 * @throws ParentTaskNotFoundException
	 */
	void deleteParentTaskByTaskId(int parentTaskId) throws ParentTaskNotFoundException;
}
