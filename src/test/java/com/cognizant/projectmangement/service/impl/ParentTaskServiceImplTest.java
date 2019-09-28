
package com.cognizant.projectmangement.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

import com.cognizant.projectmangement.exception.ParentTaskCreationException;
import com.cognizant.projectmangement.exception.ParentTaskNotFoundException;
import com.cognizant.projectmangement.jpa.model.ParentTask;
import com.cognizant.projectmangement.jpa.model.Project;
import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.jpa.repository.ParentTaskRepository;
import com.cognizant.projectmangement.jpa.repository.TaskRepository;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.request.converter.ParentTaskDetailsToParentTaskConverter;
import com.cognizant.projectmangement.response.converter.ParentTaskToParentTaskDetailsConverter;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ParentTaskServiceImplTest {

	@InjectMocks
	ParentTaskServiceImpl parentTaskServiceImpl;

	@Mock
	ParentTaskRepository parentTaskRepository;

	@Mock
	ParentTaskDetailsToParentTaskConverter parentTaskRequestConverter;

	@Mock
	ParentTaskToParentTaskDetailsConverter parentTaskResponseConverter;

	@Mock
	TaskRepository taskRepository;

	@Mock
	ParentTask mockParentTaskData;

	private static final String TEST = "TEST";
	private static final int TEST_ID = 1;
	private static final int PRIORITY = 10;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(parentTaskServiceImpl, "parentTaskRepository", parentTaskRepository);
		ReflectionTestUtils.setField(parentTaskServiceImpl, "taskRepository", taskRepository);
		ReflectionTestUtils.setField(parentTaskServiceImpl, "parentTaskRequestConverter", parentTaskRequestConverter);
		ReflectionTestUtils.setField(parentTaskServiceImpl, "parentTaskResponseConverter", parentTaskResponseConverter);
	}

	@Test
	public void testCreateParentTask() throws ParentTaskCreationException {
		ParentTaskDetails parentTaskDetailsRequest = getParentTaskDetails(TEST_ID, null);
		when(parentTaskRequestConverter.convert(Mockito.any(ParentTaskDetails.class))).thenReturn(mockParentTaskData);
		when(parentTaskRepository.save(Mockito.any(ParentTask.class))).thenReturn(mockParentTaskData);
		when(parentTaskResponseConverter.convert(Mockito.any(ParentTask.class))).thenReturn(parentTaskDetailsRequest);
		ParentTaskDetails parentTaskDetailsResponse = parentTaskServiceImpl.createParentTask(parentTaskDetailsRequest);
		assertEquals(parentTaskDetailsResponse.getParentId(), parentTaskDetailsRequest.getParentId());
	}

	@Test(expected = ParentTaskCreationException.class)
	public void testCreateParentTaskException() throws ParentTaskCreationException {
		ParentTaskDetails parentTaskDetailsRequest = getParentTaskDetails(TEST_ID, null);
		parentTaskDetailsRequest.setParentTaskDescription(null);
		parentTaskServiceImpl.createParentTask(parentTaskDetailsRequest);
	}

	@Test
	public void testUpdateParentTask() throws ParentTaskCreationException, ParentTaskNotFoundException {
		ParentTaskDetails parentTaskDetailsRequest = getParentTaskDetails(TEST_ID, null);
		when(parentTaskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockParentTaskData));
		when(parentTaskRequestConverter.convert(Mockito.any(ParentTaskDetails.class))).thenReturn(mockParentTaskData);
		when(parentTaskRepository.save(Mockito.any(ParentTask.class))).thenReturn(mockParentTaskData);
		when(parentTaskResponseConverter.convert(Mockito.any(ParentTask.class))).thenReturn(parentTaskDetailsRequest);
		ParentTaskDetails parentTaskDetailsResponse = parentTaskServiceImpl.updateParentTask(parentTaskDetailsRequest);
		assertEquals(parentTaskDetailsResponse.getParentId(), parentTaskDetailsRequest.getParentId());
	}

	@Test(expected = ParentTaskNotFoundException.class)
	public void testFindByParentTaskIdException() throws ParentTaskNotFoundException {
		when(parentTaskRepository.findById(Mockito.anyInt())).thenReturn(Optional.<ParentTask>empty());
		parentTaskServiceImpl.findParentTaskDetailsById(TEST_ID);
	}

	@Test
	public void testFindAllParentTasksWithData() {
		ParentTaskDetails parentTaskDetailsRequest = getParentTaskDetails(TEST_ID, null);
		when(parentTaskRepository.findAll()).thenReturn(getParentTaskList());
		when(parentTaskResponseConverter.convert(Mockito.any(ParentTask.class))).thenReturn(parentTaskDetailsRequest);
		assertEquals(parentTaskServiceImpl.findAllParentTaskDetails().size(), 3);
	}

	@Test
	public void testFindAllParentTasksWithoutData() {
		when(parentTaskRepository.findAll()).thenReturn(new ArrayList<ParentTask>());
		assertTrue(parentTaskServiceImpl.findAllParentTaskDetails().size() == 0);
	}

	@Test
	public void testDeleteParentTaskDetails() throws ParentTaskNotFoundException {
		ParentTaskDetails parentTaskDetailsRequest = getParentTaskDetails(TEST_ID, null);
		when(parentTaskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockParentTaskData));
		when(parentTaskRequestConverter.convert(Mockito.any(ParentTaskDetails.class))).thenReturn(mockParentTaskData);
		when(taskRepository.findAll()).thenReturn(getTaskList());
		doNothing().when(taskRepository).delete(Mockito.any(Task.class));
		parentTaskServiceImpl.deleteParentTask(parentTaskDetailsRequest);
	}

	@Test
	public void testDeleteParentTaskDetailsById() throws ParentTaskNotFoundException {
		when(parentTaskRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockParentTaskData));
		when(parentTaskRequestConverter.convert(Mockito.any(ParentTaskDetails.class))).thenReturn(mockParentTaskData);
		when(taskRepository.findAll()).thenReturn(getTaskList());
		doNothing().when(taskRepository).delete(Mockito.any(Task.class));
		parentTaskServiceImpl.deleteParentTaskByTaskId(TEST_ID);
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
	 * gets task list
	 * 
	 * @return
	 */
	private List<Task> getTaskList() {
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(getTaskData(TEST_ID, TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1)));
		taskList.add(getTaskData(0, 0, null, null));
		Task taskData = getTaskData(2, 0, null, null);
		taskData.setParentTask(null);
		taskList.add(taskData);
		return taskList;
	}

	/**
	 * 
	 * @param taskId
	 * @param parentId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Task getTaskData(int taskId, int parentId, LocalDate startDate, LocalDate endDate) {
		Task taskData = new Task();
		taskData.setTaskId(taskId);
		taskData.setTaskDescription(TEST);
		taskData.setPriority(PRIORITY);
		taskData.setStatus(TEST);
		taskData.setStartDate(startDate);
		taskData.setEndDate(endDate);
		taskData.setProject(null);
		taskData.setParentTask(getParentTask(parentId));
		return taskData;
	}

	/**
	 * 
	 * @param parentTaskId
	 * @return
	 */
	private ParentTask getParentTask(int parentTaskId) {
		ParentTask parentTaskData = new ParentTask();
		parentTaskData.setParentId(parentTaskId);
		parentTaskData.setParentTask(TEST);
		parentTaskData.setProject(null);
		return parentTaskData;
	}
}
