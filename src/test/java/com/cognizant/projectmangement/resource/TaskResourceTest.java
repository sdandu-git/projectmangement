/**
 * 
 */
package com.cognizant.projectmangement.resource;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDate;
import java.util.ArrayList;

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
import com.cognizant.projectmangement.model.TaskDetails;
import com.cognizant.projectmangement.service.TaskService;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TaskResourceTest {

	@InjectMocks
	TaskResource taskResource;

	@Mock
	TaskService taskService;

	private static final String TEST = "TEST";
	private static final int PRIORITY = 10;
	private static final int TEST_ID = 1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(taskResource, "taskService", taskService);
	}

	@Test
	public void testCreateTask() throws UserNotFoundException, TaskCreationException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID);
		when(taskService.createTask(taskDetailsRequest)).thenReturn(taskDetailsRequest);
		TaskDetails taskDetailsResponse = taskResource.createTask(taskDetailsRequest).getBody();
		assertTrue(taskDetailsResponse.getTaskId() == TEST_ID);
	}

	@Test
	public void testUpdateTask() throws TaskCreationException, UserNotFoundException, TaskNotFoundException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID);
		when(taskService.updateTask(taskDetailsRequest)).thenReturn(taskDetailsRequest);
		TaskDetails taskDetailsResponse = taskResource.updateTaskDetails(taskDetailsRequest).getBody();
		assertTrue(taskDetailsResponse.getTaskId() == TEST_ID);
	}

	@Test
	public void testFindTask() throws TaskNotFoundException {
		TaskDetails taskDetailsResponse = getTaskDetails(TEST_ID);
		when(taskService.findTaskDetailsById(Mockito.anyInt())).thenReturn(taskDetailsResponse);
		assertTrue(taskResource.findTaskByTaskId(TEST_ID).getBody().getTaskId() == TEST_ID);
	}

	@Test
	public void testFindAllTasks() {
		when(taskService.findAllTaskDetails()).thenReturn(new ArrayList<TaskDetails>());
		assertTrue(taskResource.findAllTasks().getBody().size() == 0);
	}

	@Test
	public void testDeleteTaskByBody() throws TaskNotFoundException {
		TaskDetails taskDetailsRequest = getTaskDetails(TEST_ID);
		doNothing().when(taskService).deleteTask(Mockito.any(TaskDetails.class));
		assertTrue(taskResource.deleteTask(taskDetailsRequest).getBody() == TEST_ID);
	}

	@Test
	public void testDeleteTaskByTaskId() throws TaskNotFoundException {
		doNothing().when(taskService).deleteTaskByTaskId(Mockito.anyInt());
		assertTrue(taskResource.deleteTaskByTaskId(TEST_ID).getBody() == TEST_ID);
	}

	/**
	 * Returns taskDetails
	 * 
	 * @param taskId
	 * @return
	 */
	private TaskDetails getTaskDetails(int taskId) {
		TaskDetails taskDetails = new TaskDetails();
		taskDetails.setPriority(PRIORITY);
		taskDetails.setTaskDescription(TEST);
		taskDetails.setTaskId(taskId);
		taskDetails.setStartDate(LocalDate.now());
		taskDetails.setEndDate(LocalDate.now().plusDays(1));
		taskDetails.setParentTaskDetails(null);
		taskDetails.setProjectDetails(null);
		taskDetails.setUserDetails(null);
		return taskDetails;
	}
}
