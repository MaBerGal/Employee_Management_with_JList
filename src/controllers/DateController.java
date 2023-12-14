package controllers;

// Required to allow proper serialization of objects that utilize this controller.
import java.io.Serializable;
// For working with dates.
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
// For the use of a custom date exception.
import models.InvalidDateException;

/**
 * Utility class for operating with dates.
 */
public class DateController implements Serializable {

    /**
     * Creates a GregorianCalendar instance from a date string in "DD/MM/YYYY" format.
     *
     * @param date The date string in "DD/MM/YYYY" format.
     * @return A GregorianCalendar instance representing the parsed date.
     * @throws InvalidDateException If the date string is not in the correct format or contains invalid data.
     */
    public GregorianCalendar createGregorianCalendar(String date) throws InvalidDateException {
        // Split the date string into day, month, and year parts using the slash (/) as a delimiter.
        String[] parts = date.split("-");

        // Before proceeding with the GregorianCalendar construction, verify that the array's size is correct.
        if (parts.length != 3) {
            throw new InvalidDateException("Invalid date format. The date should be in 'DD-MM-YYYY' format.");
        }
        // Extract day, month (adjusted for zero-based indexing) and year.
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]) - 1; // Calendar months are zero-based.
        int year = Integer.parseInt(parts[2]);

        // Create and return the GregorianCalendar instance.
        return new GregorianCalendar(year, month, day);
    }

    /**
     * Converts a GregorianCalendar instance to a formatted date string in "dd-MM-yyyy" format.
     *
     * @param calendar The GregorianCalendar instance to convert to a string.
     * @return A formatted date string representing the calendar's date.
     *         Returns "Not specified" if the input calendar is null.
     */
    public String calendarToString(GregorianCalendar calendar) {
        // Check if the input calendar is not null first.
        if (calendar != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(calendar.getTime());
        } else {
            return "Not specified";
        }
    }

}
