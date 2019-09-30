/**
 * 
 */
package com.cognizant.projectmangement.request.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cognizant.projectmangement.jpa.model.User;
import com.cognizant.projectmangement.model.UserDetails;
import com.cognizant.projectmangement.util.RequestConversionUtils;

/**
 * UserDetails to User Request Converter
 * 
 * @author CTS
 *
 */
@Component
public class UserDetailsToUserConverter implements Converter<UserDetails, User> {

	@Autowired
	RequestConversionUtils requestConversionUtils;

	@Override
	public User convert(UserDetails userDetails) {
		User userData = new User();
		if (userDetails.getUserId() > 0) {
			userData.setUserId(userDetails.getUserId());
		}
		userData.setFirstName(userDetails.getFirstName());
		userData.setLastName(userDetails.getLastName());
		userData.setEmployeeId(userDetails.getEmployeeId());
		if ((null != userDetails.getProjectDetails()) && (userDetails.getProjectDetails().getProjectId() > 0)) {
			userData.setProject(
					requestConversionUtils.populateProjectDataFromProjectDetails(userDetails.getProjectDetails()));
		}
		if ((null != userDetails.getTaskDetails()) && (userDetails.getTaskDetails().getTaskId() > 0)) {
			userData.setTask(
					requestConversionUtils.populateTaskDataFromTaskDetails(userDetails.getTaskDetails(), false, false));
		}
		return userData;
	}
}