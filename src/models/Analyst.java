package models;

// For working with dates.
import java.util.GregorianCalendar;

/**
 * Subclass representing an Analyst, extending the Employee class.
 */
public class Analyst extends Employee implements DateCalculator {
    /**
     * Stores a unique ID for object identification purposes throughout serialization.
     */
    //@Serial
    //private static final long serialVersionUID = 3L;*/

    /**
     * Stores the annual bonus as a percentage extra on the monthly salary applied annually.
     */
    private double annualBonus;

    /**
     * Stores an additional qualification or attribute specific to Analysts.
     */
    private String additionalQualification;

    // In Analyst class
    private boolean annualBonusCalculable = true;

    /**
     * Constructor for creating an Analyst object.
     *
     * @param name                The name of the Analyst.
     * @param employeeNumber      The unique identifier for the Analyst.
     * @param hireDate            The date when the Analyst was hired in "DD/MM/YYYY" format.
     * @param salary              The monthly salary of the Analyst.
     * @param maxSalary           The maximum salary for the Analyst.
     * @param annualBonus         The annual bonus as a percentage extra on the monthly salary.
     * @param additionalQualification  An additional qualification or attribute specific to Analysts.
     * @throws SalaryExceedsMaxException If the specified salary exceeds the maximum allowed.
     * @throws InvalidDateException If the provided hire date is invalid.
     */
    public Analyst(int employeeNumber, String name, String hireDate, double salary, double maxSalary, double annualBonus, String additionalQualification) throws SalaryExceedsMaxException, InvalidDateException {
        super(employeeNumber, name, hireDate, salary, maxSalary);
        this.annualBonus = annualBonus;
        this.additionalQualification = additionalQualification;
    }

    // Getter methods for retrieving analyst data.

    /**
     * Getter method for retrieving the annual bonus.
     *
     * @return The annual bonus as a percentage extra on the monthly salary.
     */
    public double getAnnualBonus() {
        return annualBonus;
    }

    /**
     * Getter method for retrieving the additional qualification.
     *
     * @return An additional qualification or attribute specific to Analysts.
     */
    public String getAdditionalQualification() {
        return additionalQualification;
    }

    /**
     * Getter method for retrieving information on whether the employee's salary has been updated or not.
     *
     * @return A flag on whether the annual bonus has been calculated already or not.
     */
    public boolean getAnnualBonusCalculable() {
        return annualBonusCalculable;
    }

    // Setter methods for modifying the analyst's data.

    /**
     * Setter method for modifying the annual bonus.
     *
     * @param annualBonus The new annual bonus as a percentage extra on the monthly salary.
     */
    public void setAnnualBonus(double annualBonus) {
        this.annualBonus = annualBonus;
    }

    /**
     * Setter method for modifying the additional qualification.
     *
     * @param additionalQualification The new additional qualification or attribute specific to Analysts.
     */
    public void setAdditionalQualification(String additionalQualification) {
        this.additionalQualification = additionalQualification;
    }

    /**
     * Setter method for modifying information on whether the employee's salary has been updated or not.
     */
    public void setAnnualBonusCalculable(boolean annualBonusCalculable) {
        this.annualBonusCalculable = annualBonusCalculable;
    }

    /**
     * Checks if one or more months have passed since the date of hire.
     *
     * @return True if at least one month has passed, false otherwise.
     */
    @Override
    public boolean haveMonthsPassed() {
        // Get the current date
        GregorianCalendar currentDate = new GregorianCalendar();

        // Get the hire date using the getter method
        GregorianCalendar hireDate = getHireDate();

        // Calculate the difference in months between the current date and the hire date
        int monthsPassed = ((currentDate.get(YEAR) - hireDate.get(YEAR)) * 12) + (currentDate.get(MONTH) - hireDate.get(MONTH));

        // Check if at least one month has passed
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
        return "Analyst{" +
                "employeeNumber=" + getEmployeeNumber() +
                ", name=" + getMaskedName() +
                ", hireDate=" + dateController.calendarToString(getHireDate()) +
                ", salary=" + getSalary() +
                ", maxSalary=" + getMaxSalary() +
                ", annualBonus=" + getAnnualBonus() +
                ", additionalQualification=" + getAdditionalQualification() +
                '}';
    }

}
