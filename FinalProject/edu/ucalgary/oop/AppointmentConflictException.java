package edu.ucalgary.oop;

/** Reports that a veterinarian is already booked at a requested time. */
public class AppointmentConflictException extends Exception {
    /**
     * Creates an exception with an explanation of the conflict.
     *
     * @param message the error message
     */
    public AppointmentConflictException(String message) {
        super(message);
    }
}
