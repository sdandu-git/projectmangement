package com.cognizant.projectmangement.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Task Model
 * 
 * @author CTS
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class TaskDetails {
	@JsonProperty("taskId")
	private int taskId;

	@JsonProperty("endDate")
	private LocalDate endDate;

	@JsonProperty("priority")
	private int priority;

	@JsonProperty("startDate")
	private LocalDate startDate;

	@JsonProperty("taskStatus")
	private String taskStatus;

	@JsonProperty("taskDescription")
	private String taskDescription;

	@JsonProperty("parentTaskDetails")
	private ParentTaskDetails parentTaskDetails;

	@JsonProperty("projectDetails")
	private ProjectDetails projectDetails;

	@JsonProperty("userDetails")
	private UserDetails userDetails;
}