/**
 * 
 */
package com.cognizant.projectmangement.request.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cognizant.projectmangement.jpa.model.Project;
import com.cognizant.projectmangement.model.ProjectDetails;
import com.cognizant.projectmangement.util.RequestConversionUtils;

/**
 * ProjectDetails to Project Request Converter
 * 
 * @author CTS
 *
 */
@Component
public class ProjectDetailsToProjectConverter implements Converter<ProjectDetails, Project> {

	@Autowired
	RequestConversionUtils requestConversionUtils;

	@Override
	public Project convert(ProjectDetails projectDetails) {
		return requestConversionUtils.populateProjectDataFromProjectDetails(projectDetails);
	}
}
