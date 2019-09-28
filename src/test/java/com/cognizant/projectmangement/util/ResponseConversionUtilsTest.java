/**
 * 
 */
package com.cognizant.projectmangement.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.cognizant.projectmangement.jpa.repository.TaskRepository;
import com.cognizant.projectmangement.jpa.repository.UserRepository;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.model.TaskDetails;
import com.cognizant.projectmangement.model.UserDetails;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ResponseConversionUtilsTest {

	@InjectMocks
	ResponseConversionUtils responseConversionUtils;

	@Mock
	UserRepository userRepository;

	@Mock
	TaskRepository taskRepository;

	private static final String TEST = "TEST";
	private static final int PRIORITY = 10;
	private static final int TEST_ID = 1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(responseConversionUtils, "userRepository", userRepository);
		ReflectionTestUtils.setField(responseConversionUtils, "taskRepository", taskRepository);
	}

	@Test
	public void testPopulateProjectDetailsFromProjectDataScenarioOne() {
		Project projectData = getProjectData(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		ProjectDetails projectDetails = responseConversionUtils.populateProjectDetailsFromProjectData(projectData,
				false, false);
		assertEquals(projectDetails.getProjectId(), projectData.getProjectId());
	}

	@Test
	public void testPopulateProjectDetailsFromProjectDataScenarioTwo() {
		Project projectData = getProjectData(0, null, null);
		ProjectDetails projectDetails = responseConversionUtils.populateProjectDetailsFromProjectData(projectData,
				false, false);
		assertEquals(projectDetails.getProjectId(), projectData.getProjectId());
	}

	@Test
	public void testProjectUserAndTaskData() {
		Project projectData = getProjectData(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		when(taskRepository.findAll()).thenReturn(getTaskList());
		when(userRepository.findAll()).thenReturn(getUserList());
		ProjectDetails projectDetails = responseConversionUtils.populateProjectDetailsFromProjectData(projectData, true,
				true);
		assertEquals(projectDetails.getProjectId(), projectData.getProjectId());
	}

	@Test
	public void testUserDataScenarioOne() {
		User userData = getUserData(TEST_ID);
		UserDetails userDetails = responseConversionUtils.populateUserDetailsFromUserData(userData, true, true);
		assertEquals(userDetails.getUserId(), userData.getUserId());
	}

	@Test
	public void testUserDataScenarioTwo() {
		User userData = getUserData(TEST_ID);
		userData.setProject(null);
		userData.setTask(null);
		UserDetails userDetails = responseConversionUtils.populateUserDetailsFromUserData(userData, false, false);
		assertEquals(userDetails.getUserId(), userData.getUserId());
	}

	@Test
	public void testTaskDataScenarioOne() {
		Task taskData = getTaskData(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		TaskDetails taskDetails = responseConversionUtils.populateTaskDetailsFromTaskData(taskData, true, true, true);
		assertEquals(taskDetails.getTaskId(), taskData.getTaskId());
	} 
	
	@Test
	public void testTaskDataScenarioTwo() {
		Task taskData = getTaskData(0, null, null);
		taskData.setProject(null);
		taskData.setParentTask(null);
		TaskDetails taskDetails = responseConversionUtils.populateTaskDetailsFromTaskData(taskData, false, false, false);
		assertEquals(taskDetails.getTaskId(), taskData.getTaskId());
	}
	
	@Test
	public void testParentTaskDetailsScenarioOne() {
		ParentTask parentTaskData = getParentTask(TEST_ID);
		ParentTaskDetails parentTaskDetails = responseConversionUtils.populateParentTaskDetailsFromParentTaskData(parentTaskData, true);
		assertEquals(parentTaskDetails.getParentId(), parentTaskData.getParentId());
	} 
	
	@Test
	public void testParentTaskDetailsScenarioTwo() {
		ParentTask parentTaskData = getParentTask(TEST_ID);
		parentTaskData.setProject(null);
		ParentTaskDetails parentTaskDetails = responseConversionUtils.populateParentTaskDetailsFromParentTaskData(parentTaskData, false);
		assertEquals(parentTaskDetails.getParentId(), parentTaskData.getParentId());
	}
	
	@Test
	public void testUserDetailsFromTaskDataScenarioOne() {
		when(userRepository.findAll()).thenReturn(getUserList());
		UserDetails userDetails = responseConversionUtils.populateUserDetailsFromTaskData(TEST_ID);
		assertEquals(userDetails.getUserId(), TEST_ID);
	} 
	
	@Test
	public void testUserDetailsFromTaskDataScenarioTwo() {
		when(userRepository.findAll()).thenReturn(getUserList());
		UserDetails userDetails = responseConversionUtils.populateUserDetailsFromTaskData(2);
		assertNull(userDetails);
	} 
	
	@Test
	public void testUserDetailsFromTaskDataScenarioThree() {
		when(userRepository.findAll()).thenReturn(getUserListWithOutTask());
		UserDetails userDetails = responseConversionUtils.populateUserDetailsFromTaskData(TEST_ID);
		assertNull(userDetails);
	} 
	
	@Test
	public void testTaskListByProjectIdScenarioOne() {
		when(taskRepository.findAll()).thenReturn(getTaskListWithOutProject());
		List<TaskDetails> taskDetailsList = responseConversionUtils.populateTaskListAssociatedToProject(2);
		assertNotNull(taskDetailsList.size());
	}
	
	@Test
	public void testUserDetailsFromProjectScenarioOne() {
		Mockito.when(userRepository.findAll()).thenReturn(getUserListWithOutTask());
		UserDetails userDetails = responseConversionUtils.populateUserDetailsFromProjectData(TEST_ID);
		assertNull(userDetails);
	} 
	
	@Test
	public void testUserDetailsFromProjectScenarioTwo() {
		Mockito.when(userRepository.findAll()).thenReturn(getUserListWithProjectSpecificData());
		UserDetails userDetails = responseConversionUtils.populateUserDetailsFromProjectData(TEST_ID);
		assertNull(userDetails);
	}

	/**
	 * 
	 * @return
	 */
	private Iterable<User> getUserListWithProjectSpecificData() {
		List<User> userList = new ArrayList<User>();
		User userData = getUserData(TEST_ID);
		userData.getProject().setProjectId(2);
		userList.add(userData);
		return userList;
	}

	/**
	 * 
	 * @return
	 */
	private List<Task> getTaskListWithOutProject() {
		List<Task> taskList = new ArrayList<Task>();
		Task taskData = getTaskData(2, null, null);
		taskList.add(taskData);
		return taskList;
	}

	/**
	 * 
	 * @return
	 */
	private Iterable<User> getUserListWithOutTask() {
		List<User> userList = new ArrayList<User>();
		User userData = getUserData(TEST_ID);
		userData.setTask(null);
		userData.setProject(null);
		userList.add(userData);
		return userList;
	}

	/**
	 * 
	 * @return
	 */
	private Iterable<User> getUserList() {
		List<User> userList = new ArrayList<User>();
		User userData = getUserData(0);
		userList.add(userData);
		userData.setProject(null);
		userList.add(userData);
		userData = getUserData(TEST_ID);
		userList.add(userData);
		return userList;
	}

	/**
	 * 
	 * @return
	 */
	private List<Task> getTaskList() {
		List<Task> taskList = new ArrayList<Task>();
		Task taskData = getTaskData(0, null, null);
		taskList.add(taskData);
		taskData.setProject(null);
		taskList.add(taskData);
		taskData = getTaskData(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		taskList.add(taskData);
		return taskList;
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
