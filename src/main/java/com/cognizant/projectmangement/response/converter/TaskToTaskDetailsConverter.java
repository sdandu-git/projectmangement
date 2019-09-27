/**
 * 
 */
package com.cognizant.projectmangement.response.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cognizant.projectmangement.jpa.model.Task;
import com.cognizant.projectmangement.model.TaskDetails;
import com.cognizant.projectmangement.util.ResponseConversionUtils;

/**
 * Task To TaskDetails Response Converter
 * 
 * @author CTS
 *
 */
@Component
public class TaskToTaskDetailsConverter implements Converter<Task, TaskDetails> {
	
	@Autowired
	ResponseConversionUtils responseConversionUtils;
	
	@Override
	public TaskDetails convert(Task taskData) {
		return responseConversionUtils.populateTaskDetailsFromTaskData(taskData, true, true, true);
	}
}