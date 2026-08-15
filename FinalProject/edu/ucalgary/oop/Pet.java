package edu.ucalgary.oop;

/** Stores information shared by dogs and cats. */
public abstract class Pet implements Identifiable {
    private final int id;
    private String name;
    private int age;
    private final Owner owner;

    /**
     * Creates a pet.
     *
     * @param id the database ID
     * @param name the pet's name
     * @param age the pet's age
     * @param owner the pet's owner
     * @throws IllegalArgumentException if any value is invalid
     */
    public Pet(int id, String name, int age, Owner owner) {
        if (id <= 0 || owner == null) {
            throw new IllegalArgumentException(
                "Pet needs a valid ID and owner"
            );
        }
        this.id = id;
        this.owner = owner;
        setName(name);
        setAge(age);
    }

    /**
     * Returns the pet ID.
     *
     * @return the pet ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the pet's name.
     *
     * @return the pet's name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the pet's name.
     *
     * @param name the new name
     * @throws IllegalArgumentException if the name is blank
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet name cannot be blank");
        }
        this.name = name.trim();
    }

    /**
     * Returns the pet's age.
     *
     * @return the pet's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Changes the pet's age.
     *
     * @param age the new age
     * @throws IllegalArgumentException if the age is negative
     */
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Pet age cannot be negative");
        }
        this.age = age;
    }

    /**
     * Returns the pet's owner.
     *
     * @return the owner
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     * Returns the pet's species.
     *
     * @return the species
     */
    public abstract String getSpecies();

    /**
     * Returns a short pet description.
     *
     * @return the pet description
     */
    public String toString() {
        return id + " - " + name + " (" + getSpecies() + ", age " + age
            + ") - Owner: " + owner.getName();
    }
}
