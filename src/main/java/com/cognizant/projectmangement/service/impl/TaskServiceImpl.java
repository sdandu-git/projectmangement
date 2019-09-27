/**
 * 
 */
package com.cognizant.projectmangement.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cognizant.projectmangement.exception.TaskCreationException;
import com.cognizant.projectmangement.exception.TaskNotFoundException;
import com.cognizant.projectmangement.exception.UserNotFoundException;
import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.jpa.model.User;
import com.cognizant.projectmangement.jpa.repository.TaskRepository;
import com.cognizant.projectmangement.jpa.repository.UserRepository;
import com.cognizant.projectmangement.model.TaskDetails;
import com.cognizant.projectmangement.model.UserDetails;
import com.cognizant.projectmangement.request.converter.TaskDetailsToTaskConverter;
import com.cognizant.projectmangement.response.converter.TaskToTaskDetailsConverter;
import com.cognizant.projectmangement.response.converter.UserToUserDetailsConverter;
import com.cognizant.projectmangement.service.TaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * Task Service Implementation for tasks module
 * 
 * @author CTS
 *
 */
@Component
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	TaskDetailsToTaskConverter taskRequestConverter;

	@Autowired
	TaskToTaskDetailsConverter taskResponseConverter;

	@Autowired
	UserToUserDetailsConverter userToUserDetailsConverter;

	@Autowired
	UserRepository userRepository;

	@Override
	public TaskDetails createTask(TaskDetails taskDetailsRequest) throws UserNotFoundException, TaskCreationException {
		// validate Task Details to Create
		validateTaskDetails(taskDetailsRequest);
		// Step 1: Create Task
		Task taskDataResponse = taskRepository.save(taskRequestConverter.convert(taskDetailsRequest));
		TaskDetails taskDetailsResponse = taskResponseConverter.convert(taskDataResponse);
		// Step 2: Map newly created taskId to user based on userId
		UserDetails userDetailsUpdated = updateUserTaskIdReference(taskDetailsRequest, taskDataResponse);
		taskDetailsResponse.setUserDetails(userDetailsUpdated);
		return taskDetailsResponse;
	}

	/**
	 * Updates taskId reference to userData
	 * 
	 * @param taskDetailsRequest
	 * @param taskDataResponse
	 * @return
	 * @throws UserNotFoundException
	 */
	private UserDetails updateUserTaskIdReference(TaskDetails taskDetailsRequest, Task taskDataResponse)
			throws UserNotFoundException {
		if (null != taskDetailsRequest.getUserDetails()) {
			int userId = taskDetailsRequest.getUserDetails().getUserId();
			Optional<User> userDataOpt = userRepository.findById(userId);
			if (!userDataOpt.isPresent()) {
				log.error("User with userId " + userId + " not found");
				throw new UserNotFoundException(userId);
			}
			User userData = userDataOpt.get();
			userData.setTask(taskDataResponse);
			userRepository.save(userData);
			return userToUserDetailsConverter.convert(userData);
		}
		return null;
	}

	/**
	 * Validates Task creation or update details
	 * 
	 * @param taskDetailsRequest
	 * @throws TaskCreationException
	 */
	private void validateTaskDetails(TaskDetails taskDetailsRequest) throws TaskCreationException {
		if (StringUtils.isBlank(taskDetailsRequest.getTaskDescription())) {
			log.error("Task Creation validation failed  taskDescription is blank");
			throw new TaskCreationException("taskDescription");
		}

		if (StringUtils.isBlank(taskDetailsRequest.getTaskStatus())) {
			taskDetailsRequest.setTaskStatus("ACTIVE");
		}

		if (null != taskDetailsRequest.getStartDate()) {
			if (null == taskDetailsRequest.getEndDate()) {
				log.error("Task Creation validation failed  endDate is null");
				throw new TaskCreationException("endDate");
			}
			// Make sure endDate is always greater than startDate by 1 day
			else if (!validateTaskDates(taskDetailsRequest.getStartDate(), taskDetailsRequest.getEndDate())) {
				log.error("Task Creation validation failed  endDate is not after startDate");
				throw new TaskCreationException("endDate");
			}
		}
		// if startDate and endDate are null, default startDate will be today and
		// endDate is tomorrow
		else if ((null == taskDetailsRequest.getStartDate()) && (null == taskDetailsRequest.getEndDate())) {
			taskDetailsRequest.setStartDate(LocalDate.now());
			taskDetailsRequest.setEndDate(LocalDate.now().plusDays(1));
		}
	}

	/**
	 * Validates endDate and startDate , returns true only if endDate is after
	 * startDate
	 * 
	 * @param startDate
	 * @param endDate
	 * @return true if endDate is greater than startDate else false
	 */
	private boolean validateTaskDates(LocalDate startDate, LocalDate endDate) {
		return endDate.isAfter(startDate);
	}

	@Override
	public TaskDetails updateTask(TaskDetails taskDetailsRequest)
			throws TaskCreationException, UserNotFoundException, TaskNotFoundException {
		findTaskDetailsById(taskDetailsRequest.getTaskId());
		// validate Task Details to Update
		validateTaskDetails(taskDetailsRequest);
		// Step 1: Create Task
		Task taskDataResponse = taskRepository.save(taskRequestConverter.convert(taskDetailsRequest));
		TaskDetails taskDetailsResponse = taskResponseConverter.convert(taskDataResponse);
		// Step 2: Map newly created taskId to user based on userId
		UserDetails userDetailsUpdated = updateUserTaskIdReference(taskDetailsRequest, taskDataResponse);
		taskDetailsResponse.setUserDetails(userDetailsUpdated);
		return taskDetailsResponse;
	}

	@Override
	public TaskDetails findTaskDetailsById(int taskId) throws TaskNotFoundException {
		Optional<Task> taskData = taskRepository.findById(taskId);
		if (!taskData.isPresent()) {
			log.error("Task with taskId " + taskId + " not found");
			throw new TaskNotFoundException(taskId);
		}
		return taskResponseConverter.convert(taskData.get());
	}

	@Override
	public List<TaskDetails> findAllTaskDetails() {
		List<TaskDetails> taskDetailsList = new ArrayList<TaskDetails>();
		Iterable<Task> taskDataList = taskRepository.findAll();
		for (Task taskData : taskDataList) {
			taskDetailsList.add(taskResponseConverter.convert(taskData));
		}
		return taskDetailsList;
	}

	@Override
	public void deleteTask(TaskDetails taskDetails) throws TaskNotFoundException {
		// find task via taskId
		findTaskDetailsById(taskDetails.getTaskId());
		// nullify taskId references for taskId in users table
		deleteUserTaskIdReferences(taskDetails.getTaskId());
		// deletes Task if matching taskId found in DB
		taskRepository.delete(taskRequestConverter.convert(taskDetails));
	}

	/**
	 * Deletes taskId reference from userData
	 * 
	 * @param taskId
	 */
	private void deleteUserTaskIdReferences(int taskId) {
		for (User userData : userRepository.findAll()) {
			if ((null != userData.getTask()) && (userData.getTask().getTaskId() == taskId)) {
				userData.setTask(null);
				userRepository.save(userData);
			}
		}
	}

	@Override
	public void deleteTaskByTaskId(int taskId) throws TaskNotFoundException {
		// find task via taskId
		findTaskDetailsById(taskId);
		// nullify taskId references for taskId in users table
		deleteUserTaskIdReferences(taskId);
		// deletes Task if matching taskId found in DB
		taskRepository.deleteById(taskId);
	}
}