/**
 * 
 */
package com.cognizant.projectmangement.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.projectmangement.exception.TaskCreationException;
import com.cognizant.projectmangement.exception.TaskNotFoundException;
import com.cognizant.projectmangement.exception.UserNotFoundException;
import com.cognizant.projectmangement.model.TaskDetails;
import com.cognizant.projectmangement.service.TaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * Tasks module controller
 * 
 * @author CTS
 *
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/tasks")
@Slf4j
public class TaskResource {

	@Autowired
	TaskService taskService;

	/**
	 * Creates Task
	 * 
	 * @param taskDetailsRequest
	 * @return taskDetailsResponse
	 * @throws UserNotFoundException
	 * @throws TaskCreationException
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskDetails> createTask(@RequestBody TaskDetails taskDetailsRequest)
			throws UserNotFoundException, TaskCreationException {
		log.info("Create Task request received: " + taskDetailsRequest);
		return (ResponseEntity<TaskDetails>) ResponseEntity.ok(taskService.createTask(taskDetailsRequest));
	}

	/**
	 * Lists all tasks
	 * 
	 * @return list of taskDetails
	 */
	@GetMapping
	public ResponseEntity<List<TaskDetails>> findAllTasks() {
		log.info("Find all tasks request received: ");
		return ResponseEntity.ok(taskService.findAllTaskDetails());
	}

	/**
	 * Find task by taskId
	 * 
	 * @param taskId
	 * @return taskDetails
	 * @throws TaskNotFoundException
	 */
	@GetMapping(path = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskDetails> findTaskByTaskId(@PathVariable Integer taskId) throws TaskNotFoundException {
		log.info("Find by taskId " + taskId + " request received: ");
		return ResponseEntity.ok(taskService.findTaskDetailsById(taskId));
	}

	/**
	 * Updates Task Details
	 * 
	 * @param taskDetailsRequest
	 * @return taskDetailsResponse
	 * @throws TaskCreationException
	 * @throws UserNotFoundException
	 * @throws TaskNotFoundException
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskDetails> updateTaskDetails(@RequestBody TaskDetails taskDetailsRequest)
			throws TaskCreationException, UserNotFoundException, TaskNotFoundException {
		log.info("Update Task Details request received: " + taskDetailsRequest);
		return (ResponseEntity<TaskDetails>) ResponseEntity.ok(taskService.updateTask(taskDetailsRequest));
	}

	/**
	 * Deletes Task
	 * 
	 * @param taskDetailsRequest
	 * @return taskId
	 * @throws TaskNotFoundException
	 */
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> deleteTask(@RequestBody TaskDetails taskDetailsRequest)
			throws TaskNotFoundException {
		log.info("Delete Task request received: " + taskDetailsRequest);
		taskService.deleteTask(taskDetailsRequest);
		return ResponseEntity.ok(taskDetailsRequest.getTaskId());
	}

	/**
	 * Deletes Task by taskId
	 * 
	 * @param taskId
	 * @return taskId
	 * @throws TaskNotFoundException
	 */
	@DeleteMapping(path = "/{taskId}")
	public ResponseEntity<Integer> deleteTaskByTaskId(@PathVariable Integer taskId) throws TaskNotFoundException {
		log.info("Delete Task request received for taskId: " + taskId);
		taskService.deleteTaskByTaskId(taskId);
		return ResponseEntity.ok(taskId);
	}
}
