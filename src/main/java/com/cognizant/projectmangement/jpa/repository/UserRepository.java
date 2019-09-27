
package com.cognizant.projectmangement.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.projectmangement.jpa.model.User;

/**
 * User JPA Repository for Project Management App
 * @author CTS
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
