package models;

// For allowing serialization.
import java.io.Serializable;
// For formatting dates.
import java.text.SimpleDateFormat;
// For working with dates.
import java.util.GregorianCalendar;
// For creating and formatting gregorian calendars.
import controllers.DateController;

/**
 * Class for storing and managing employee information.
 */
public class Employee implements Serializable, Comparable<Employee> {

    /**
     * Stores the unique identifier for the employee.
     */
    private int employeeNumber;

    /**
     * Stores the employee's name.
     */
    private transient String name;

    /**
     * Stores the employee's name masked with asterisks.
     */
    private String maskedName;

    /**
     * Stores the date when the employee was hired.
     */
    private GregorianCalendar hireDate;

    /**
     * Stores the employee's salary.
     */
    private double salary;

    /**
     * Stores the employee's maximum salary.
     */
    private double maxSalary;

    /*
     * Uses a custom date controller to create and format gregorian calendars.
     */
    DateController dateController = new DateController();

    /**
     * Constructor for creating an EmployeeModel object.
     *
     * @param name           The name of the employee.
     * @param employeeNumber The unique identifier for the employee.
     * @param hireDate       The date when the employee was hired in "DD/MM/YYYY" format.
     * @param salary         The salary of the employee.
     * @param maxSalary      The maximum salary for the employee.
     * @throws SalaryExceedsMaxException If the specified salary exceeds the maximum allowed.
     * @throws InvalidDateException      If the provided hire date is invalid.
     */
    public Employee(int employeeNumber, String name, String hireDate, double salary, double maxSalary)
            throws SalaryExceedsMaxException, InvalidDateException {
        // Handle cases in which the salary exceeds the maximum allowed salary.
        if (salary > maxSalary) {
            throw new SalaryExceedsMaxException("Employee salary cannot exceed maximum salary.");
        }

        this.employeeNumber = employeeNumber;
        this.name = name;
        nameMasker();

        try {
            this.hireDate = dateController.createGregorianCalendar(hireDate);

            // Validate if the hire date is posterior to the current system date.
            GregorianCalendar currentSystemDate = new GregorianCalendar();
            if (this.hireDate != null && this.hireDate.after(currentSystemDate)) {
                throw new InvalidDateException("Invalid hire date. The date cannot be in the future.");
            }

            // Validate if the hire date is prior to the year 1907.
            GregorianCalendar minimumDate = new GregorianCalendar(1907, GregorianCalendar.JANUARY, 1);
            if (this.hireDate != null && this.hireDate.before(minimumDate)) {
                throw new InvalidDateException("Invalid hire date. The date cannot be earlier than 1907.");
            }
        } catch (NumberFormatException nfe) {
            // Handle NumberFormatException if the parsing fails due to invalid data.
            throw new InvalidDateException("Invalid hire date. Make sure you're inputting numeric date values.");
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            // Handle ArrayIndexOutOfBoundsException if the parsing fails due to wrong formatting.
            throw new InvalidDateException("Invalid hire date format. The date should be in 'DD/MM/YYYY' format.");
        } catch (InvalidDateException ide) {
            throw new InvalidDateException("Invalid hire date. The date cannot be in the future.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.salary = salary;
        this.maxSalary = maxSalary;
    }

    // Getter methods for retrieving employee data.

    /**
     * Getter method for retrieving the employee's unique identifier.
     *
     * @return The unique identifier of the employee.
     */
    public int getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * Getter method for retrieving the employee's name.
     *
     * @return The name of the employee.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method to return a masked version of the name with asterisks.
     *
     * @return The masked name of the employee.
     */
    public String getMaskedName() {
        return maskedName;
    }

    /**
     * Getter method for retrieving the date when the employee was hired.
     *
     * @return A GregorianCalendar instance representing the date of hire.
     */
    public GregorianCalendar getHireDate() {
        return hireDate;
    }

    /**
     * Getter method for retrieving the employee's salary.
     *
     * @return The salary of the employee.
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Getter method for retrieving the maximum allowed salary for the employee.
     *
     * @return The maximum salary of the employee.
     */
    public double getMaxSalary() {
        return maxSalary;
    }

    // Setter methods for modifying the employee's data.

    /**
     * Setter method for modifying the employee's unique identifier.
     *
     * @return The unique identifier of the employee.
     */
    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /**
     * Setter method for modifying the employee's name.
     *
     * @param name The new name of the employee.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter method for modifying the date when the employee was hired.
     *
     * @param hireDate A GregorianCalendar instance representing the new date of hire.
     */
    public void setHireDate(GregorianCalendar hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * Setter method for modifying the employee's salary.
     *
     * @param salary The new salary of the employee.
     * @throws SalaryExceedsMaxException If the specified salary exceeds the maximum allowed.
     */
    public void setSalary(double salary) throws SalaryExceedsMaxException {
        if (salary > maxSalary) {
            throw new SalaryExceedsMaxException("Employee salary exceeds the maximum salary.");
        }
        this.salary = salary;
    }

    /**
     * Setter method for modifying the employee's maximum salary.
     *
     * @param maxSalary The new maximum salary of the employee.
     * @throws SalaryExceedsMaxException If the specified salary exceeds the maximum allowed.
     */
    public void setMaxSalary(double maxSalary) throws SalaryExceedsMaxException {
        if (salary > maxSalary) {
            throw new SalaryExceedsMaxException("Employee salary exceeds the new maximum salary.");
        }
        this.maxSalary = maxSalary;
    }

    /**
     * Masks the employee's name by replacing each character with an asterisk ('*').
     * The length of the masked name is equal to the length of the original name.
     */
    public void nameMasker() {
        int nameLength = name.length();
        this.maskedName = "*".repeat(nameLength);
    }

    /**
     * Compares this Employee object with another Employee object for order.
     * Returns a negative value if this instance is less than the other,
     * zero if they are equal, and a positive value if this instance is greater.
     *
     * @param other The Employee object to compare with.
     * @return A negative value, zero, or a positive value if this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Employee other) {
        return this.getEmployeeNumber() - other.getEmployeeNumber();
    }

    /**
     * Returns a string representation of the Employee object.
     *
     * @return A string containing the employee's name, unique identifier, hire date, salary, and maximum salary.
     */
    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", name=" +  maskedName +
                ", hireDate=" + dateController.calendarToString(hireDate) +
                ", salary=" + salary +
                ", maxSalary=" + maxSalary +
                '}';
    }

}