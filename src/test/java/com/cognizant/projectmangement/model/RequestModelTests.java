/**
 * 
 */
package com.cognizant.projectmangement.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cognizant.projectmangement.jpa.model.ParentTask;
import com.cognizant.projectmangement.jpa.model.Project;
import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.jpa.model.User;
import com.cognizant.projectmangement.open.pojo.util.PojoTestUtils;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RequestModelTests {
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUserDetails() {
		PojoTestUtils.validateAccessors(UserDetails.class);
	}

	@Test
	public void testProjectDetails() {
		PojoTestUtils.validateAccessors(ProjectDetails.class);
	}

	@Test
	public void testTaskDetails() {
		PojoTestUtils.validateAccessors(TaskDetails.class);
	}

	@Test
	public void testParentTaskDetails() {
		PojoTestUtils.validateAccessors(ParentTaskDetails.class);
	}

	@Test
	public void testUser() {
		PojoTestUtils.validateAccessors(User.class);
	}

	@Test
	public void testProject() {
		PojoTestUtils.validateAccessors(Project.class);
	}

	@Test
	public void testTask() {
		PojoTestUtils.validateAccessors(Task.class);
	}

	@Test
	public void testParentTask() {
		PojoTestUtils.validateAccessors(ParentTask.class);
	}
}
