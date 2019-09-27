/**
 * 
 */
package com.cognizant.projectmangement.service;

import java.util.List;

import com.cognizant.projectmangement.exception.TaskCreationException;
import com.cognizant.projectmangement.exception.TaskNotFoundException;
import com.cognizant.projectmangement.exception.UserNotFoundException;
import com.cognizant.projectmangement.model.TaskDetails;

/**
 * Task Service Interface
 * 
 * @author CTS
 *
 */
public interface TaskService {

	/**
	 * Creates Task
	 * 
	 * @param taskDetailsRequest
	 * @return taskDetailsResponse
	 * @throws UserNotFoundException
	 * @throws TaskCreationException
	 */
	TaskDetails createTask(TaskDetails taskDetailsRequest) throws UserNotFoundException, TaskCreationException;

	/**
	 * Updates Task
	 * 
	 * @param taskDetailsRequest
	 * @return taskDetailsResponse
	 * @throws TaskCreationException
	 * @throws UserNotFoundException
	 * @throws TaskNotFoundException
	 */
	TaskDetails updateTask(TaskDetails taskDetailsRequest)
			throws TaskCreationException, UserNotFoundException, TaskNotFoundException;

	/**
	 * Find taskDetails by taskId
	 * 
	 * @param taskId
	 * @return taskDetails
	 * @throws TaskNotFoundException
	 */
	TaskDetails findTaskDetailsById(int taskId) throws TaskNotFoundException;

	/**
	 * Lists all taskDetails
	 * 
	 * @return taskDetailsList
	 */
	List<TaskDetails> findAllTaskDetails();

	/**
	 * Deletes Task by body
	 * 
	 * @param taskDetails
	 * @throws TaskNotFoundException
	 */
	void deleteTask(TaskDetails taskDetails) throws TaskNotFoundException;

	/**
	 * Deletes Task by taskId
	 * 
	 * @param taskId
	 * @throws TaskNotFoundException
	 */
	void deleteTaskByTaskId(int taskId) throws TaskNotFoundException;
}
