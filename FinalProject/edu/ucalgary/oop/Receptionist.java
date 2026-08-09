package edu.ucalgary.oop;

/** Represents a receptionist. */
public class Receptionist extends Staff {
    private static final String ROLE = "Receptionist";

    /** Creates a receptionist. */
    public Receptionist(int id, String name) {
        super(id, name);
    }

    /** Returns the receptionist role. */
    public String getRole() {
        return ROLE;
    }
}
