package edu.ucalgary.oop;

/** Represents a cat and whether it is an indoor cat. */
public class Cat extends Pet {
    private static final String SPECIES = "Cat";
    private boolean indoor;

    /**
     * Creates a cat.
     *
     * @param id the database ID
     * @param name the cat's name
     * @param age the cat's age
     * @param owner the cat's owner
     * @param indoor whether the cat is an indoor cat
     * @throws IllegalArgumentException if inherited pet data is invalid
     */
    public Cat(int id, String name, int age, Owner owner, boolean indoor) {
        super(id, name, age, owner);
        this.indoor = indoor;
    }

    /**
     * Returns the cat's species.
     *
     * @return {@code "Cat"}
     */
    public String getSpecies() {
        return SPECIES;
    }

    /**
     * Returns whether the cat is an indoor cat.
     *
     * @return {@code true} if the cat is an indoor cat
     */
    public boolean isIndoor() {
        return indoor;
    }

    /**
     * Changes whether the cat is an indoor cat.
     *
     * @param indoor the new indoor status
     */
    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    /**
     * Returns a short cat description.
     *
     * @return the cat description
     */
    public String toString() {
        return super.toString() + " - Indoor: " + (indoor ? "Yes" : "No");
    }
}
