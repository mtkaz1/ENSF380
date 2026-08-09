package edu.ucalgary.oop;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Stores clinic data and applies clinic rules. */
public class Clinic {
    public static final int MAX_DAILY_APPOINTMENTS = 8;

    private final DatabaseManager database;
    private final ArrayList<Staff> staff;
    private final ArrayList<Owner> owners;
    private final ArrayList<Pet> pets;
    private final ArrayList<Appointment> appointments;

    /** Loads the clinic from the database. */
    public Clinic(DatabaseManager database) throws SQLException {
        if (database == null) {
            throw new IllegalArgumentException("Database manager cannot be null");
        }
        this.database = database;
        staff = database.loadStaff();
        owners = database.loadOwners();
        pets = database.loadPets(owners);
        appointments = database.loadAppointments(pets, staff);
    }

    /** Returns all staff members. */
    public ArrayList<Staff> getStaff() {
        return staff;
    }

    /** Returns all owners. */
    public ArrayList<Owner> getOwners() {
        return owners;
    }

    /** Returns all pets. */
    public ArrayList<Pet> getPets() {
        return pets;
    }

    /** Returns all appointments. */
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    /** Returns all veterinarians. */
    public ArrayList<Veterinarian> getVeterinarians() {
        ArrayList<Veterinarian> veterinarians = new ArrayList<>();
        for (Staff member : staff) {
            if (member instanceof Veterinarian) {
                veterinarians.add((Veterinarian) member);
            }
        }
        return veterinarians;
    }

    /** Registers and stores an owner. */
    public Owner registerOwner(String name, String phone, String email)
                               throws SQLException {
        validateText(name, "Owner name");
        validateText(phone, "Phone number");
        validateText(email, "Email");

        for (Owner owner : owners) {
            if (owner.getEmail().equalsIgnoreCase(email.trim())) {
                throw new IllegalArgumentException("That email is already registered");
            }
        }

        int id = database.insertOwner(name.trim(), phone.trim(), email.trim());
        Owner owner = new Owner(id, name, phone, email);
        owners.add(owner);
        return owner;
    }

    /** Registers and stores a dog. */
    public Dog registerDog(String name, int age, Owner owner, boolean vaccinated)
                           throws SQLException {
        validatePet(name, age, owner);
        int id = database.insertDog(name.trim(), age, owner.getId(), vaccinated);
        Dog dog = new Dog(id, name, age, owner, vaccinated);
        pets.add(dog);
        owner.addPet(dog);
        return dog;
    }

    /** Registers and stores a cat. */
    public Cat registerCat(String name, int age, Owner owner, boolean indoor)
                           throws SQLException {
        validatePet(name, age, owner);
        int id = database.insertCat(name.trim(), age, owner.getId(), indoor);
        Cat cat = new Cat(id, name, age, owner, indoor);
        pets.add(cat);
        owner.addPet(cat);
        return cat;
    }

    /** Schedules and stores an appointment. */
    public Appointment scheduleAppointment(Pet pet, Veterinarian veterinarian,
                                           LocalDateTime dateTime, String notes)
                                           throws SQLException,
                                           AppointmentConflictException,
                                           DailyAppointmentLimitException {
        if (pet == null || veterinarian == null || dateTime == null) {
            throw new IllegalArgumentException("Appointment information is incomplete");
        }

        int dailyCount = 0;
        LocalDate requestedDate = dateTime.toLocalDate();

        for (Appointment appointment : appointments) {
            if (appointment.getVeterinarian().getId() == veterinarian.getId()) {
                if (appointment.getDateTime().equals(dateTime)) {
                    throw new AppointmentConflictException(
                        veterinarian.getName() + " is already booked at that time"
                    );
                }
                if (appointment.getDateTime().toLocalDate().equals(requestedDate)) {
                    dailyCount++;
                }
            }
        }

        if (dailyCount >= MAX_DAILY_APPOINTMENTS) {
            throw new DailyAppointmentLimitException(
                veterinarian.getName() + " already has "
                    + MAX_DAILY_APPOINTMENTS + " appointments that day"
            );
        }

        String safeNotes = notes == null ? "" : notes.trim();
        int id = database.insertAppointment(
            pet.getId(), veterinarian.getId(), dateTime, safeNotes
        );
        Appointment appointment = new Appointment(
            id, pet, veterinarian, dateTime, safeNotes
        );
        appointments.add(appointment);
        return appointment;
    }

    /** Cancels an appointment. */
    public void cancelAppointment(Appointment appointment) throws SQLException {
        if (appointment == null) {
            throw new IllegalArgumentException("Select an appointment to cancel");
        }
        if (!database.deleteAppointment(appointment.getId())) {
            throw new IllegalArgumentException("Appointment was not found");
        }
        appointments.remove(appointment);
    }

    /** Checks basic pet information. */
    private void validatePet(String name, int age, Owner owner) {
        validateText(name, "Pet name");
        if (age < 0) {
            throw new IllegalArgumentException("Pet age cannot be negative");
        }
        if (owner == null) {
            throw new IllegalArgumentException("Select an owner");
        }
    }

    /** Checks required text. */
    private void validateText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
    }
}
