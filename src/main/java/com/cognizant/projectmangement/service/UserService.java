/**
 * 
 */
package com.cognizant.projectmangement.service;

import java.util.List;

import com.cognizant.projectmangement.exception.UserCreationException;
import com.cognizant.projectmangement.exception.UserNotFoundException;
import com.cognizant.projectmangement.model.UserDetails;

/**
 * User Service Interface
 * 
 * @author CTS
 */
public interface UserService {
	/**
	 * Creates User
	 * 
	 * @param userDetails
	 * @return
	 * @throws UserCreationException
	 */
	UserDetails createUser(UserDetails userDetails) throws UserCreationException;

	/**
	 * finds User by userId
	 * 
	 * @param employeeId
	 * @return
	 * @throws UserNotFoundException
	 */
	UserDetails findUserByUserId(int userId) throws UserNotFoundException;

	/**
	 * Lists all users
	 * 
	 * @return
	 */
	List<UserDetails> findAllUsers();

	/**
	 * Updates User
	 * 
	 * @param userDetails
	 * @return
	 * @throws UserCreationException
	 * @throws UserNotFoundException
	 */
	UserDetails updateUser(UserDetails userDetails) throws UserCreationException, UserNotFoundException;

	/**
	 * Deletes User
	 * 
	 * @param userDetails
	 * @throws UserNotFoundException
	 */
	void deleteUser(UserDetails userDetails) throws UserNotFoundException;

	/**
	 * Deletes user by Id
	 * 
	 * @param userId
	 * @throws UserNotFoundException
	 */
	void deleteUserById(int userId) throws UserNotFoundException;
}
