/**
 * 
 */
package com.cognizant.projectmangement.config;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * LocalDate to SQL Date Converter as part of JPA Conversion
 * @author CTS
 *
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate localDate) {
		return (localDate == null) ? null : Date.valueOf(localDate);
	}

	@Override
	public LocalDate convertToEntityAttribute(Date sqlDate) {
		return (sqlDate == null) ? null : sqlDate.toLocalDate();
	}
}
