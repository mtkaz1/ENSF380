package edu.ucalgary.oop;

/** Stores information shared by all pets. */
public abstract class Pet implements Identifiable {
    private final int id;
    private String name;
    private int age;
    private final Owner owner;

    /** Creates a pet. */
    public Pet(int id, String name, int age, Owner owner) {
        if (id <= 0 || owner == null) {
            throw new IllegalArgumentException("Pet needs a valid ID and owner");
        }
        this.id = id;
        this.owner = owner;
        setName(name);
        setAge(age);
    }

    /** Returns the pet ID. */
    public int getId() {
        return id;
    }

    /** Returns the pet name. */
    public String getName() {
        return name;
    }

    /** Changes the pet name. */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Pet name cannot be blank");
        }
        this.name = name.trim();
    }

    /** Returns the pet age. */
    public int getAge() {
        return age;
    }

    /** Changes the pet age. */
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Pet age cannot be negative");
        }
        this.age = age;
    }

    /** Returns the pet owner. */
    public Owner getOwner() {
        return owner;
    }

    /** Returns the permanent species. */
    public abstract String getSpecies();

    /** Returns a short pet description. */
    public String toString() {
        return id + " - " + name + " (" + getSpecies() + ", age " + age
            + ") - Owner: " + owner.getName();
    }
}
