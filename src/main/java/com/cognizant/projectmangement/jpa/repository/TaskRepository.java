/**
 * 
 */
package com.cognizant.projectmangement.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.projectmangement.jpa.model.Task;

/**
 * Task JPA Repository for Project Management App
 *  @author CTS
 *
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
