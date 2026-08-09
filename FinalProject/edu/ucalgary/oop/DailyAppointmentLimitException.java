package edu.ucalgary.oop;

/** Reports a veterinarian's full schedule. */
public class DailyAppointmentLimitException extends Exception {
    private static final long serialVersionUID = 1L;

    /** Creates the exception. */
    public DailyAppointmentLimitException(String message) {
        super(message);
    }
}
