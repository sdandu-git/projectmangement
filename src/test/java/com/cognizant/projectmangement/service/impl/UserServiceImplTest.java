/**
 * 
 */
package com.cognizant.projectmangement.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
import com.cognizant.projectmangement.jpa.model.User;
import com.cognizant.projectmangement.jpa.repository.UserRepository;
import com.cognizant.projectmangement.model.UserDetails;
import com.cognizant.projectmangement.request.converter.UserDetailsToUserConverter;
import com.cognizant.projectmangement.response.converter.UserToUserDetailsConverter;

/**
 * @author CTS
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	UserRepository userRepository;

	@Mock
	UserDetailsToUserConverter userRequestConverter;

	@Mock
	UserToUserDetailsConverter userResponseConverter;

	@Mock
	User mockUserData;

	private static final String TEST = "TEST";
	private static final int TEST_ID = 1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(userServiceImpl, "userRepository", userRepository);
		ReflectionTestUtils.setField(userServiceImpl, "userRequestConverter", userRequestConverter);
		ReflectionTestUtils.setField(userServiceImpl, "userResponseConverter", userResponseConverter);
	}

	@Test
	public void testCreateUser() throws UserCreationException {
		UserDetails userDetailsRequest = getUserDetails(TEST_ID);
		when(userRequestConverter.convert(userDetailsRequest)).thenReturn(mockUserData);
		when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUserData);
		when(userResponseConverter.convert(Mockito.any(User.class))).thenReturn(userDetailsRequest);
		UserDetails userDetailsResponse = userServiceImpl.createUser(userDetailsRequest);
		assertEquals(userDetailsRequest.getUserId(), userDetailsResponse.getUserId());
	}

	@Test(expected = UserCreationException.class)
	public void testCreateUserScenarioEmployeeIdZero() throws UserCreationException {
		UserDetails userDetailsRequest = getUserDetails(0);
		userServiceImpl.createUser(userDetailsRequest);
	}

	@Test(expected = UserCreationException.class)
	public void testCreateUserScenarioLastNameEmpty() throws UserCreationException {
		UserDetails userDetailsRequest = getUserDetails(0);
		userDetailsRequest.setLastName(StringUtils.EMPTY);
		userServiceImpl.createUser(userDetailsRequest);
	}

	@Test(expected = UserCreationException.class)
	public void testCreateUserScenarioFirstNameEmpty() throws UserCreationException {
		UserDetails userDetailsRequest = getUserDetails(0);
		userDetailsRequest.setFirstName(StringUtils.EMPTY);
		userServiceImpl.createUser(userDetailsRequest);
	}

	@Test
	public void testUpdateUser() throws UserCreationException, UserNotFoundException {
		UserDetails userDetailsRequest = getUserDetails(TEST_ID);
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockUserData));
		when(userRequestConverter.convert(userDetailsRequest)).thenReturn(mockUserData);
		when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUserData);
		when(userResponseConverter.convert(Mockito.any(User.class))).thenReturn(userDetailsRequest);
		UserDetails userDetailsResponse = userServiceImpl.updateUser(userDetailsRequest);
		assertEquals(userDetailsRequest.getUserId(), userDetailsResponse.getUserId());
	}

	@Test(expected = UserNotFoundException.class)
	public void testFindUserById() throws UserNotFoundException {
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.<User>empty());
		userServiceImpl.findUserByUserId(TEST_ID);
	}

	@Test
	public void testFindAllUsers() {
		when(userRepository.findAll()).thenReturn(getUserList());
		when(userResponseConverter.convert(Mockito.any(User.class))).thenReturn(getUserDetails(TEST_ID));
		assertEquals(userServiceImpl.findAllUsers().size(), 3);
	}

	@Test
	public void testFindAllUsersNoData() {
		when(userRepository.findAll()).thenReturn(new ArrayList<User>());
		assertEquals(userServiceImpl.findAllUsers().size(), 0);
	}

	@Test
	public void testDeleteUserByBody() throws UserNotFoundException {
		UserDetails userDetailsRequest = getUserDetails(TEST_ID);
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockUserData));
		when(userRequestConverter.convert(Mockito.any(UserDetails.class))).thenReturn(mockUserData);
		doNothing().when(userRepository).delete(mockUserData);
		userServiceImpl.deleteUser(userDetailsRequest);
	}

	@Test
	public void testDeleteUserById() throws UserNotFoundException {
		when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockUserData));
		when(userRequestConverter.convert(Mockito.any(UserDetails.class))).thenReturn(mockUserData);
		doNothing().when(userRepository).delete(mockUserData);
		userServiceImpl.deleteUserById(TEST_ID);
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
	 * @return
	 */
	private User getUserData(int userId, int projectId) {
		User userData = new User();
		userData.setUserId(userId);
		userData.setFirstName(TEST);
		userData.setLastName(TEST);
		userData.setEmployeeId(userId);
		userData.setProject(null);
		userData.setTask(null);
		return userData;
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
