/**
 * 
 */
package com.cognizant.projectmangement.response.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cognizant.projectmangement.jpa.model.Project;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.util.ResponseConversionUtils;

/**
 * Project to ProjectDetails Response Converter
 * 
 * @author CTS
 *
 */
@Component
public class ProjectToProjectDetailsConverter implements Converter<Project, ProjectDetails> {
	@Autowired
	ResponseConversionUtils responseConversionUtils;

	@Override
	public ProjectDetails convert(Project projectData) {
		return responseConversionUtils.populateProjectDetailsFromProjectData(projectData, true, true);
	}
}
