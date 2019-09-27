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

import com.cognizant.projectmangement.exception.ProjectCreationException;
import com.cognizant.projectmangement.exception.ProjectNotFoundException;
import com.cognizant.projectmangement.exception.UserCreationException;
import com.cognizant.projectmangement.exception.UserNotFoundException;
import com.cognizant.projectmangement.jpa.model.ParentTask;
import com.cognizant.projectmangement.jpa.model.Project;
import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.jpa.model.User;
import com.cognizant.projectmangement.jpa.repository.ParentTaskRepository;
import com.cognizant.projectmangement.jpa.repository.ProjectRepository;
import com.cognizant.projectmangement.jpa.repository.TaskRepository;
import com.cognizant.projectmangement.jpa.repository.UserRepository;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.model.UserDetails;
import com.cognizant.projectmangement.request.converter.ProjectDetailsToProjectConverter;
import com.cognizant.projectmangement.response.converter.ProjectToProjectDetailsConverter;
import com.cognizant.projectmangement.response.converter.UserToUserDetailsConverter;
import com.cognizant.projectmangement.service.ProjectService;

import lombok.extern.slf4j.Slf4j;

/**
 * Project Service Implementation
 * 
 * @author CTS
 */
@Component
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	ProjectDetailsToProjectConverter projectRequestConverter;

	@Autowired
	ProjectToProjectDetailsConverter projectResponseConverter;

	@Autowired
	UserToUserDetailsConverter userToUserDetailsConverter;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	ParentTaskRepository parentTaskRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public ProjectDetails createProject(ProjectDetails projectDetailsRequest)
			throws UserNotFoundException, ProjectCreationException {
		validateNewProjectData(projectDetailsRequest);
		// Step 1: Create Project Data
		Project projectDataResponse = projectRepository.save(projectRequestConverter.convert(projectDetailsRequest));
		ProjectDetails projectDetailsResponse = projectResponseConverter.convert(projectDataResponse);
		// Step 2: Update users table with projectId References
		UserDetails userDetailsUpdated = updateProjectIdReferencesForUsers(projectDetailsRequest, projectDataResponse);
		projectDetailsResponse.setUserDetails(userDetailsUpdated);
		return projectDetailsResponse;
	}

	/**
	 * Updates ProjectId references in users table for assigned User
	 * 
	 * @param projectDetailsRequest
	 * @param projectDataResponse
	 * @return userDetailsUpdated
	 * @throws UserNotFoundException
	 */
	private UserDetails updateProjectIdReferencesForUsers(ProjectDetails projectDetailsRequest,
			Project projectDataResponse) throws UserNotFoundException {
		if (null != projectDetailsRequest.getUserDetails()) {
			int userId = projectDetailsRequest.getUserDetails().getUserId();
			Optional<User> userDataOpt = userRepository.findById(userId);
			if (!userDataOpt.isPresent()) {
				log.error("User with userId " + userId + " not found");
				throw new UserNotFoundException(userId);
			}
			User userData = userDataOpt.get();
			userData.setProject(projectDataResponse);
			userRepository.save(userData);
			return userToUserDetailsConverter.convert(userData);
		}
		return null;
	}

	/**
	 * validates new project data and throws error if there is validation error
	 * 
	 * @param projectRequest
	 * @throws ProjectCreationException
	 */
	private void validateNewProjectData(ProjectDetails projectRequest) throws ProjectCreationException {
		if (StringUtils.isBlank(projectRequest.getProjectDescription())) {
			log.error("Project Creation validation failed  projectDescription is blank");
			throw new ProjectCreationException("projectDescription");
		}

		if (null != projectRequest.getStartDate()) {
			if (null == projectRequest.getEndDate()) {
				log.error("Project Creation validation failed  endDate is null");
				throw new ProjectCreationException("endDate");
			}
			// Make sure endDate is always greater than startDate by 1 day
			else if (!validateProjectDates(projectRequest.getStartDate(), projectRequest.getEndDate())) {
				log.error("Project Creation validation failed  endDate is not after startDate");
				throw new ProjectCreationException("endDate");
			}
		}
		// if startDate and endDate are null, default startDate will be today and
		// endDate is tomorrow
		else if ((null == projectRequest.getStartDate()) && (null == projectRequest.getEndDate())) {
			projectRequest.setStartDate(LocalDate.now());
			projectRequest.setEndDate(LocalDate.now().plusDays(1));
		}
	}

	/**
	 * Validates endDate and startDate , returns true only if endDate is after
	 * startDate
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private boolean validateProjectDates(LocalDate startDate, LocalDate endDate) {
		return endDate.isAfter(startDate);
	}

	@Override
	public ProjectDetails findProjectByProjectId(int projectId) throws ProjectNotFoundException {
		Optional<Project> projectData = projectRepository.findById(projectId);
		if (!projectData.isPresent()) {
			log.error("Project with projectId " + projectId + " not found");
			throw new ProjectNotFoundException(projectId);
		}
		return projectResponseConverter.convert(projectData.get());
	}

	@Override
	public List<ProjectDetails> findAllProjects() {
		List<ProjectDetails> projectDetailsList = new ArrayList<ProjectDetails>();
		Iterable<Project> projectDataList = projectRepository.findAll();
		for (Project projectData : projectDataList) {
			projectDetailsList.add(projectResponseConverter.convert(projectData));
		}
		return projectDetailsList;
	}

	@Override
	public ProjectDetails updateProjectDetails(ProjectDetails projectDetailsRequest)
			throws ProjectNotFoundException, UserNotFoundException {
		findProjectByProjectId(projectDetailsRequest.getProjectId());
		// Step 1: Create Project Data
		Project projectDataResponse = projectRepository.save(projectRequestConverter.convert(projectDetailsRequest));
		ProjectDetails projectDetailsResponse = projectResponseConverter.convert(projectDataResponse);
		// Step 2: Update users table with projectId References
		UserDetails userDetailsResponse = updateProjectIdReferencesForUsers(projectDetailsRequest, projectDataResponse);
		projectDetailsResponse.setUserDetails(userDetailsResponse);
		return projectDetailsResponse;
	}

	@Override
	public void deleteProject(ProjectDetails projectRequest) throws ProjectNotFoundException {
		// Step 1: find Project via projectId
		findProjectByProjectId(projectRequest.getProjectId());

		// Step 2: nullify projectId references for projectId in users table
		deleteProjectIdReferenceFromUser(projectRequest.getProjectId());

		// Step 3: delete all tasks from Task table with matching projectId
		deleteTaskProjectIdReferences(projectRequest.getProjectId());

		// Step 4: delete all parentTasks from ParentTask table with matching projectId
		deleteParentTasksForProjectId(projectRequest.getProjectId());

		// Step 5: deletes Project if matching projectId found in DB
		projectRepository.delete(projectRequestConverter.convert(projectRequest));
	}

	@Override
	public void deleteProjectById(int projectId) throws ProjectNotFoundException {
		// Step 1: find Project via projectId
		findProjectByProjectId(projectId);

		// Step 2: nullify projectId references for projectId in users table
		deleteProjectIdReferenceFromUser(projectId);

		// Step 3: delete all tasks from Task table with matching projectId
		deleteTaskProjectIdReferences(projectId);

		// Step 4: delete all parentTasks from ParentTask table with matching projectId
		deleteParentTasksForProjectId(projectId);

		// Step 5: deletes Project if matching projectId found in DB
		projectRepository.deleteById(projectId);
	}

	/**
	 * Deletes projectId reference from user table
	 * 
	 * @param projectId
	 * @throws UserNotFoundException
	 * @throws UserCreationException
	 */
	private void deleteProjectIdReferenceFromUser(int projectId) {
		for (User userData : userRepository.findAll()) {
			if ((null != userData.getProject()) && (userData.getProject().getProjectId() == projectId)) {
				userData.setProject(null);
				userRepository.save(userData);
			}
		}
	}

	/**
	 * Deletes all tasks associated to projectId
	 * 
	 * @param projectId
	 */
	private void deleteTaskProjectIdReferences(int projectId) {
		for (Task taskData : taskRepository.findAll()) {
			if ((null != taskData.getProject()) && (taskData.getProject().getProjectId() == projectId)) {
				taskRepository.delete(taskData);
			}
		}
	}

	/**
	 * Deletes all parentTasks for a given projectId
	 * 
	 * @param projectId
	 */
	private void deleteParentTasksForProjectId(int projectId) {
		for (ParentTask parentTaskData : parentTaskRepository.findAll()) {
			if ((null != parentTaskData.getProject()) && (parentTaskData.getProject().getProjectId() == projectId)) {
				parentTaskRepository.delete(parentTaskData);
			}
		}
	}
}
