/**
 * 
 */
package com.cognizant.projectmangement.response.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cognizant.projectmangement.jpa.model.ParentTask;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.util.ResponseConversionUtils;

/**
 * ParentTask to ParentTaskDetails Response Converter
 * 
 * @author CTS
 *
 */
@Component
public class ParentTaskToParentTaskDetailsConverter implements Converter<ParentTask, ParentTaskDetails> {

	@Autowired
	ResponseConversionUtils responseConversionUtils;

	@Override
	public ParentTaskDetails convert(ParentTask parentTaskData) {
		return responseConversionUtils.populateParentTaskDetailsFromParentTaskData(parentTaskData, true);
	}
}
