package edu.ucalgary.oop;

/** Reports a veterinarian's full schedule. */
public class DailyAppointmentLimitException extends Exception {
    /** Creates the exception. */
    public DailyAppointmentLimitException(String message) {
        super(message);
    }
}
