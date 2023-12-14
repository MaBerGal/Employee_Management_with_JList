package models;

/**
 * An exception indicating an invalid date in the context of employee information.
 */
public class InvalidDateException extends Exception {

    /**
     * Constructs an InvalidDateException with the specified detail message.
     *
     * @param message The detail message providing information about the exception.
     */
    public InvalidDateException(String message) {
        super(message);
    }
}
