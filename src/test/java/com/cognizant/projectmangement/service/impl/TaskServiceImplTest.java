/**
 * 
 */
package com.cognizant.projectmangement.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.cognizant.projectmangement.exception.TaskCreationException;
import com.cognizant.projectmangement.exception.TaskNotFoundException;
import com.cognizant.projectmangement.exception.UserNotFoundException;
import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.jpa.model.User;
import com.cognizant.projectmangement.jpa.repository.TaskRepository;
import com.cognizant.projectmangement.jpa.repository.UserRepository;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.model.TaskDetails;
import com.cognizant.projectmangement.model.UserDetails;
import com.cognizant.projectmangement.request.converter.TaskDetailsToTaskConverter;
import com.cognizant.projectmangement.response.converter.TaskToTaskDetailsConverter;
import com.cognizant.projectmangement.response.converter.UserToUserDetailsConverter;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskServiceImplTest {

	@InjectMocks
	TaskServiceImpl taskServiceImpl;

	@Mock
	TaskRepository taskRepository;

	@Mock
	TaskDetailsToTaskConverter taskRequestConverter;

	@Mock
	TaskToTaskDetailsConverter taskResponseConverter;

	@Mock
	UserToUserDetailsConverter userToUserDetailsConverter;

	@Mock
	UserRepository userRepository;

	@Mock
	Task mockTaskData;

	@Mock
	User mockUserData;

	@Mock
	UserDetails mockUserDetails;

	private static final String TEST = "TEST";
	private static final int PRIORITY = 10;
	private static final int TEST_ID = 1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(taskServiceImpl, "taskRepository", taskRepository);
		ReflectionTestUtils.setField(taskServiceImpl, "taskRequestConverter", taskRequestConverter);
		ReflectionTestUtils.setField(taskServiceImpl, "taskResponseConverter", taskResponseConverter);
		ReflectionTestUtils.setField(taskServiceImpl, "userToUserDetailsConverter", userToUserDetailsConverter);
		ReflectionTestUtils.setField(taskServiceImpl, "userRepository", userRepository);
	}

	@Test
	public void testCreateTaskScenarioOne() throws UserNotFoundException, TaskCreationException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID, TEST_ID, TEST_ID, TEST_ID, LocalDate.now(),
				LocalDate.now().plusDays(1));
		when(taskRequestConverter.convert(taskDetailsRequest)).thenReturn(mockTaskData);
		when(taskRepository.save(mockTaskData)).thenReturn(mockTaskData);
		when(taskResponseConverter.convert(mockTaskData)).thenReturn(taskDetailsRequest);
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockUserData));
		when(userRepository.save(mockUserData)).thenReturn(mockUserData);
		when(userToUserDetailsConverter.convert(mockUserData)).thenReturn(mockUserDetails);
		TaskDetails taskDetailsResponse = taskServiceImpl.createTask(taskDetailsRequest);
		assertEquals(taskDetailsRequest.getTaskId(), taskDetailsResponse.getTaskId());
	}

	@Test(expected = UserNotFoundException.class)
	public void testCreateTaskScenarioTwo() throws UserNotFoundException, TaskCreationException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID, TEST_ID, TEST_ID, TEST_ID, null, null);
		taskDetailsRequest.setTaskStatus("IN-PROGRESS");
		when(taskRequestConverter.convert(taskDetailsRequest)).thenReturn(mockTaskData);
		when(taskRepository.save(mockTaskData)).thenReturn(mockTaskData);
		when(taskResponseConverter.convert(mockTaskData)).thenReturn(taskDetailsRequest);
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.<User>empty());
		taskServiceImpl.createTask(taskDetailsRequest);
	}

	@Test
	public void testCreateTaskScenarioThree() throws UserNotFoundException, TaskCreationException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID, TEST_ID, TEST_ID, 0, null,
				LocalDate.now().plusDays(1));
		taskDetailsRequest.setUserDetails(null);
		when(taskRequestConverter.convert(taskDetailsRequest)).thenReturn(mockTaskData);
		when(taskRepository.save(mockTaskData)).thenReturn(mockTaskData);
		when(taskResponseConverter.convert(mockTaskData)).thenReturn(taskDetailsRequest);
		TaskDetails taskDetailsResponse = taskServiceImpl.createTask(taskDetailsRequest);
		assertEquals(taskDetailsRequest.getTaskId(), taskDetailsResponse.getTaskId());
	}

	@Test(expected = TaskCreationException.class)
	public void testCreateTaskScenarioFour() throws UserNotFoundException, TaskCreationException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID, TEST_ID, TEST_ID, 0, LocalDate.now(), LocalDate.now());
		taskServiceImpl.createTask(taskDetailsRequest);
	}

	@Test(expected = TaskCreationException.class)
	public void testCreateTaskScenarioFive() throws UserNotFoundException, TaskCreationException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID, TEST_ID, TEST_ID, 0, LocalDate.now(), null);
		taskServiceImpl.createTask(taskDetailsRequest);
	}

	@Test(expected = TaskCreationException.class)
	public void testCreateTaskScenarioSix() throws UserNotFoundException, TaskCreationException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID, TEST_ID, TEST_ID, 0, LocalDate.now(), null);
		taskDetailsRequest.setTaskDescription(null);
		taskServiceImpl.createTask(taskDetailsRequest);
	}

	@Test
	public void testUpdateTask() throws UserNotFoundException, TaskCreationException, TaskNotFoundException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID, TEST_ID, TEST_ID, TEST_ID, LocalDate.now(),
				LocalDate.now().plusDays(1));

		when(taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockTaskData));
		when(taskResponseConverter.convert(mockTaskData)).thenReturn(taskDetailsRequest);
		when(taskRequestConverter.convert(taskDetailsRequest)).thenReturn(mockTaskData);
		when(taskRepository.save(mockTaskData)).thenReturn(mockTaskData);
		when(taskResponseConverter.convert(mockTaskData)).thenReturn(taskDetailsRequest);
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockUserData));
		when(userRepository.save(mockUserData)).thenReturn(mockUserData);
		when(userToUserDetailsConverter.convert(mockUserData)).thenReturn(mockUserDetails);
		TaskDetails taskDetailsResponse = taskServiceImpl.updateTask(taskDetailsRequest);
		assertEquals(taskDetailsRequest.getTaskId(), taskDetailsResponse.getTaskId());
	}

	@Test(expected = TaskNotFoundException.class)
	public void testFindTaskByTaskId() throws TaskNotFoundException {
		when(taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.<Task>empty());
		taskServiceImpl.findTaskDetailsById(TEST_ID);
	}

	@Test
	public void testFindAllTasksScenarioOne() {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new Task());
		when(taskRepository.findAll()).thenReturn(taskList);
		when(taskResponseConverter.convert(Mockito.any(Task.class)))
				.thenReturn(getTaskDetails(TEST_ID, TEST_ID, TEST_ID, TEST_ID, null, null));
		List<TaskDetails> taskDetailsList = taskServiceImpl.findAllTaskDetails();
		assertEquals(taskDetailsList.size(), 1);
	}

	@Test
	public void testFindAllProjectsScenarioTwo() {
		List<Task> taskList = new ArrayList<Task>();
		when(taskRepository.findAll()).thenReturn(taskList);
		List<TaskDetails> taskDetailsList = taskServiceImpl.findAllTaskDetails();
		assertEquals(taskDetailsList.size(), 0);
	}

	@Test
	public void testDeleteTask() throws TaskNotFoundException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID, TEST_ID, TEST_ID, TEST_ID, LocalDate.now(),
				LocalDate.now().plusDays(1));
		when(taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockTaskData));
		when(taskResponseConverter.convert(mockTaskData)).thenReturn(taskDetailsRequest);
		when(userRepository.findAll()).thenReturn(getUserList());
		when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUserData);
		when(taskRequestConverter.convert(taskDetailsRequest)).thenReturn(mockTaskData);
		doNothing().when(taskRepository).delete(mockTaskData);
		taskServiceImpl.deleteTask(taskDetailsRequest);
	}

	@Test
	public void testDeleteTaskById() throws TaskNotFoundException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID, TEST_ID, TEST_ID, TEST_ID, LocalDate.now(),
				LocalDate.now().plusDays(1));
		when(taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockTaskData));
		when(taskResponseConverter.convert(mockTaskData)).thenReturn(taskDetailsRequest);
		when(userRepository.findAll()).thenReturn(getUserList());
		when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUserData);
		when(taskRequestConverter.convert(taskDetailsRequest)).thenReturn(mockTaskData);
		doNothing().when(taskRepository).delete(mockTaskData);
		taskServiceImpl.deleteTaskByTaskId(TEST_ID);
	}

	/**
	 * 
	 * @return
	 */
	private User getUserData(int userId, int projectId) {
		User userData = new User();
		userData.setUserId(userId);
		userData.setFirstName(TEST);
		userData.setLastName(TEST);
		userData.setEmployeeId(userId);
		userData.setProject(null);
		userData.setTask(getTaskData(TEST_ID, TEST_ID, null, null));
		return userData;
	}

	/**
	 * 
	 * @param taskId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Task getTaskData(int taskId, int projectId, LocalDate startDate, LocalDate endDate) {
		Task taskData = new Task();
		taskData.setTaskId(taskId);
		taskData.setTaskDescription(TEST);
		taskData.setPriority(PRIORITY);
		taskData.setStatus(TEST);
		taskData.setStartDate(startDate);
		taskData.setEndDate(endDate);
		taskData.setProject(null);
		taskData.setParentTask(null);
		return taskData;
	}

	/**
	 * 
	 * @return
	 */
	private Iterable<User> getUserList() {
		List<User> userList = new ArrayList<User>();
		userList.add(getUserData(TEST_ID, TEST_ID));
		User userDataObj = getUserData(0, 0);
		userDataObj.getTask().setTaskId(3);
		userList.add(userDataObj);
		User userData = getUserData(2, 0);
		userData.setTask(null);
		userList.add(userData);
		return userList;
	}

	/**
	 * Returns taskDetails
	 * 
	 * @param taskId
	 * @param parentTaskId
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private TaskDetails getTaskDetails(int taskId, int parentTaskId, int projectId, int userId, LocalDate startDate,
			LocalDate endDate) {
		TaskDetails taskDetails = new TaskDetails();
		taskDetails.setPriority(PRIORITY);
		taskDetails.setTaskDescription(TEST);
		taskDetails.setTaskId(taskId);
		taskDetails.setStartDate(startDate);
		taskDetails.setEndDate(endDate);
		taskDetails.setParentTaskDetails(getParentTaskDetails(parentTaskId, null));
		taskDetails.setProjectDetails(getProjectDetails(projectId, startDate, endDate));
		taskDetails.setUserDetails(getUserDetails(userId));
		return taskDetails;
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
		userDetails.setProjectDetails(null);
		userDetails.setTaskDetails(null);
		return userDetails;
	}

	/**
	 * returns parentTask details
	 * 
	 * @param parentTaskId
	 * @param projectDetails
	 * @return
	 */
	private ParentTaskDetails getParentTaskDetails(int parentTaskId, ProjectDetails projectDetails) {
		ParentTaskDetails parentTaskDetails = new ParentTaskDetails();
		parentTaskDetails.setParentId(parentTaskId);
		parentTaskDetails.setParentTaskDescription(TEST);
		parentTaskDetails.setProjectDetails(projectDetails);
		return parentTaskDetails;
	}

	/**
	 * 
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private ProjectDetails getProjectDetails(int projectId, LocalDate startDate, LocalDate endDate) {
		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setPriority(PRIORITY);
		projectDetails.setProjectDescription(TEST);
		projectDetails.setProjectId(projectId);
		projectDetails.setStartDate(startDate);
		projectDetails.setEndDate(endDate);
		projectDetails.setUserDetails(null);
		projectDetails.setTaskList(null);
		return projectDetails;
	}
}
