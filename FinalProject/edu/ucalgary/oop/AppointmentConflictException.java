package edu.ucalgary.oop;

/** Reports a double-booked veterinarian. */
public class AppointmentConflictException extends Exception {
    private static final long serialVersionUID = 1L;

    /** Creates the exception. */
    public AppointmentConflictException(String message) {
        super(message);
    }
}
