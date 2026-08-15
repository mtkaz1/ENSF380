package edu.ucalgary.oop;

/** Stores information shared by veterinarians and receptionists. */
public abstract class Staff implements Identifiable {
    private static int staffCount = 0;

    private final int id;
    private String name;

    /**
     * Creates a staff member.
     *
     * @param id the database ID
     * @param name the staff member's name
     * @throws IllegalArgumentException if the ID or name is invalid
     */
    public Staff(int id, String name) {
        if (id <= 0 || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Staff needs a valid ID and name"
            );
        }
        this.id = id;
        this.name = name.trim();
        staffCount++;
    }

    /**
     * Returns the staff ID.
     *
     * @return the staff ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the staff member's name.
     *
     * @return the staff member's name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the staff member's name.
     *
     * @param name the new name
     * @throws IllegalArgumentException if the name is blank
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff name cannot be blank");
        }
        this.name = name.trim();
    }

    /**
     * Returns the staff member's role.
     *
     * @return the staff role
     */
    public abstract String getRole();

    /**
     * Returns the number of staff objects.
     *
     * @return the staff count
     */
    public static int getStaffCount() {
        return staffCount;
    }

    /** Decreases the staff count after deletion. */
    public static void decreaseStaffCount() {
        if (staffCount > 0) {
            staffCount--;
        }
    }

    /**
     * Returns a short staff description.
     *
     * @return the staff description
     */
    public String toString() {
        return id + " - " + name + " (" + getRole() + ")";
    }
}
