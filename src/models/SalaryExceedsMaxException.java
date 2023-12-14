package models;

/**
 * Exception class for handling situations where an employee's salary exceeds the maximum allowed.
 */
public class SalaryExceedsMaxException extends Exception {
    /**
     * Constructs a new SalaryExceedsMaxException with the specified detail message.
     *
     * @param message The detail message for the exception.
     */
    public SalaryExceedsMaxException(String message) {
        super(message);
    }
}
