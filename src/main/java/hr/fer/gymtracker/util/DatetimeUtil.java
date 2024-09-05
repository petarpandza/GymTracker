package hr.fer.gymtracker.util;

import java.time.LocalDate;

public class DatetimeUtil {

    /**
     * Takes an ISO 8601 date string and returns a date string in format "dd. MM. yyyy."
     * @param s ISO 8601 date string with delimiter "-"
     * @return date string in format "dd. MM. yyyy."
     */
    public static String trimDate(String s) {
        var date =  s.split(" ")[0];
        var parts = date.split("-");
        return String.format("%s. %s. %s.", parts[2], parts[1], parts[0]);
    }

}
