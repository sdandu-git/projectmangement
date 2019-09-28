/**
 * 
 */
package com.cognizant.projectmangement.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

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

import com.cognizant.projectmangement.exception.ProjectCreationException;
import com.cognizant.projectmangement.exception.ProjectNotFoundException;
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
import com.cognizant.projectmangement.model.TaskDetails;
import com.cognizant.projectmangement.model.UserDetails;
import com.cognizant.projectmangement.request.converter.ProjectDetailsToProjectConverter;
import com.cognizant.projectmangement.response.converter.ProjectToProjectDetailsConverter;
import com.cognizant.projectmangement.response.converter.UserToUserDetailsConverter;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectServiceImplTest {

	@InjectMocks
	ProjectServiceImpl projectServiceImpl;

	@Mock
	ProjectRepository projectRepository;

	@Mock
	ProjectDetailsToProjectConverter projectRequestConverter;

	@Mock
	ProjectToProjectDetailsConverter projectResponseConverter;

	@Mock
	UserToUserDetailsConverter userToUserDetailsConverter;

	@Mock
	TaskRepository taskRepository;

	@Mock
	ParentTaskRepository parentTaskRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	Project mockProjectData;

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
		ReflectionTestUtils.setField(projectServiceImpl, "projectRepository", projectRepository);
		ReflectionTestUtils.setField(projectServiceImpl, "projectRequestConverter", projectRequestConverter);
		ReflectionTestUtils.setField(projectServiceImpl, "projectResponseConverter", projectResponseConverter);
		ReflectionTestUtils.setField(projectServiceImpl, "userToUserDetailsConverter", userToUserDetailsConverter);
		ReflectionTestUtils.setField(projectServiceImpl, "taskRepository", taskRepository);
		ReflectionTestUtils.setField(projectServiceImpl, "parentTaskRepository", parentTaskRepository);
		ReflectionTestUtils.setField(projectServiceImpl, "userRepository", userRepository);
	}

	@Test
	public void testCreateProjectScenarioOne() throws UserNotFoundException, ProjectCreationException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		when(projectRequestConverter.convert(projectDetailsRequest)).thenReturn(mockProjectData);
		when(projectRepository.save(mockProjectData)).thenReturn(mockProjectData);
		when(projectResponseConverter.convert(mockProjectData)).thenReturn(projectDetailsRequest);
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockUserData));
		when(userRepository.save(mockUserData)).thenReturn(mockUserData);
		when(userToUserDetailsConverter.convert(mockUserData)).thenReturn(mockUserDetails);
		ProjectDetails projectDetailsResponse = projectServiceImpl.createProject(projectDetailsRequest);
		assertEquals(projectDetailsRequest.getProjectId(), projectDetailsResponse.getProjectId());
	}

	@Test(expected = UserNotFoundException.class)
	public void testCreateProjectScenarioTwo() throws UserNotFoundException, ProjectCreationException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		when(projectRequestConverter.convert(projectDetailsRequest)).thenReturn(mockProjectData);
		when(projectRepository.save(mockProjectData)).thenReturn(mockProjectData);
		when(projectResponseConverter.convert(mockProjectData)).thenReturn(projectDetailsRequest);
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.<User>empty());
		projectServiceImpl.createProject(projectDetailsRequest);
	}

	@Test
	public void testCreateProjectScenarioThree() throws UserNotFoundException, ProjectCreationException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, null, null);
		projectDetailsRequest.setUserDetails(null);
		when(projectRequestConverter.convert(projectDetailsRequest)).thenReturn(mockProjectData);
		when(projectRepository.save(mockProjectData)).thenReturn(mockProjectData);
		when(projectResponseConverter.convert(mockProjectData)).thenReturn(projectDetailsRequest);
		ProjectDetails projectDetailsResponse = projectServiceImpl.createProject(projectDetailsRequest);
		assertNull(projectDetailsResponse.getUserDetails());
	}

	@Test
	public void testCreateProjectScenarioFour() throws UserNotFoundException, ProjectCreationException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, null, LocalDate.now().plusDays(1));
		projectDetailsRequest.setUserDetails(null);
		when(projectRequestConverter.convert(projectDetailsRequest)).thenReturn(mockProjectData);
		when(projectRepository.save(mockProjectData)).thenReturn(mockProjectData);
		when(projectResponseConverter.convert(mockProjectData)).thenReturn(projectDetailsRequest);
		ProjectDetails projectDetailsResponse = projectServiceImpl.createProject(projectDetailsRequest);
		assertNull(projectDetailsResponse.getUserDetails());
	}

	@Test(expected = ProjectCreationException.class)
	public void testCreateProjectScenarioFive() throws UserNotFoundException, ProjectCreationException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now());
		projectServiceImpl.createProject(projectDetailsRequest);
	}

	@Test(expected = ProjectCreationException.class)
	public void testCreateProjectScenarioSix() throws UserNotFoundException, ProjectCreationException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, LocalDate.now(), null);
		projectServiceImpl.createProject(projectDetailsRequest);
	}

	@Test(expected = ProjectCreationException.class)
	public void testCreateProjectScenarioSeven() throws UserNotFoundException, ProjectCreationException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, null, null);
		projectDetailsRequest.setProjectDescription(null);
		projectServiceImpl.createProject(projectDetailsRequest);
	}

	@Test
	public void testUpdateProject() throws ProjectNotFoundException, UserNotFoundException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		when(projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockProjectData));
		when(projectResponseConverter.convert(mockProjectData)).thenReturn(projectDetailsRequest);
		when(projectRequestConverter.convert(projectDetailsRequest)).thenReturn(mockProjectData);
		when(projectRepository.save(mockProjectData)).thenReturn(mockProjectData);
		when(projectResponseConverter.convert(mockProjectData)).thenReturn(projectDetailsRequest);
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockUserData));
		when(userRepository.save(mockUserData)).thenReturn(mockUserData);
		when(userToUserDetailsConverter.convert(mockUserData)).thenReturn(mockUserDetails);
		ProjectDetails projectDetailsResponse = projectServiceImpl.updateProjectDetails(projectDetailsRequest);
		assertEquals(projectDetailsRequest.getProjectId(), projectDetailsResponse.getProjectId());
	}

	@Test(expected = ProjectNotFoundException.class)
	public void testFindProjectByProjectId() throws ProjectNotFoundException {
		when(projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.<Project>empty());
		projectServiceImpl.findProjectByProjectId(TEST_ID);
	}

	@Test
	public void testFindAllProjectsScenarioOne() {
		List<Project> projectList = new ArrayList<Project>();
		projectList.add(new Project());
		when(projectRepository.findAll()).thenReturn(projectList);
		when(projectResponseConverter.convert(Mockito.any(Project.class)))
				.thenReturn(getProjectDetails(TEST_ID, null, null));
		List<ProjectDetails> projectDetailsList = projectServiceImpl.findAllProjects();
		assertEquals(projectDetailsList.size(), 1);
	}

	@Test
	public void testFindAllProjectsScenarioTwo() {
		List<Project> projectList = new ArrayList<Project>();
		when(projectRepository.findAll()).thenReturn(projectList);
		List<ProjectDetails> projectDetailsList = projectServiceImpl.findAllProjects();
		assertEquals(projectDetailsList.size(), 0);
	}

	@Test
	public void testDeleteProject() throws ProjectNotFoundException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		when(projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockProjectData));
		when(projectResponseConverter.convert(mockProjectData)).thenReturn(projectDetailsRequest);
		when(userRepository.findAll()).thenReturn(getUserList());
		when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUserData);
		when(taskRepository.findAll()).thenReturn(getTaskList());
		doNothing().when(taskRepository).delete(Mockito.any(Task.class));
		when(parentTaskRepository.findAll()).thenReturn(getParentTaskList());
		doNothing().when(parentTaskRepository).delete(Mockito.any(ParentTask.class));
		doNothing().when(projectRepository).deleteById(Mockito.anyInt());
		projectServiceImpl.deleteProject(projectDetailsRequest);
	} 
	
	
	@Test
	public void testDeleteProjectById() throws ProjectNotFoundException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		when(projectRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockProjectData));
		when(projectResponseConverter.convert(mockProjectData)).thenReturn(projectDetailsRequest);
		when(userRepository.findAll()).thenReturn(getUserList());
		when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUserData);
		when(taskRepository.findAll()).thenReturn(getTaskList());
		doNothing().when(taskRepository).delete(Mockito.any(Task.class));
		when(parentTaskRepository.findAll()).thenReturn(getParentTaskList());
		doNothing().when(parentTaskRepository).delete(Mockito.any(ParentTask.class));
		doNothing().when(projectRepository).deleteById(Mockito.anyInt());
		projectServiceImpl.deleteProjectById(TEST_ID);
	}

	/**
	 * ParentTask List
	 * 
	 * @return
	 */
	private List<ParentTask> getParentTaskList() {
		List<ParentTask> parentTaskList = new ArrayList<ParentTask>();
		parentTaskList.add(getParentTask(TEST_ID, TEST_ID));
		parentTaskList.add(getParentTask(0, 0));
		ParentTask parentTaskData = getParentTask(0, 0);
		parentTaskData.setProject(null);
		parentTaskList.add(parentTaskData);
		return parentTaskList;
	}

	/**
	 * Returns Parent Task
	 * 
	 * @param parentTaskId
	 * @param projectId
	 * @return
	 */
	private ParentTask getParentTask(int parentTaskId, int projectId) {
		ParentTask parentTaskData = new ParentTask();
		parentTaskData.setParentId(parentTaskId);
		parentTaskData.setParentTask(TEST);
		parentTaskData.setProject(getProjectData(projectId, null, null));
		return parentTaskData;
	}

	/**
	 * gets task list
	 * 
	 * @return
	 */
	private List<Task> getTaskList() {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(getTaskData(TEST_ID, TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1)));
		taskList.add(getTaskData(0, 0, null, null));
		Task taskData = getTaskData(2, 0, null, null);
		taskData.setProject(null);
		taskList.add(taskData);
		return taskList;
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
		taskData.setProject(getProjectData(projectId, startDate, endDate));
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
		userList.add(getUserData(0, 0));
		User userData = getUserData(2, 0);
		userData.setProject(null);
		userList.add(userData);
		return userList;
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
		projectDetails.setUserDetails(getUserDetails(TEST_ID));
		projectDetails.setTaskList(addTaskList());
		return projectDetails;
	}

	/**
	 * 
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Project getProjectData(int projectId, LocalDate startDate, LocalDate endDate) {
		Project projectData = new Project();
		projectData.setPriority(PRIORITY);
		projectData.setProject(TEST);
		projectData.setProjectId(projectId);
		projectData.setStartDate(startDate);
		projectData.setEndDate(endDate);
		return projectData;
	}

	/**
	 * 
	 * @return
	 */
	private List<TaskDetails> addTaskList() {
		List<TaskDetails> taskDetailsList = new ArrayList<TaskDetails>();
		taskDetailsList.add(getTaskDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1)));
		return taskDetailsList;
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
	 * 
	 * @return
	 */
	private User getUserData(int userId, int projectId) {
		User userData = new User();
		userData.setUserId(userId);
		userData.setFirstName(TEST);
		userData.setLastName(TEST);
		userData.setEmployeeId(userId);
		userData.setProject(getProjectData(projectId, null, null));
		userData.setTask(null);
		return userData;
	}

	/**
	 * 
	 * @param taskId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private TaskDetails getTaskDetails(int taskId, LocalDate startDate, LocalDate endDate) {
		TaskDetails taskDetails = new TaskDetails();
		taskDetails.setPriority(PRIORITY);
		taskDetails.setTaskDescription(TEST);
		taskDetails.setTaskId(taskId);
		taskDetails.setStartDate(startDate);
		taskDetails.setEndDate(endDate);
		taskDetails.setParentTaskDetails(null);
		taskDetails.setProjectDetails(null);
		return taskDetails;
	}
}
