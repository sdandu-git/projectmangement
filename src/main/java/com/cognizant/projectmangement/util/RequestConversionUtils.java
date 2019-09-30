/**
 * 
 */
package com.cognizant.projectmangement.util;

import org.springframework.stereotype.Component;

import com.cognizant.projectmangement.jpa.model.ParentTask;
import com.cognizant.projectmangement.jpa.model.Project;
import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.model.TaskDetails;

/**
 * API to MySQLDB Request Conversion Utils
 * 
 * @author CTS
 *
 */
/**
 * 
 */

@Component
public class RequestConversionUtils {

	/**
	 * Returns projectData from projectDetails
	 * 
	 * @param projectDetails
	 * @return projectData
	 */
	public Project populateProjectDataFromProjectDetails(ProjectDetails projectDetails) {
		Project projectData = new Project();
		if (projectDetails.getProjectId() > 0) {
			projectData.setProjectId(projectDetails.getProjectId());
		}
		projectData.setProject(projectDetails.getProjectDescription());
		projectData.setPriority(projectDetails.getPriority());
		if (null != projectDetails.getStartDate()) {
			projectData.setStartDate(projectDetails.getStartDate());
		}
		if (null != projectDetails.getEndDate()) {
			projectData.setEndDate(projectDetails.getEndDate());
		}
		return projectData;
	}

	/**
	 * Populates taskData from taskDetails
	 * 
	 * @param taskDetails
	 * @param projectDetailsCheck
	 * @param parentTaskDetailsCheck
	 * @return taskData
	 */
	public Task populateTaskDataFromTaskDetails(TaskDetails taskDetails, boolean projectDetailsCheck,
			boolean parentTaskDetailsCheck) {
		Task taskData = new Task();
		if (taskDetails.getTaskId() > 0) {
			taskData.setTaskId(taskDetails.getTaskId());
		}
		taskData.setTaskDescription(taskDetails.getTaskDescription());
		taskData.setStatus(taskDetails.getTaskStatus());
		taskData.setPriority(taskDetails.getPriority());
		if (null != taskDetails.getStartDate()) {
			taskData.setStartDate(taskDetails.getStartDate());
		}
		if (null != taskDetails.getEndDate()) {
			taskData.setEndDate(taskDetails.getEndDate());
		}
		if ((null != taskDetails.getParentTaskDetails()) && parentTaskDetailsCheck
				&& (taskDetails.getParentTaskDetails().getParentId() > 0)) {
			taskData.setParentTask(
					populateParentTaskDataFromParentTaskDetails(taskDetails.getParentTaskDetails(), false));
		}

		if ((null != taskDetails.getProjectDetails()) && projectDetailsCheck
				&& (taskDetails.getProjectDetails().getProjectId() > 0)) {
			taskData.setProject(populateProjectDataFromProjectDetails(taskDetails.getProjectDetails()));
		}

		return taskData;
	}

	/**
	 * Returns parentTask from parentTaskDetails
	 * 
	 * @param parentTaskDetails
	 * @param projectDetailsCheck
	 * @return projectTaskData
	 */
	public ParentTask populateParentTaskDataFromParentTaskDetails(ParentTaskDetails parentTaskDetails,
			boolean projectDetailsCheck) {
		ParentTask parentTask = new ParentTask();
		if (parentTaskDetails.getParentId() > 0) {
			parentTask.setParentId(parentTaskDetails.getParentId());
		}
		parentTask.setParentTask(parentTaskDetails.getParentTaskDescription());
		if ((null != parentTaskDetails.getProjectDetails()) && projectDetailsCheck
				&& (parentTaskDetails.getProjectDetails().getProjectId() > 0)) {
			parentTask.setProject(populateProjectDataFromProjectDetails(parentTaskDetails.getProjectDetails()));
		}
		return parentTask;
	}
}