/**
 * 
 */
package com.cognizant.projectmangement.resource;

import static org.junit.Assert.assertEquals;
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

import com.cognizant.projectmangement.exception.ProjectCreationException;
import com.cognizant.projectmangement.exception.ProjectNotFoundException;
import com.cognizant.projectmangement.exception.UserNotFoundException;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.service.ProjectService;

/**
 * @author Narasimha Kishore Kaipa
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectsResourceTest {

	@InjectMocks
	ProjectsResource projectsResource;

	@Mock
	ProjectService projectService;

	private static final String TEST = "TEST";
	private static final int PRIORITY = 10;
	private static final int TEST_ID = 1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(projectsResource, "projectService", projectService);
	}

	@Test
	public void testCreateProject() throws UserNotFoundException, ProjectCreationException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		when(projectService.createProject(projectDetailsRequest)).thenReturn(projectDetailsRequest);
		assertEquals(projectsResource.createProject(projectDetailsRequest).getBody().getProjectId(), TEST_ID);
	}

	@Test
	public void testUpdateProject() throws UserNotFoundException, ProjectCreationException, ProjectNotFoundException {
		ProjectDetails projectDetailsRequest = getProjectDetails(TEST_ID, LocalDate.now(), LocalDate.now().plusDays(1));
		when(projectService.updateProjectDetails(projectDetailsRequest)).thenReturn(projectDetailsRequest);
		assertEquals(projectsResource.updateProjectDetails(projectDetailsRequest).getBody().getProjectId(), TEST_ID);
	}

	@Test
	public void testFindAProject() throws ProjectNotFoundException {
		when(projectService.findProjectByProjectId(Mockito.anyInt()))
				.thenReturn(getProjectDetails(TEST_ID, null, null));
		assertEquals(projectsResource.findProjectByProjectId(TEST_ID).getBody().getProjectId(), TEST_ID);
	}

	@Test
	public void testFindAllProjects() {
		when(projectService.findAllProjects()).thenReturn(new ArrayList<ProjectDetails>());
		assertEquals(projectsResource.findAllProjects().getBody().size(), 0);
	}

	@Test
	public void testDeleteProject() throws ProjectNotFoundException {
		doNothing().when(projectService).deleteProject(Mockito.any(ProjectDetails.class));
		projectsResource.deleteProject(getProjectDetails(TEST_ID, null, null));
	}

	@Test
	public void testDeleteProjectId() throws ProjectNotFoundException {
		doNothing().when(projectService).deleteProject(Mockito.any(ProjectDetails.class));
		projectsResource.deleteProjectByProjectId(TEST_ID);
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
