package interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public interface DateAndTime {
	static boolean timeChecker(String time) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(time, timeFormatter);
            return true;
        } catch (Exception e) {
            return false;
        }
	}
	
	static boolean timeChecker(String startTime, String endTime) {
		if (!timeChecker(endTime)) {
            return false;
        }
        
        LocalTime from = LocalTime.parse(startTime);
        LocalTime to = LocalTime.parse(endTime);
        
        return to.isAfter(from);
	}
	
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
	
	static boolean timeOfDay(String time) {
		if (time.matches("([0-1][0-9]|2[0-3]):([0-5][0-9])")) {
            return true;
        } else {
            return false;
        }
	}
}
