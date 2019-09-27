/**
 * 
 */
package com.cognizant.projectmangement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cognizant.projectmangement.exception.ParentTaskCreationException;
import com.cognizant.projectmangement.exception.ParentTaskNotFoundException;
import com.cognizant.projectmangement.jpa.model.ParentTask;
import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.jpa.repository.ParentTaskRepository;
import com.cognizant.projectmangement.jpa.repository.TaskRepository;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.request.converter.ParentTaskDetailsToParentTaskConverter;
import com.cognizant.projectmangement.response.converter.ParentTaskToParentTaskDetailsConverter;
import com.cognizant.projectmangement.service.ParentTaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * Parent Task Service Implementation
 * 
 * @author CTS
 *
 */
@Component
@Service
@Slf4j
public class ParentTaskServiceImpl implements ParentTaskService {

	@Autowired
	ParentTaskRepository parentTaskRepository;

	@Autowired
	ParentTaskDetailsToParentTaskConverter parentTaskRequestConverter;

	@Autowired
	ParentTaskToParentTaskDetailsConverter parentTaskResponseConverter;

	@Autowired
	TaskRepository taskRepository;

	@Override
	public ParentTaskDetails createParentTask(ParentTaskDetails parentTaskDetailsRequest)
			throws ParentTaskCreationException {
		// validates parentTask description and projectId
		validateParentTaskDetails(parentTaskDetailsRequest);
		return parentTaskResponseConverter
				.convert(parentTaskRepository.save(parentTaskRequestConverter.convert(parentTaskDetailsRequest)));
	}

	@Override
	public ParentTaskDetails updateParentTask(ParentTaskDetails parentTaskDetailsRequest)
			throws ParentTaskCreationException, ParentTaskNotFoundException {
		findParentTaskDetailsById(parentTaskDetailsRequest.getParentId());
		// validates parentTask description and projectId
		validateParentTaskDetails(parentTaskDetailsRequest);
		return parentTaskResponseConverter
				.convert(parentTaskRepository.save(parentTaskRequestConverter.convert(parentTaskDetailsRequest)));
	}

	@Override
	public ParentTaskDetails findParentTaskDetailsById(int parentTaskId) throws ParentTaskNotFoundException {
		Optional<ParentTask> parentTask = parentTaskRepository.findById(parentTaskId);
		if (!parentTask.isPresent()) {
			log.error("Parent Task with taskId " + parentTaskId + " not found");
			throw new ParentTaskNotFoundException(parentTaskId);
		}
		return parentTaskResponseConverter.convert(parentTask.get());
	}

	@Override
	public List<ParentTaskDetails> findAllParentTaskDetails() {
		List<ParentTaskDetails> parentTaskDetailList = new ArrayList<ParentTaskDetails>();
		Iterable<ParentTask> parentTaskDataList = parentTaskRepository.findAll();
		for (ParentTask parentTask : parentTaskDataList) {
			parentTaskDetailList.add(parentTaskResponseConverter.convert(parentTask));
		}
		return parentTaskDetailList;
	}

	@Override
	public void deleteParentTask(ParentTaskDetails parentTaskDetailsRequest) throws ParentTaskNotFoundException {
		// find parentTask via parentTaskId
		findParentTaskDetailsById(parentTaskDetailsRequest.getParentId());
		// delete all tasks associated with this parentTaskId
		deleteParentTaskIdReferences(parentTaskDetailsRequest.getParentId());
		// deletes parentTask if matching parentTaskId found in DB
		parentTaskRepository.delete(parentTaskRequestConverter.convert(parentTaskDetailsRequest));
	}

	@Override
	public void deleteParentTaskByTaskId(int parentTaskId) throws ParentTaskNotFoundException {
		// find parentTask via parentTaskId
		findParentTaskDetailsById(parentTaskId);
		// delete all tasks associated with this parentTaskId
		deleteParentTaskIdReferences(parentTaskId);
		// deletes parentTask if matching parentTaskId found in DB
		parentTaskRepository.deleteById(parentTaskId);
	}

	/**
	 * Validates new parent task data and throws error if there is validation error
	 * 
	 * @param parentTaskDetailsRequest
	 * @throws ParentTaskCreationException
	 */
	private void validateParentTaskDetails(ParentTaskDetails parentTaskDetailsRequest)
			throws ParentTaskCreationException {
		if (StringUtils.isBlank(parentTaskDetailsRequest.getParentTaskDescription())) {
			log.error("Parent Task Creation validation failed  parent task description is blank");
			throw new ParentTaskCreationException("parentTaskDescription");
		}
	}

	/**
	 * Deletes tasks with parentTaskId references from Task table
	 * 
	 * @param parentTaskId
	 */
	private void deleteParentTaskIdReferences(int parentTaskId) {
		for (Task taskData : taskRepository.findAll()) {
			if ((null != taskData.getParentTask()) && (taskData.getParentTask().getParentId() == parentTaskId)) {
				taskRepository.delete(taskData);
			}
		}
	}

}
