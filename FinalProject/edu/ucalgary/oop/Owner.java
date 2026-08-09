package edu.ucalgary.oop;

import java.util.ArrayList;

/** Represents a pet owner. */
public class Owner implements Identifiable {
    private static int ownerCount = 0;

    private final int id;
    private String name;
    private String phone;
    private String email;
    private ArrayList<Pet> pets = new ArrayList<>();

    /** Creates an owner. */
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

    /** Returns the owner ID. */
    public int getId() {
        return id;
    }

    /** Returns the owner name. */
    public String getName() {
        return name;
    }

    /** Changes the owner name. */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be blank");
        }
        this.name = name.trim();
    }

    /** Returns the phone number. */
    public String getPhone() {
        return phone;
    }

    /** Changes the phone number. */
    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be blank");
        }
        this.phone = phone.trim();
    }

    /** Returns the email address. */
    public String getEmail() {
        return email;
    }

    /** Changes the email address. */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
        this.email = email.trim();
    }

    /** Returns the owner's pets. */
    public ArrayList<Pet> getPets() {
        return pets;
    }

    /** Adds a pet to the owner. */
    public void addPet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        pets.add(pet);
    }

    /** Returns the number of owner objects. */
    public static int getOwnerCount() {
        return ownerCount;
    }

    /** Returns a short owner description. */
    public String toString() {
        return id + " - " + name + " - " + phone + " - " + email;
    }
}
