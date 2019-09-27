package com.cognizant.projectmangement.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project Model
 * 
 * @author CTS
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class ProjectDetails {

	@JsonProperty("projectId")
	private int projectId;

	@JsonProperty("endDate")
	private LocalDate endDate;

	@JsonProperty("priority")
	private int priority;

	@JsonProperty("projectDescription")
	private String projectDescription;

	@JsonProperty("startDate")
	private LocalDate startDate;

	@JsonProperty("userDetails")
	private UserDetails userDetails;

	@JsonProperty("taskList")
	private List<TaskDetails> taskList;
}