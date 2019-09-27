/**
 * 
 */
package com.cognizant.projectmangement.response.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cognizant.projectmangement.jpa.model.User;
import com.cognizant.projectmangement.model.UserDetails;
import com.cognizant.projectmangement.util.ResponseConversionUtils;

/**
 * User to UserDetails Response Converter
 * 
 * @author CTS
 *
 */
@Component
public class UserToUserDetailsConverter implements Converter<User, UserDetails> {

	@Autowired
	ResponseConversionUtils responseConversionUtils;

	@Override
	public UserDetails convert(User userData) {
		return responseConversionUtils.populateUserDetailsFromUserData(userData, true, true);
	}
}
