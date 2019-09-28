/**
 * 
 */
package com.cognizant.projectmangement.response.converter;

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
import com.cognizant.projectmangement.util.ResponseConversionUtils;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ResponseConvertersTest {

	@InjectMocks
	UserToUserDetailsConverter userToUserDetailsConverter;

	@InjectMocks
	ProjectToProjectDetailsConverter projectToProjectDetailsConverter;

	@InjectMocks
	TaskToTaskDetailsConverter taskToTaskDetailsConverter;

	@InjectMocks
	ParentTaskToParentTaskDetailsConverter parentTaskToParentTaskDetailsConverter;

	@Mock
	ResponseConversionUtils responseConversionUtils;

	@Mock
	UserDetails mockUserDetails;

	@Mock
	ProjectDetails mockProjectDetails;

	@Mock
	TaskDetails mockTaskDetails;

	@Mock
	ParentTaskDetails mockParentTaskDetails;

	private static final String TEST = "TEST";
	private static final int PRIORITY = 10;
	private static final int TEST_ID = 1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(userToUserDetailsConverter, "responseConversionUtils", responseConversionUtils);
		ReflectionTestUtils.setField(projectToProjectDetailsConverter, "responseConversionUtils",
				responseConversionUtils);
		ReflectionTestUtils.setField(taskToTaskDetailsConverter, "responseConversionUtils", responseConversionUtils);
		ReflectionTestUtils.setField(parentTaskToParentTaskDetailsConverter, "responseConversionUtils",
				responseConversionUtils);
	}

	@Test
	public void testUserToUserDetails() {
		User userData = getUserData(TEST_ID);
		when(responseConversionUtils.populateUserDetailsFromUserData(Mockito.any(User.class), Mockito.anyBoolean(),
				Mockito.anyBoolean())).thenReturn(mockUserDetails);
		UserDetails userDetails = userToUserDetailsConverter.convert(userData);
		assertEquals(userDetails.getUserId(), 0);
	}

	@Test
	public void testProjectToProjectDetails() {
		Project projectData = getProjectData(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		when(responseConversionUtils.populateProjectDetailsFromProjectData(Mockito.any(Project.class),
				Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(mockProjectDetails);
		ProjectDetails projectDetails = projectToProjectDetailsConverter.convert(projectData);
		assertEquals(projectDetails.getProjectId(), 0);

	}

	@Test
	public void testTaskToTaskDetails() {
		Task taskData = getTaskData(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		when(responseConversionUtils.populateTaskDetailsFromTaskData(Mockito.any(Task.class), Mockito.anyBoolean(),
				Mockito.anyBoolean(), Mockito.anyBoolean())).thenReturn(mockTaskDetails);
		TaskDetails taskDetails = taskToTaskDetailsConverter.convert(taskData);
		assertEquals(taskDetails.getTaskId(), 0);

	}

	@Test
	public void testParentTaskToParentTaskDetails() {
		ParentTask parentTaskData = getParentTask(TEST_ID);
		when(responseConversionUtils.populateParentTaskDetailsFromParentTaskData(Mockito.any(ParentTask.class),
				Mockito.anyBoolean())).thenReturn(mockParentTaskDetails);
		ParentTaskDetails parentTaskDetails = parentTaskToParentTaskDetailsConverter.convert(parentTaskData);
		assertEquals(parentTaskDetails.getParentId(), 0);
	}

	/**
	 * Builds project Data
	 * 
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Project getProjectData(int projectId, LocalDate startDate, LocalDate endDate) {
		Project projectData = new Project();
		projectData.setProjectId(projectId);
		projectData.setProject(TEST);
		projectData.setPriority(PRIORITY);
		projectData.setStartDate(startDate);
		projectData.setEndDate(endDate);
		return projectData;
	}

	/**
	 * 
	 * @param taskId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Task getTaskData(int taskId, LocalDate startDate, LocalDate endDate) {
		Task taskData = new Task();
		taskData.setTaskId(taskId);
		taskData.setTaskDescription(TEST);
		taskData.setPriority(PRIORITY);
		taskData.setStatus(TEST);
		taskData.setStartDate(startDate);
		taskData.setEndDate(endDate);
		taskData.setProject(getProjectData(TEST_ID, startDate, endDate));
		taskData.setParentTask(getParentTask(TEST_ID));
		return taskData;
	}

	/**
	 * 
	 * @param projectId
	 * @return
	 */
	private ParentTask getParentTask(int parentTaskId) {
		ParentTask parentTaskData = new ParentTask();
		parentTaskData.setParentId(TEST_ID);
		parentTaskData.setParentTask(TEST);
		parentTaskData.setProject(getProjectData(TEST_ID, null, null));
		return parentTaskData;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	private User getUserData(int userId) {
		User userData = new User();
		userData.setUserId(TEST_ID);
		userData.setFirstName(TEST);
		userData.setLastName(TEST);
		userData.setEmployeeId(TEST_ID);
		userData.setProject(getProjectData(TEST_ID, null, null));
		userData.setTask(getTaskData(TEST_ID, null, null));
		return userData;
	}
}
