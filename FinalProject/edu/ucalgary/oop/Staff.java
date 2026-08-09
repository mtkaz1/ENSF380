package edu.ucalgary.oop;

/** Stores information shared by all staff members. */
public abstract class Staff implements Identifiable {
    private static int staffCount = 0;

    private final int id;
    private String name;

    /** Creates a staff member. */
    public Staff(int id, String name) {
        if (id <= 0 || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff needs a valid ID and name");
        }
        this.id = id;
        this.name = name.trim();
        staffCount++;
    }

    /** Returns the staff ID. */
    public int getId() {
        return id;
    }

    /** Returns the staff name. */
    public String getName() {
        return name;
    }

    /** Changes the staff name. */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff name cannot be blank");
        }
        this.name = name.trim();
    }

    /** Returns the permanent staff role. */
    public abstract String getRole();

    /** Returns the number of staff objects. */
    public static int getStaffCount() {
        return staffCount;
    }

    /** Returns a short staff description. */
    public String toString() {
        return id + " - " + name + " (" + getRole() + ")";
    }
}
