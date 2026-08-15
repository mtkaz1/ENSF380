package edu.ucalgary.oop;

import java.util.ArrayList;

/** Stores an owner's contact information and registered pets. */
public class Owner implements Identifiable {
    private static int ownerCount = 0;

    private final int id;
    private String name;
    private String phone;
    private String email;
    private ArrayList<Pet> pets = new ArrayList<>();

    /**
     * Creates an owner.
     *
     * @param id the database ID
     * @param name the owner's name
     * @param phone the owner's phone number
     * @param email the owner's email address
     * @throws IllegalArgumentException if any value is invalid
     */
    public Owner(int id, String name, String phone, String email) {
        if (id <= 0) {
            throw new IllegalArgumentException("Owner needs a valid ID");
        }
        this.id = id;
        setName(name);
        setPhone(phone);
        setEmail(email);
        ownerCount++;
    }

    /**
     * Returns the owner ID.
     *
     * @return the owner ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the owner's name.
     *
     * @return the owner's name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the owner's name.
     *
     * @param name the new name
     * @throws IllegalArgumentException if the name is blank
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be blank");
        }
        this.name = name.trim();
    }

    /**
     * Returns the owner's phone number.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Changes the owner's phone number.
     *
     * @param phone the new phone number
     * @throws IllegalArgumentException if the phone number is blank
     */
    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be blank");
        }
        this.phone = phone.trim();
    }

    /**
     * Returns the owner's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Changes the owner's email address.
     *
     * @param email the new email address
     * @throws IllegalArgumentException if the email address is blank
     */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
        this.email = email.trim();
    }

    /**
     * Returns the owner's registered pets.
     *
     * @return the owner's pets
     */
    public ArrayList<Pet> getPets() {
        return pets;
    }

    /**
     * Adds a pet to the owner.
     *
     * @param pet the pet to add
     * @throws IllegalArgumentException if the pet is {@code null}
     */
    public void addPet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        pets.add(pet);
    }

    /**
     * Removes a pet from the owner.
     *
     * @param pet the pet to remove
     */
    public void removePet(Pet pet) {
        pets.remove(pet);
    }

    /**
     * Returns the number of owner objects created and not deleted.
     *
     * @return the owner count
     */
    public static int getOwnerCount() {
        return ownerCount;
    }

    /** Decreases the owner count after deletion. */
    public static void decreaseOwnerCount() {
        if (ownerCount > 0) {
            ownerCount--;
        }
    }

    /**
     * Returns a short owner description.
     *
     * @return the owner description
     */
    public String toString() {
        return id + " - " + name + " - " + phone + " - " + email;
    }
}
