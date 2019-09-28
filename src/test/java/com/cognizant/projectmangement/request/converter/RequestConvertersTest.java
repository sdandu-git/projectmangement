/**
 * 
 */
package com.cognizant.projectmangement.request.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.cognizant.projectmangement.jpa.model.ParentTask;
import com.cognizant.projectmangement.jpa.model.Project;
import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.jpa.model.User;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.model.TaskDetails;
import com.cognizant.projectmangement.model.UserDetails;
import com.cognizant.projectmangement.util.RequestConversionUtils;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RequestConvertersTest {

	@InjectMocks
	UserDetailsToUserConverter userDetailsToUserConverter;

	@InjectMocks
	ProjectDetailsToProjectConverter projectDetailsToProjectConverter;

	@InjectMocks
	TaskDetailsToTaskConverter taskDetailsToTaskConverter;

	@InjectMocks
	ParentTaskDetailsToParentTaskConverter parentTaskDetailsToParentTaskConverter;

	@Mock
	RequestConversionUtils requestConversionUtils;

	@Mock
	Project mockProjectData;

	@Mock
	Task mockTaskData;
	
	@Mock
	ParentTask mockParentTaskData;

	private static final String TEST = "TEST";
	private static final int PRIORITY = 10;
	private static final int TEST_ID = 1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(userDetailsToUserConverter, "requestConversionUtils", requestConversionUtils);
		when(requestConversionUtils.populateProjectDataFromProjectDetails(Mockito.any(ProjectDetails.class)))
				.thenReturn(mockProjectData);
		when(requestConversionUtils.populateTaskDataFromTaskDetails(Mockito.any(TaskDetails.class),
				Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(mockTaskData);
	}

	@Test
	public void testUserDetailsToUserConverterScenarioOne() {
		User userData = userDetailsToUserConverter.convert(getUserDetails(TEST_ID));
		assertEquals(userData.getUserId(), TEST_ID);
	}

	@Test
	public void testUserDetailsToUserConverterScenarioTwo() {
		UserDetails userDetails = getUserDetails(0);
		userDetails.setProjectDetails(null);
		userDetails.setTaskDetails(null);
		User userData = userDetailsToUserConverter.convert(userDetails);
		assertEquals(userData.getUserId(), 0);
	}

	@Test
	public void testProjectDetailsToProjectConverter() {
		Project projectData = projectDetailsToProjectConverter.convert(getProjectDetails());
		assertEquals(projectData.getProjectId(), 0);
	}

	@Test
	public void testTaskDetailsToTaskConverter() {
		Task taskData = taskDetailsToTaskConverter.convert(getTaskDetails());
		assertEquals(taskData.getTaskId(), 0);
	}

	@Test
	public void testParentTaskDetailsToParentTaskConverter() {
		when(requestConversionUtils.populateParentTaskDataFromParentTaskDetails(Mockito.any(ParentTaskDetails.class),
				Mockito.anyBoolean())).thenReturn(mockParentTaskData);
		ParentTask parentTaskData = parentTaskDetailsToParentTaskConverter.convert(getParentTaskDetails());
		assertEquals(parentTaskData.getParentId(), 0);
	}

	private ParentTaskDetails getParentTaskDetails() {
		ParentTaskDetails parentTaskDetails = new ParentTaskDetails();
		parentTaskDetails.setParentId(TEST_ID);
		parentTaskDetails.setParentTaskDescription(TEST);
		parentTaskDetails.setProjectDetails(null);
		return parentTaskDetails;
	}

	/**
	 * 
	 * @return
	 */
	private UserDetails getUserDetails(int userId) {
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId(userId);
		userDetails.setFirstName(TEST);
		userDetails.setLastName(TEST);
		userDetails.setEmployeeId(userId);
		userDetails.setProjectDetails(getProjectDetails());
		userDetails.setTaskDetails(getTaskDetails());
		return userDetails;
	}

	/**
	 * 
	 * @return
	 */
	private TaskDetails getTaskDetails() {
		TaskDetails taskDetails = new TaskDetails();
		taskDetails.setPriority(PRIORITY);
		taskDetails.setTaskDescription(TEST);
		taskDetails.setTaskId(TEST_ID);
		taskDetails.setStartDate(LocalDate.now());
		taskDetails.setEndDate(LocalDate.now().plusDays(1));
		return taskDetails;
	}

	/**
	 * 
	 * @return
	 */
	private ProjectDetails getProjectDetails() {
		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setPriority(PRIORITY);
		projectDetails.setProjectDescription(TEST);
		projectDetails.setProjectId(TEST_ID);
		projectDetails.setStartDate(LocalDate.now());
		projectDetails.setEndDate(LocalDate.now().plusDays(1));
		return projectDetails;
	}
}
