/**
 * 
 */
package com.cognizant.projectmangement.exception;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ExceptionTest {
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testExceptions() {
		new UserNotFoundException(101);
		new UserCreationException("firstName");
		new ProjectNotFoundException(102);
		new ProjectCreationException("projectDescription");
		new TaskNotFoundException(103);
		new TaskCreationException("taskDescription");
		new ParentTaskNotFoundException(104);
		new ParentTaskCreationException("parentTaskDescription");
	}
}
