package edu.ucalgary.oop;

/** Represents a cat. */
public class Cat extends Pet {
    private static final String SPECIES = "Cat";
    private boolean indoor;

    /** Creates a cat. */
    public Cat(int id, String name, int age, Owner owner, boolean indoor) {
        super(id, name, age, owner);
        this.indoor = indoor;
    }

    /** Returns the cat species. */
    public String getSpecies() {
        return SPECIES;
    }

    /** Returns whether the cat is indoors. */
    public boolean isIndoor() {
        return indoor;
    }

    /** Changes whether the cat is indoors. */
    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    /** Returns a short cat description. */
    public String toString() {
        return super.toString() + " - Indoor: " + (indoor ? "Yes" : "No");
    }
}
