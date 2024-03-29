package com.cognizant.projectmangement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Parent Task Model
 * 
 * @author CTS
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class ParentTaskDetails {
	@JsonProperty("parentId")
	private int parentId;

	@JsonProperty("parentTaskDescription")
	private String parentTaskDescription;

	@JsonProperty("projectDetails")
	private ProjectDetails projectDetails;
}