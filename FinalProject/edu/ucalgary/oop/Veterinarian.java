package edu.ucalgary.oop;

/** Represents a veterinarian. */
public class Veterinarian extends Staff {
    private static final String ROLE = "Vet";
    private String specialization;

    /** Creates a veterinarian. */
    public Veterinarian(int id, String name, String specialization) {
        super(id, name);
        setSpecialization(specialization);
    }

    /** Returns the veterinarian role. */
    public String getRole() {
        return ROLE;
    }

    /** Returns the area of expertise. */
    public String getSpecialization() {
        return specialization;
    }

    /** Changes the area of expertise. */
    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new IllegalArgumentException("A veterinarian needs a specialization");
        }
        this.specialization = specialization.trim();
    }

    /** Returns a short veterinarian description. */
    public String toString() {
        return super.toString() + " - " + specialization;
    }
}
