/**
 * 
 */
package com.cognizant.projectmangement.request.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cognizant.projectmangement.jpa.model.ParentTask;
import com.cognizant.projectmangement.model.ParentTaskDetails;
import com.cognizant.projectmangement.util.RequestConversionUtils;

/**
 * ParentTaskDetails to ParentTask Request Converter
 * 
 * @author CTS
 *
 */
@Component
public class ParentTaskDetailsToParentTaskConverter implements Converter<ParentTaskDetails, ParentTask> {

	@Autowired
	RequestConversionUtils requestConversionUtils;

	@Override
	public ParentTask convert(ParentTaskDetails parentTaskDetails) {
		return requestConversionUtils.populateParentTaskDataFromParentTaskDetails(parentTaskDetails, true);
	}

}
