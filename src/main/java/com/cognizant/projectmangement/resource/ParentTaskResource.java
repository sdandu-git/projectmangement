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

import com.cognizant.projectmangement.exception.ParentTaskCreationException;
import com.cognizant.projectmangement.exception.ParentTaskNotFoundException;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.service.ParentTaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * Tasks module controller
 * 
 * @author CTS
 *
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/parent/tasks")
@Slf4j
public class ParentTaskResource {

	@Autowired
	ParentTaskService parentTaskService;

	/**
	 * Creates Parent Task
	 * 
	 * @param parentTaskDetailsRequest
	 * @return parentTaskDetailsResponse
	 * @throws ParentTaskCreationException
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ParentTaskDetails> createParentTask(@RequestBody ParentTaskDetails parentTaskDetailsRequest)
			throws ParentTaskCreationException {
		log.info("Create Parent Task request received: " + parentTaskDetailsRequest);
		return (ResponseEntity<ParentTaskDetails>) ResponseEntity
				.ok(parentTaskService.createParentTask(parentTaskDetailsRequest));
	}

	/**
	 * Lists all parent tasks
	 * 
	 * @return parentTaskDetails List
	 */
	@GetMapping
	public ResponseEntity<List<ParentTaskDetails>> findAllParentTasks() {
		log.info("Find all parent tasks request received: ");
		return ResponseEntity.ok(parentTaskService.findAllParentTaskDetails());
	}

	/**
	 * Finds ParentTask by parentTaskId
	 * 
	 * @param parentTaskId
	 * @return parentTaskDetails
	 * @throws ParentTaskNotFoundException
	 */
	@GetMapping(path = "/{parentTaskId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ParentTaskDetails> findParentTaskByTaskId(@PathVariable Integer parentTaskId)
			throws ParentTaskNotFoundException {
		log.info("Find by parentTaskId " + parentTaskId + " request received: ");
		return ResponseEntity.ok(parentTaskService.findParentTaskDetailsById(parentTaskId));
	}

	/**
	 * Updates Parent Task
	 * 
	 * @param parentTaskRequest
	 * @return parentTaskResponse
	 * @throws ParentTaskCreationException
	 * @throws ParentTaskNotFoundException
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ParentTaskDetails> updateParentTaskDetails(@RequestBody ParentTaskDetails parentTaskRequest)
			throws ParentTaskCreationException, ParentTaskNotFoundException {
		log.info("Update Parent Task Details request received: " + parentTaskRequest);
		return (ResponseEntity<ParentTaskDetails>) ResponseEntity
				.ok(parentTaskService.updateParentTask(parentTaskRequest));
	}

	/**
	 * Deletes Parent Task by body
	 * 
	 * @param parentTaskDetailsRequest
	 * @return parentTaskId
	 * @throws ParentTaskNotFoundException
	 */
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> deleteParentTask(@RequestBody ParentTaskDetails parentTaskDetailsRequest)
			throws ParentTaskNotFoundException {
		log.info("Delete Parent Task request received: " + parentTaskDetailsRequest);
		parentTaskService.deleteParentTask(parentTaskDetailsRequest);
		return ResponseEntity.ok(parentTaskDetailsRequest.getParentId());
	}

	/**
	 * Deletes Parent Task by parentTaskId
	 * 
	 * @param parentTaskId
	 * @return parentTaskId
	 * @throws ParentTaskNotFoundException
	 */
	@DeleteMapping(path = "/{parentTaskId}")
	public ResponseEntity<Integer> deleteParentTaskByTaskId(@PathVariable Integer parentTaskId)
			throws ParentTaskNotFoundException {
		log.info("Delete Parent Task request received for parentTaskId: " + parentTaskId);
		parentTaskService.deleteParentTaskByTaskId(parentTaskId);
		return ResponseEntity.ok(parentTaskId);
	}
}