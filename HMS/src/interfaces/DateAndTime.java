package interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/**
 * Interface that provides utility methods for validating date and time formats and ranges.
 * These methods check the validity of time, date, and the two time(start and end time).
 */
public interface DateAndTime {
	/**
     * Checks if the provided time is valid according to the "HH:mm" format.
     *
     * @param time the time string to be checked
     * @return true if the time is valid, false otherwise
     */
	static boolean timeChecker(String time) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(time, timeFormatter);
            return true;
        } catch (Exception e) {
            return false;
        }
	}
	/**
     * Checks if the provided start and end time are valid and if the end time is after the start time.
     *
     * @param startTime the start time string in "HH:mm" format
     * @param endTime   the end time string in "HH:mm" format
     * @return true if the times are valid and the end time is after the start time, false otherwise
     */
	static boolean timeChecker(String startTime, String endTime) {
		if (!timeChecker(endTime)) {
            return false;
        }
        
        LocalTime from = LocalTime.parse(startTime);
        LocalTime to = LocalTime.parse(endTime);
        
        return to.isAfter(from);
	}
	/**
     * Checks if the provided date is valid and if it represents a date after the current date.
     *
     * @param date the date string to be checked, in "dd/MM/yyyy" format
     * @return true if the date is valid and is in the future, false otherwise
     */
	static boolean dateChecker(String date) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");   	
    	try {
    		LocalDate furtherDate = LocalDate.parse(date, dateFormatter);
    		LocalDate now = LocalDate.now();
    		return furtherDate.isAfter(now);
    	} catch (Exception e) {
    		return false;
    	}
	}
	/**
     * Checks if the provided time matches a valid time-of-day format ("HH:mm").
     *
     * @param time the time to be checked
     * @return true if the time string matches the valid format, false otherwise
     */
	static boolean timeOfDay(String time) {
		if (time.matches("([0-1][0-9]|2[0-3]):([0-5][0-9])")) {
            return true;
        } else {
            return false;
        }
	}
}
