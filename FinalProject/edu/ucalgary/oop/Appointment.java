package edu.ucalgary.oop;

import java.time.LocalDateTime;

/** Represents a veterinary appointment. */
public class Appointment implements Identifiable {
    private final int id;
    private final Pet pet;
    private final Veterinarian veterinarian;
    private final LocalDateTime dateTime;
    private String notes;

    /** Creates an appointment. */
    public Appointment(int id, Pet pet, Veterinarian veterinarian,
                       LocalDateTime dateTime, String notes) {
        if (id <= 0 || pet == null || veterinarian == null
                || dateTime == null) {
            throw new IllegalArgumentException(
                "Appointment information is incomplete"
            );
        }
        this.id = id;
        this.pet = pet;
        this.veterinarian = veterinarian;
        this.dateTime = dateTime;
        this.notes = notes == null ? "" : notes.trim();
    }

    /** Returns the appointment ID. */
    public int getId() {
        return id;
    }

    /** Returns the pet. */
    public Pet getPet() {
        return pet;
    }

    /** Returns the veterinarian. */
    public Veterinarian getVeterinarian() {
        return veterinarian;
    }

    /** Returns the appointment date and time. */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /** Returns the appointment notes. */
    public String getNotes() {
        return notes;
    }

    /** Changes the appointment notes. */
    public void setNotes(String notes) {
        this.notes = notes == null ? "" : notes.trim();
    }

    /** Returns a short appointment description. */
    public String toString() {
        return id + " - " + dateTime.toString().replace("T", " ") + " - "
            + pet.getName() + " with " + veterinarian.getName();
    }
}
