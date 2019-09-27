/**
 * 
 */
package com.cognizant.projectmangement.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * Date Utils for Project and Tasks Modules
 * 
 * @author CTS
 *
 */
@Component
public class DateUtils {

	/**
	 * returns Calendar Object
	 * 
	 * @param todaysDate
	 * @return
	 */
	public Calendar getCalendar(Date todaysDate, int plusDays) {
		Calendar calendarObj = Calendar.getInstance();
		calendarObj.setTime(todaysDate);
		calendarObj.add(Calendar.DATE, plusDays);
		calendarObj.clear(Calendar.DAY_OF_MONTH);
		calendarObj.clear(Calendar.HOUR_OF_DAY);
		calendarObj.clear(Calendar.AM_PM);
		calendarObj.clear(Calendar.MINUTE);
		calendarObj.clear(Calendar.SECOND);
		calendarObj.clear(Calendar.MILLISECOND);
		return calendarObj;
	}

	/**
	 * Returns tomorrow's date
	 * 
	 * @param todaysDate
	 * @return
	 * @throws ParseException
	 */
	public Date getFutureDate(Date todaysDate, int plusDays) {
		Calendar calendarObj = getCalendar(todaysDate, plusDays);
		return getFormattedDate(calendarObj.getTime());
	}

	/**
	 * returns todays date
	 * 
	 * @return todaysDate without timestamp
	 * @throws ParseException
	 */
	public Date getTodaysDate() {
		Date todaysDate = new Date();
		Calendar calendarObj = getCalendar(todaysDate, 0);
		return getFormattedDate(calendarObj.getTime());
	}

	/**
	 * Returns formatted date
	 * 
	 * @param dateInput
	 * @return
	 * @throws ParseException
	 */
	public Date getFormattedDate(Date dateInput) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.parse(dateFormat.format(dateInput));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateInput;
	}

	/**
	 * Returns date without Timestamp attributes
	 * 
	 * @param dateWithTime
	 * @return
	 * @throws ParseException
	 */
	public Date getDateWithoutTimestamp(Date dateWithTime) {
		Calendar calendarObj = getCalendar(dateWithTime, 0);
		return getFormattedDate(calendarObj.getTime());
	}
}
