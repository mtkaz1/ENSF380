package edu.ucalgary.oop;

/** Represents a dog and its vaccination status. */
public class Dog extends Pet {
    private static final String SPECIES = "Dog";
    private boolean vaccinated;

    /**
     * Creates a dog.
     *
     * @param id the database ID
     * @param name the dog's name
     * @param age the dog's age
     * @param owner the dog's owner
     * @param vaccinated whether the dog is vaccinated
     * @throws IllegalArgumentException if inherited pet data is invalid
     */
    public Dog(int id, String name, int age, Owner owner, boolean vaccinated) {
        super(id, name, age, owner);
        this.vaccinated = vaccinated;
    }

    /**
     * Returns the dog's species.
     *
     * @return {@code "Dog"}
     */
    public String getSpecies() {
        return SPECIES;
    }

    /**
     * Returns whether the dog is vaccinated.
     *
     * @return {@code true} if the dog is vaccinated
     */
    public boolean isVaccinated() {
        return vaccinated;
    }

    /**
     * Changes the dog's vaccination status.
     *
     * @param vaccinated the new vaccination status
     */
    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    /**
     * Returns a short dog description.
     *
     * @return the dog description
     */
    public String toString() {
        return super.toString() + " - Vaccinated: "
            + (vaccinated ? "Yes" : "No");
    }
}
