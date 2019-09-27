/**
 * 
 */
package com.cognizant.projectmangement.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.projectmangement.jpa.model.Project;

/**
 * Project JPA Repository for Project Management App
 *  @author CTS
 *
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {

}
