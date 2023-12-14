package models;

// For working with dates.
import java.util.GregorianCalendar;

/**
 * Subclass representing a Programmer, extending the Employee class.
 */
public class Programmer extends Employee implements DateCalculator {

    /*
     * Stores a unique ID for object identification purposes throughout serialization.
    @Serial
    private static final long serialVersionUID = 2L;*/

    /**
     * Stores the monthly extra salary specific to Programmers.
     */
    private double monthlyExtra;

    /**
     * Stores the main programming language used by the Programmer.
     */
    private String mainLanguage;

    private boolean monthlyExtraCalculable = true;

    /**
     * Constructor for creating a Programmer object.
     *
     * @param name         The name of the Programmer.
     * @param employeeNumber   The unique identifier for the Programmer.
     * @param hireDate     The date when the Programmer was hired in "DD/MM/YYYY" format.
     * @param salary       The monthly salary of the Programmer.
     * @param maxSalary    The maximum salary for the Programmer.
     * @param monthlyExtra The monthly extra salary specific to Programmers.
     * @param mainLanguage The main programming language used by the Programmer.
     * @throws SalaryExceedsMaxException If the specified salary exceeds the maximum allowed.
     * @throws InvalidDateException If the provided hire date is invalid.
     */
    public Programmer(int employeeNumber, String name, String hireDate, double salary, double maxSalary, double monthlyExtra, String mainLanguage) throws SalaryExceedsMaxException, InvalidDateException {
        super(employeeNumber, name, hireDate, salary, maxSalary);
        this.monthlyExtra = monthlyExtra;
        this.mainLanguage = mainLanguage;
    }

    // Getter methods for retrieving programmer data.

    /**
     * Getter method for retrieving the monthly extra salary.
     *
     * @return The monthly extra salary specific to Programmers.
     */
    public double getMonthlyExtra() {
        return monthlyExtra;
    }

    /**
     * Getter method for retrieving the main programming language.
     *
     * @return The main programming language used by the Programmer.
     */
    public String getMainLanguage() {
        return mainLanguage;
    }

    /**
     * Getter method for checking if the monthly extra has already been calculated or not.
     *
     * @return A flag on whether the monthly extra has already been calculated or not.
     */
    public boolean getMonthlyExtraCalculable() {
        return monthlyExtraCalculable;
    }

    // Setter methods for modifying the analyst's data.

    /**
     * Setter method for modifying the monthly extra salary.
     *
     * @param monthlyExtra The new monthly extra salary specific to Programmers.
     */
    public void setMonthlyExtra(double monthlyExtra) {
        this.monthlyExtra = monthlyExtra;
    }

    /**
     * Setter method for modifying the main programming language.
     *
     * @param mainLanguage The new main programming language used by the Programmer.
     */
    public void setMainLanguage(String mainLanguage) {
        this.mainLanguage = mainLanguage;
    }

    public void setMonthlyExtraCalculable(boolean monthlyExtraCalculable) {
        this.monthlyExtraCalculable = monthlyExtraCalculable;
    }

    /**
     * Checks if one or more months have passed since the date of hire.
     *
     * @return True if at least one month has passed, false otherwise.
     */
    @Override
    public boolean haveMonthsPassed() {
        // Get the current date.
        GregorianCalendar currentDate = new GregorianCalendar();

        // Get the hire date using the getter method.
        GregorianCalendar hireDate = getHireDate();

        // Calculate the difference in months between the current date and the hire date.
        int monthsPassed = ((currentDate.get(YEAR) - hireDate.get(YEAR)) * 12) + (currentDate.get(MONTH) - hireDate.get(MONTH));

        // Check if at least one month has passed.
        return monthsPassed >= 1;
    }

    /**
     * Checks if one or more years have passed since the date of hire.
     *
     * @return True if at least one year has passed, false otherwise.
     */
    @Override
    public boolean haveYearsPassed() {
        // Get the current date.
        GregorianCalendar currentDate = new GregorianCalendar();

        // Get the hire date using the getter method.
        GregorianCalendar hireDate = getHireDate();

        // Calculate the difference in years between the current date and the hire date, without considering month and day.
        int yearsPassed = currentDate.get(YEAR) - hireDate.get(YEAR);

        // Check if at least one year has passed by considering month and day.
        if (currentDate.get(MONTH) < hireDate.get(MONTH) || (currentDate.get(MONTH) == hireDate.get(MONTH) && currentDate.get(DAY) < hireDate.get(DAY))) {
            yearsPassed--;
        }

        return yearsPassed >= 1;
    }

    /**
     * Returns a string representation of the Analyst object.
     *
     * @return A string containing the analyst's name, unique identifier, hire date, salary and maximum salary.
     */
    @Override
    public String toString() {
        return "Programmer{" +
                "employeeNumber=" + getEmployeeNumber() +
                ", name=" + getMaskedName() +
                ", hireDate=" + dateController.calendarToString(getHireDate()) +
                ", salary=" + getSalary() +
                ", maxSalary=" + getMaxSalary() +
                ", monthlyExtra=" + getMonthlyExtra() +
                ", mainLanguage=" + getMainLanguage() +
                '}';
    }

}
