package edu.ucalgary.oop;

/** Reports a double-booked veterinarian. */
public class AppointmentConflictException extends Exception {
    /** Creates the exception. */
    public AppointmentConflictException(String message) {
        super(message);
    }
}
