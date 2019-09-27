/**
 * 
 */
package com.cognizant.projectmangement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cognizant.projectmangement.request.converter.ParentTaskDetailsToParentTaskConverter;
import com.cognizant.projectmangement.request.converter.ProjectDetailsToProjectConverter;
import com.cognizant.projectmangement.request.converter.TaskDetailsToTaskConverter;
import com.cognizant.projectmangement.request.converter.UserDetailsToUserConverter;
import com.cognizant.projectmangement.response.converter.ParentTaskToParentTaskDetailsConverter;
import com.cognizant.projectmangement.response.converter.ProjectToProjectDetailsConverter;
import com.cognizant.projectmangement.response.converter.TaskToTaskDetailsConverter;
import com.cognizant.projectmangement.response.converter.UserToUserDetailsConverter;

/**
 * Registration of Spring Converter services
 * 
 * @author CTS
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) { 
		
		// Request Converters
		registry.addConverter(new UserDetailsToUserConverter());
		registry.addConverter(new ProjectDetailsToProjectConverter());
		registry.addConverter(new TaskDetailsToTaskConverter());
		registry.addConverter(new ParentTaskDetailsToParentTaskConverter());
		
		// Response Converters
		registry.addConverter(new UserToUserDetailsConverter());
		registry.addConverter(new ProjectToProjectDetailsConverter());
		registry.addConverter(new TaskToTaskDetailsConverter());
		registry.addConverter(new ParentTaskToParentTaskDetailsConverter());
	}
}
