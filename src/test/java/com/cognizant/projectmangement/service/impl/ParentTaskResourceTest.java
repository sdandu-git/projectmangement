/**
 * 
 */
package com.cognizant.projectmangement.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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

import com.cognizant.projectmangement.exception.ParentTaskCreationException;
import com.cognizant.projectmangement.exception.ParentTaskNotFoundException;
import com.cognizant.projectmangement.exception.TaskNotFoundException;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.resource.ParentTaskResource;
import com.cognizant.projectmangement.service.ParentTaskService;

/**
 * @authorCTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ParentTaskResourceTest {

	@InjectMocks
	ParentTaskResource parentTaskResource;

	@Mock
	ParentTaskService parentTaskService;

	private static final String TEST = "TEST";
	private static final int TEST_ID = 1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(parentTaskResource, "parentTaskService", parentTaskService);
	}

	@Test
	public void testCreateParentTask() throws ParentTaskCreationException {
		ParentTaskDetails parentTaskDetailsRequest = getParentTaskDetails(TEST_ID);
		when(parentTaskService.createParentTask(Mockito.any(ParentTaskDetails.class)))
				.thenReturn(getParentTaskDetails(TEST_ID));
		assertTrue(parentTaskResource.createParentTask(parentTaskDetailsRequest).getBody().getParentId() == TEST_ID);
	}

	@Test
	public void testUpdateParentTask() throws ParentTaskCreationException, ParentTaskNotFoundException {
		ParentTaskDetails parentTaskDetailsRequest = getParentTaskDetails(TEST_ID);
		when(parentTaskService.updateParentTask(Mockito.any(ParentTaskDetails.class)))
				.thenReturn(getParentTaskDetails(TEST_ID));
		assertTrue(parentTaskResource.updateParentTaskDetails(parentTaskDetailsRequest).getBody()
				.getParentId() == TEST_ID);
	}

	@Test
	public void testFindParentTask() throws ParentTaskNotFoundException {
		ParentTaskDetails parentTaskDetailsResponse = getParentTaskDetails(TEST_ID);
		when(parentTaskService.findParentTaskDetailsById(Mockito.anyInt())).thenReturn(parentTaskDetailsResponse);
		assertTrue(parentTaskResource.findParentTaskByTaskId(TEST_ID).getBody().getParentId() == TEST_ID);
	}

	@Test
	public void testFindAllParentTasks() {
		when(parentTaskService.findAllParentTaskDetails()).thenReturn(new ArrayList<ParentTaskDetails>());
		assertTrue(parentTaskResource.findAllParentTasks().getBody().size() == 0);
	}

	@Test
	public void testDeleteParentTaskByBody() throws TaskNotFoundException, ParentTaskNotFoundException {
		ParentTaskDetails parentTaskDetailsRequest = getParentTaskDetails(TEST_ID);
		doNothing().when(parentTaskService).deleteParentTask(Mockito.any(ParentTaskDetails.class));
		assertTrue(parentTaskResource.deleteParentTask(parentTaskDetailsRequest).getBody() == TEST_ID);
	}

	@Test
	public void testDeleteParentTaskById() throws TaskNotFoundException, ParentTaskNotFoundException {
		doNothing().when(parentTaskService).deleteParentTaskByTaskId(Mockito.anyInt());
		assertTrue(parentTaskResource.deleteParentTaskByTaskId(TEST_ID).getBody() == TEST_ID);
	}

	/**
	 * Returns parentTaskDetails
	 * 
	 * @param parentTaskId
	 * @return
	 */
	private ParentTaskDetails getParentTaskDetails(int parentTaskId) {
		ParentTaskDetails parentTaskDetails = new ParentTaskDetails();
		parentTaskDetails.setParentId(parentTaskId);
		parentTaskDetails.setParentTaskDescription(TEST);
		parentTaskDetails.setProjectDetails(null);
		return parentTaskDetails;
	}
}
