package models;

// Dependency for working with date calculations.
import java.util.Calendar;

/**
 * Interface for date calculations.
 */
public interface DateCalculator {
    /**
     * Constant representing the day of the month in the Calendar class.
     */
    int DAY = Calendar.DAY_OF_MONTH;

    /**
     * Constant representing the month in the Calendar class.
     */
    int MONTH = Calendar.MONTH;

    /**
     * Constant representing the year in the Calendar class.
     */
    int YEAR = Calendar.YEAR;

    /**
     * Checks if a certain number of months have passed since the opening date.
     *
     * @return True if the specified number of months has passed, false otherwise.
     */
    boolean haveMonthsPassed();

    /**
     * Checks if a certain number of years have passed since the opening date.
     *
     * @return True if the specified number of years has passed, false otherwise.
     */
    boolean haveYearsPassed();
}

