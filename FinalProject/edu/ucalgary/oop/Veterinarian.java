package edu.ucalgary.oop;

/** Represents a veterinarian and their area of expertise. */
public class Veterinarian extends Staff {
    private static final String ROLE = "Vet";
    private String specialization;

    /**
     * Creates a veterinarian.
     *
     * @param id the database ID
     * @param name the veterinarian's name
     * @param specialization the veterinarian's area of expertise
     * @throws IllegalArgumentException if any value is invalid
     */
    public Veterinarian(int id, String name, String specialization) {
        super(id, name);
        setSpecialization(specialization);
    }

    /**
     * Returns the veterinarian role.
     *
     * @return {@code "Vet"}
     */
    public String getRole() {
        return ROLE;
    }

    /**
     * Returns the veterinarian's area of expertise.
     *
     * @return the specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Changes the veterinarian's area of expertise.
     *
     * @param specialization the new specialization
     * @throws IllegalArgumentException if the specialization is blank
     */
    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "A veterinarian needs a specialization"
            );
        }
        this.specialization = specialization.trim();
    }

    /**
     * Returns a short veterinarian description.
     *
     * @return the veterinarian description
     */
    public String toString() {
        return super.toString() + " - " + specialization;
    }
}
