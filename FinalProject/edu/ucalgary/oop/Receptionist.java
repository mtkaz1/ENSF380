package edu.ucalgary.oop;

/** Represents a clinic receptionist. */
public class Receptionist extends Staff {
    private static final String ROLE = "Receptionist";

    /**
     * Creates a receptionist.
     *
     * @param id the database ID
     * @param name the receptionist's name
     * @throws IllegalArgumentException if the ID or name is invalid
     */
    public Receptionist(int id, String name) {
        super(id, name);
    }

    /**
     * Returns the receptionist role.
     *
     * @return {@code "Receptionist"}
     */
    public String getRole() {
        return ROLE;
    }
}
