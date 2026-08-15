package edu.ucalgary.oop;

/** Reports that a veterinarian has reached the daily appointment limit. */
public class DailyAppointmentLimitException extends Exception {
    /**
     * Creates an exception with an explanation of the limit.
     *
     * @param message the error message
     */
    public DailyAppointmentLimitException(String message) {
        super(message);
    }
}
