package edu.ucalgary.oop;

import java.time.LocalDateTime;

/** Stores the pet, veterinarian, time, and notes for an appointment. */
public class Appointment implements Identifiable {
    private final int id;
    private final Pet pet;
    private final Veterinarian veterinarian;
    private final LocalDateTime dateTime;
    private String notes;

    /**
     * Creates an appointment.
     *
     * @param id the database ID
     * @param pet the pet receiving care
     * @param veterinarian the veterinarian assigned to the appointment
     * @param dateTime the appointment date and time
     * @param notes additional appointment notes
     * @throws IllegalArgumentException if required information is invalid
     */
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

    /**
     * Returns the appointment ID.
     *
     * @return the appointment ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the pet receiving care.
     *
     * @return the pet
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Returns the assigned veterinarian.
     *
     * @return the veterinarian
     */
    public Veterinarian getVeterinarian() {
        return veterinarian;
    }

    /**
     * Returns the appointment date and time.
     *
     * @return the appointment date and time
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the appointment notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Changes the appointment notes.
     *
     * @param notes the new notes, or {@code null} for no notes
     */
    public void setNotes(String notes) {
        this.notes = notes == null ? "" : notes.trim();
    }

    /**
     * Returns a short appointment description.
     *
     * @return the appointment description
     */
    public String toString() {
        return id + " - " + dateTime.toString().replace("T", " ") + " - "
            + pet.getName() + " with " + veterinarian.getName();
    }

}
