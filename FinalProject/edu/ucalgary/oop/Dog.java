package edu.ucalgary.oop;

/** Represents a dog. */
public class Dog extends Pet {
    private static final String SPECIES = "Dog";
    private boolean vaccinated;

    /** Creates a dog. */
    public Dog(int id, String name, int age, Owner owner, boolean vaccinated) {
        super(id, name, age, owner);
        this.vaccinated = vaccinated;
    }

    /** Returns the dog species. */
    public String getSpecies() {
        return SPECIES;
    }

    /** Returns the vaccination status. */
    public boolean isVaccinated() {
        return vaccinated;
    }

    /** Changes the vaccination status. */
    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    /** Returns a short dog description. */
    public String toString() {
        return super.toString() + " - Vaccinated: "
            + (vaccinated ? "Yes" : "No");
    }
}
