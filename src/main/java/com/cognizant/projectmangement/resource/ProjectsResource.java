/**
 * 
 */
package com.cognizant.projectmangement.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.projectmangement.exception.ProjectCreationException;
import com.cognizant.projectmangement.exception.ProjectNotFoundException;
import com.cognizant.projectmangement.exception.UserNotFoundException;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.service.ProjectService;

import lombok.extern.slf4j.Slf4j;

/**
 * Projects module controller
 * 
 * @author CTS
 *
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/projects")
@Slf4j
public class ProjectsResource {

	@Autowired
	ProjectService projectService;

	/**
	 * Creates Project
	 * 
	 * @param projectDetailsRequest
	 * @return projectDetailsResponse
	 * @throws UserNotFoundException
	 * @throws ProjectCreationException
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjectDetails> createProject(@RequestBody ProjectDetails projectDetailsRequest)
			throws UserNotFoundException, ProjectCreationException {
		log.info("Create Project request received: " + projectDetailsRequest);
		return (ResponseEntity<ProjectDetails>) ResponseEntity.ok(projectService.createProject(projectDetailsRequest));
	}

	/**
	 * list all projects
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<ProjectDetails>> findAllProjects() {
		log.info("Find all Projects request received: ");
		return ResponseEntity.ok(projectService.findAllProjects());
	}

	/**
	 * finds project details by projectId
	 * 
	 * @param projectId
	 * @return projectDetails
	 * @throws ProjectNotFoundException
	 */
	@GetMapping(path = "/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjectDetails> findProjectByProjectId(@PathVariable Integer projectId)
			throws ProjectNotFoundException {
		log.info("Find by projectId " + projectId + " request received: ");
		return ResponseEntity.ok(projectService.findProjectByProjectId(projectId));
	}

	/**
	 * Updates Project Details
	 * 
	 * @param projectDetailsRequest
	 * @return projectDetailsResponse
	 * @throws UserNotFoundException
	 * @throws ProjectCreationException
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjectDetails> updateProjectDetails(@RequestBody ProjectDetails projectDetailsRequest)
			throws UserNotFoundException, ProjectCreationException {
		log.info("Update Project request received: " + projectDetailsRequest);
		return (ResponseEntity<ProjectDetails>) ResponseEntity.ok(projectService.createProject(projectDetailsRequest));
	}

	/**
	 * Deletes Project
	 * 
	 * @param projectRequest
	 * @return prjectId
	 * @throws ProjectNotFoundException
	 */
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> deleteProject(@RequestBody ProjectDetails projectRequest)
			throws ProjectNotFoundException {
		log.info("Delete Project request received: " + projectRequest);
		projectService.deleteProject(projectRequest);
		return ResponseEntity.ok(projectRequest.getProjectId());
	}

	/**
	 * Deletes Project by projectId
	 * 
	 * @param projectId
	 * @return projectId
	 * @throws ProjectNotFoundException
	 */
	@DeleteMapping(path = "/{projectId}")
	public ResponseEntity<Integer> deleteProjectByProjectId(@PathVariable Integer projectId)
			throws ProjectNotFoundException {
		log.info("Delete Project request received for projectId: " + projectId);
		projectService.deleteProjectById(projectId);
		return ResponseEntity.ok(projectId);
	}
}
