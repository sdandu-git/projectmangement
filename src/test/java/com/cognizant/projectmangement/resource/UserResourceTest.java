/**
 * 
 */
package com.cognizant.projectmangement.resource;

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

import com.cognizant.projectmangement.exception.UserCreationException;
import com.cognizant.projectmangement.exception.UserNotFoundException;
import com.cognizant.projectmangement.model.UserDetails;
import com.cognizant.projectmangement.service.UserService;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserResourceTest {

	@InjectMocks
	UserResource userResource;

	@Mock
	UserService userService;

	private static final String TEST = "TEST";
	private static final int TEST_ID = 1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(userResource, "userService", userService);
	}

	@Test
	public void testCreateUser() throws UserCreationException {
		UserDetails userDetailsRequest = getUserDetails(0);
		when(userService.createUser(Mockito.any(UserDetails.class))).thenReturn(getUserDetails(TEST_ID));
		UserDetails userDetailsResponse = userResource.createUser(userDetailsRequest).getBody();
		assertTrue(userDetailsResponse.getUserId() == TEST_ID);
	}

	@Test
	public void testUpdateUser() throws UserCreationException, UserNotFoundException {
		UserDetails userDetailsRequest = getUserDetails(0);
		when(userService.updateUser(Mockito.any(UserDetails.class))).thenReturn(getUserDetails(TEST_ID));
		UserDetails userDetailsResponse = userResource.updateUser(userDetailsRequest).getBody();
		assertTrue(userDetailsResponse.getUserId() == TEST_ID);
	}

	@Test
	public void testFindUser() throws UserCreationException, UserNotFoundException {
		when(userService.findUserByUserId(Mockito.anyInt())).thenReturn(getUserDetails(TEST_ID));
		assertTrue(userResource.findUserByUserId(TEST_ID).getBody().getUserId() == TEST_ID);
	}

	@Test
	public void testFindAllUsers() throws UserCreationException, UserNotFoundException {
		when(userService.findAllUsers()).thenReturn(new ArrayList<UserDetails>());
		assertTrue(userResource.findAllUsers().getBody().size() == 0);
	}

	@Test
	public void testDeleteUserByBody() throws UserNotFoundException {
		doNothing().when(userService).deleteUser(Mockito.any(UserDetails.class));
		assertTrue(userResource.deleteUser(getUserDetails(TEST_ID)).getBody() == TEST_ID);
	}

	@Test
	public void testDeleteUserById() throws UserNotFoundException {
		doNothing().when(userService).deleteUserById(Mockito.anyInt());
		assertTrue(userResource.deleteUserByUserId(TEST_ID).getBody() == TEST_ID);
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
}
