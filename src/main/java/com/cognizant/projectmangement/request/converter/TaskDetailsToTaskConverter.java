/**
 * 
 */
package com.cognizant.projectmangement.request.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.model.TaskDetails;
import com.cognizant.projectmangement.util.RequestConversionUtils;

/**
 * TaskDetails to Task Request Converter
 * 
 * @author CTS
 *
 */
@Component
public class TaskDetailsToTaskConverter implements Converter<TaskDetails, Task> {

	@Autowired
	RequestConversionUtils requestConversionUtils;

	@Override
	public Task convert(TaskDetails taskDetails) {
		return requestConversionUtils.populateTaskDataFromTaskDetails(taskDetails, true, true);
	}
}
