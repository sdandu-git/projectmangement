/**
 * 
 */
package com.cognizant.projectmangement.util;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cognizant.projectmangement.jpa.model.ParentTask;
import com.cognizant.projectmangement.jpa.model.Project;
import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.model.TaskDetails;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RequestConversionUtilsTest {

	@InjectMocks
	RequestConversionUtils requestConversionUtils;

	private static final String TEST = "TEST";
	private static final int PRIORITY = 10;
	private static final int TEST_ID = 1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testProjectDetailsScenarioOne() {
		ProjectDetails projectDetails = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		Project projectData = requestConversionUtils.populateProjectDataFromProjectDetails(projectDetails);
		assertEquals(projectData.getProjectId(), TEST_ID);
	}

	@Test
	public void testProjectDetailsScenarioTwo() {
		ProjectDetails projectDetails = getProjectDetails(0, LocalDate.now(), LocalDate.now().plusDays(1));
		projectDetails.setStartDate(null);
		projectDetails.setEndDate(null);
		Project projectData = requestConversionUtils.populateProjectDataFromProjectDetails(projectDetails);
		assertEquals(projectData.getProjectId(), 0);
	}

	@Test
	public void testTaskDetailsScenarioOne() {
		ProjectDetails projectDetails = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		ParentTaskDetails parentTaskDetails = getParentTaskDetails(TEST_ID, projectDetails);
		TaskDetails taskDetails = getTaskDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1),
				parentTaskDetails, projectDetails);
		Task taskData = requestConversionUtils.populateTaskDataFromTaskDetails(taskDetails, true, true);
		assertEquals(taskData.getTaskId(), TEST_ID);
	}

	@Test
	public void testTaskDetailsScenarioTwo() {
		ProjectDetails projectDetails = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		ParentTaskDetails parentTaskDetails = getParentTaskDetails(0, null);
		TaskDetails taskDetails = getTaskDetails(0, null, null, parentTaskDetails, projectDetails);
		Task taskData = requestConversionUtils.populateTaskDataFromTaskDetails(taskDetails, true, true);
		assertEquals(taskData.getTaskId(), 0);
	}

	@Test
	public void testTaskDetailsScenarioThree() {
		ProjectDetails projectDetails = getProjectDetails(0, LocalDate.now(), LocalDate.now().plusDays(1));
		ParentTaskDetails parentTaskDetails = getParentTaskDetails(0, projectDetails);
		TaskDetails taskDetails = getTaskDetails(0, null, null, parentTaskDetails, projectDetails);
		Task taskData = requestConversionUtils.populateTaskDataFromTaskDetails(taskDetails, false, false);
		assertEquals(taskData.getTaskId(), 0);
	}

	@Test
	public void testTaskDetailsScenarioFour() {
		TaskDetails taskDetails = getTaskDetails(0, null, null, null, null);
		Task taskData = requestConversionUtils.populateTaskDataFromTaskDetails(taskDetails, false, false);
		assertEquals(taskData.getTaskId(), 0);
	}

	@Test
	public void testParentTaskDetailsScenarioOne() {
		ParentTaskDetails parentTaskDetails = getParentTaskDetails(TEST_ID, getProjectDetails(TEST_ID, null, null));
		ParentTask parentTaskData = requestConversionUtils
				.populateParentTaskDataFromParentTaskDetails(parentTaskDetails, true);
		assertEquals(parentTaskData.getParentId(), TEST_ID);
	}

	@Test
	public void testParentTaskDetailsScenarioTwo() {
		ParentTaskDetails parentTaskDetails = getParentTaskDetails(0, getProjectDetails(0, null, null));
		ParentTask parentTaskData = requestConversionUtils
				.populateParentTaskDataFromParentTaskDetails(parentTaskDetails, false);
		assertEquals(parentTaskData.getParentId(), 0);
	}

	@Test
	public void testParentTaskDetailsScenarioThree() {
		ParentTaskDetails parentTaskDetails = getParentTaskDetails(0, null);
		ParentTask parentTaskData = requestConversionUtils
				.populateParentTaskDataFromParentTaskDetails(parentTaskDetails, false);
		assertEquals(parentTaskData.getParentId(), 0);
	}

	private ProjectDetails getProjectDetails(int projectId, LocalDate startDate, LocalDate endDate) {
		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setPriority(PRIORITY);
		projectDetails.setProjectDescription(TEST);
		projectDetails.setProjectId(projectId);
		projectDetails.setStartDate(startDate);
		projectDetails.setEndDate(endDate);
		return projectDetails;
	}

	private TaskDetails getTaskDetails(int taskId, LocalDate startDate, LocalDate endDate,
			ParentTaskDetails parentTaskDetails, ProjectDetails projectDetails) {
		TaskDetails taskDetails = new TaskDetails();
		taskDetails.setPriority(PRIORITY);
		taskDetails.setTaskDescription(TEST);
		taskDetails.setTaskId(taskId);
		taskDetails.setStartDate(startDate);
		taskDetails.setEndDate(endDate);
		taskDetails.setParentTaskDetails(parentTaskDetails);
		taskDetails.setProjectDetails(projectDetails);
		return taskDetails;
	}

	private ParentTaskDetails getParentTaskDetails(int parentTaskId, ProjectDetails projectDetails) {
		ParentTaskDetails parentTaskDetails = new ParentTaskDetails();
		parentTaskDetails.setParentId(parentTaskId);
		parentTaskDetails.setParentTaskDescription(TEST);
		parentTaskDetails.setProjectDetails(projectDetails);
		return parentTaskDetails;
	}
}
