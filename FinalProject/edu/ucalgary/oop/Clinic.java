package edu.ucalgary.oop;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Manages clinic data in memory and saves changes to the database. */
public class Clinic {
    /** Maximum appointments allowed for one veterinarian in one day. This can be set to the users choosing. */
    public static final int MAX_DAILY_APPOINTMENTS = 8;

    private final DatabaseManager database;
    private final ArrayList<Staff> staff;
    private final ArrayList<Owner> owners;
    private final ArrayList<Pet> pets;
    private final ArrayList<Appointment> appointments;

    /**
     * Loads all clinic data from the database.
     *
     * @param database the database manager to use
     * @throws SQLException if clinic data cannot be loaded
     * @throws IllegalArgumentException if  the database manager is null
     */
    public Clinic(DatabaseManager database) throws SQLException {
        if (database == null) {
            throw new IllegalArgumentException(
                "Database manager can't be null"
            );
        }
        this.database = database;
        staff = database.loadStaff();
        owners = database.loadOwners();
        pets = database.loadPets(owners);
        appointments = database.loadAppointments(pets, staff);
    }

    /**
     * Returns all staff members stored in memory.
     *
     * @return the staff members
     */
    public ArrayList<Staff> getStaff() {
        return staff;
    }

    /**
     * Returns all owners stored in memory.
     *
     * @return the owners
     */
    public ArrayList<Owner> getOwners() {
        return owners;
    }

    /**
     * Returns all pets stored in memory.
     *
     * @return the pets
     */
    public ArrayList<Pet> getPets() {
        return pets;
    }

    /**
     * Returns all appointments stored in memory.
     *
     * @return the appointments
     */
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Returns all veterinarians stored in memory.
     *
     * @return the veterinarians
     */
    public ArrayList<Veterinarian> getVeterinarians() {
        ArrayList<Veterinarian> veterinarians = new ArrayList<>();
        for (Staff member : staff) {
            if (member.getRole().equals("Vet")) {
                veterinarians.add((Veterinarian) member);
            }
        }
        return veterinarians;
    }

    /**
     * Registers a veterinarian in memory and in the database.
     *
     * @param name the veterinarian's name
     * @param specialization the veterinarian's area of expertise
     * @return the registered veterinarian
     * @throws SQLException if the veterinarian cannot be stored
     * @throws IllegalArgumentException if the information is invalid
     */
    public Veterinarian registerVeterinarian(String name, String specialization)
                                               throws SQLException {
        validateName(name, "Staff name");
        validateText(specialization, "Specialization");
        int id = database.insertVeterinarian(
            name.trim(), specialization.trim()
        );
        Veterinarian veterinarian = new Veterinarian(
            id, name, specialization
        );
        staff.add(veterinarian);
        return veterinarian;
    }

    /**
     * Registers a receptionist in memory and in the database.
     *
     * @param name the receptionist's name
     * @return the registered receptionist
     * @throws SQLException if the receptionist cannot be stored
     * @throws IllegalArgumentException if the name is invalid
     */
    public Receptionist registerReceptionist(String name) throws SQLException {
        validateName(name, "Staff name");
        int id = database.insertReceptionist(name.trim());
        Receptionist receptionist = new Receptionist(id, name);
        staff.add(receptionist);
        return receptionist;
    }

    /**
     * Registers an owner in memory and in the database.
     *
     * @param name the owner's name
     * @param phone the owner's phone number
     * @param email the owner's email address
     * @return the registered owner
     * @throws SQLException if the owner cannot be stored
     * @throws IllegalArgumentException if the information is invalid
     */
    public Owner registerOwner(String name, String phone, String email)
                               throws SQLException {
        validateName(name, "Owner name");
        validatePhone(phone);
        validateEmail(email);

        for (Owner owner : owners) {
            if (owner.getEmail().equalsIgnoreCase(email.trim())) {
                throw new IllegalArgumentException(
                    "That email is already registered"
                );
            }
        }

        int id = database.insertOwner(name.trim(), phone.trim(), email.trim());
        Owner owner = new Owner(id, name, phone, email);
        owners.add(owner);
        return owner;
    }

    /**
     * Registers a dog in memory and in the database.
     *
     * @param name the dog's name
     * @param age the dog's age
     * @param owner the dog's owner
     * @param vaccinated whether the dog is vaccinated
     * @return the registered dog
     * @throws SQLException if the dog cannot be stored
     * @throws IllegalArgumentException if the information is invalid
     */
    public Dog registerDog(String name, int age, Owner owner,
                           boolean vaccinated)
                           throws SQLException {
        validatePet(name, age, owner);
        int id = database.insertDog(
            name.trim(), age, owner.getId(), vaccinated
        );
        Dog dog = new Dog(id, name, age, owner, vaccinated);
        pets.add(dog);
        owner.addPet(dog);
        return dog;
    }

    /**
     * Registers a cat in memory and in the database.
     *
     * @param name the cat's name
     * @param age the cat's age
     * @param owner the cat's owner
     * @param indoor whether the cat is an indoor cat
     * @return the registered cat
     * @throws SQLException if the cat cannot be stored
     * @throws IllegalArgumentException if the information is invalid
     */
    public Cat registerCat(String name, int age, Owner owner, boolean indoor)
                           throws SQLException {
        validatePet(name, age, owner);
        int id = database.insertCat(name.trim(), age, owner.getId(), indoor);
        Cat cat = new Cat(id, name, age, owner, indoor);
        pets.add(cat);
        owner.addPet(cat);
        return cat;
    }

    /**
     * Schedules an appointment in memory and in the database.
     *
     * @param pet the pet receiving care
     * @param veterinarian the veterinarian assigned to the appointment
     * @param dateTime the appointment date and time
     * @param notes additional appointment notes
     * @return the scheduled appointment
     * @throws SQLException if the appointment cannot be stored
     * @throws AppointmentConflictException if the veterinarian is booked
     * @throws DailyAppointmentLimitException if the daily limit is reached
     * @throws IllegalArgumentException if the information is invalid
     */
    public Appointment scheduleAppointment(Pet pet, Veterinarian veterinarian,
                                           LocalDateTime dateTime, String notes)
                                           throws SQLException,
                                           AppointmentConflictException,
                                           DailyAppointmentLimitException {
        if (pet == null || veterinarian == null || dateTime == null) {
            throw new IllegalArgumentException(
                "Appointment information is incomplete"
            );
        }
        if (!dateTime.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                "Appointment must be in the future"
            );
        }

        int dailyCount = 0;
        LocalDate requestedDate = dateTime.toLocalDate();

        for (Appointment appointment : appointments) {
            if (appointment.getVeterinarian().getId() == veterinarian.getId()) {
                if (appointment.getDateTime().equals(dateTime)) {
                    throw new AppointmentConflictException(
                        veterinarian.getName()
                            + " is already booked at that time"
                    );
                }
                if (appointment.getDateTime().toLocalDate()
                        .equals(requestedDate)) {
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

    /**
     * Removes an appointment from memory and the database.
     *
     * @param appointment the appointment to cancel
     * @throws SQLException if the appointment cannot be deleted
     * @throws IllegalArgumentException if the appointment is not found
     */
    public void cancelAppointment(Appointment appointment) throws SQLException {
        if (appointment == null) {
            throw new IllegalArgumentException(
                "Select an appointment to cancel"
            );
        }
        if (!database.deleteAppointment(appointment.getId())) {
            throw new IllegalArgumentException("Appointment was not found");
        }
        appointments.remove(appointment);
    }

    /**
     * Deletes a staff member and their appointments.
     *
     * @param member the staff member to delete
     * @throws SQLException if the staff member cannot be deleted
     * @throws IllegalArgumentException if the staff member is not found
     */
    public void deleteStaff(Staff member) throws SQLException {
        if (member == null) {
            throw new IllegalArgumentException(
                "Select a staff member to delete"
            );
        }
        if (!database.deleteStaff(member.getId())) {
            throw new IllegalArgumentException("Staff member was not found");
        }

        if (member.getRole().equals("Vet")) {
            for (int i = appointments.size() - 1; i >= 0; i--) {
                if (appointments.get(i).getVeterinarian().getId()
                        == member.getId()) {
                    appointments.remove(i);
                }
            }
        }
        staff.remove(member);
        Staff.decreaseStaffCount();
    }

    /**
     * Deletes a pet and its appointments.
     *
     * @param pet the pet to delete
     * @throws SQLException if the pet cannot be deleted
     * @throws IllegalArgumentException if the pet is not found
     */
    public void deletePet(Pet pet) throws SQLException {
        if (pet == null) {
            throw new IllegalArgumentException("Select a pet to delete");
        }
        if (!database.deletePet(pet.getId())) {
            throw new IllegalArgumentException("Pet was not found");
        }

        for (int i = appointments.size() - 1; i >= 0; i--) {
            if (appointments.get(i).getPet().getId() == pet.getId()) {
                appointments.remove(i);
            }
        }
        pet.getOwner().removePet(pet);
        pets.remove(pet);
    }

    /**
     * Deletes an owner, their pets, and their appointments.
     *
     * @param owner the owner to delete
     * @throws SQLException if the owner cannot be deleted
     * @throws IllegalArgumentException if the owner is not found
     */
    public void deleteOwner(Owner owner) throws SQLException {
        if (owner == null) {
            throw new IllegalArgumentException("Select an owner to delete");
        }
        if (!database.deleteOwner(owner.getId())) {
            throw new IllegalArgumentException("Owner was not found");
        }

        for (int i = appointments.size() - 1; i >= 0; i--) {
            if (appointments.get(i).getPet().getOwner().getId()
                    == owner.getId()) {
                appointments.remove(i);
            }
        }
        for (int i = pets.size() - 1; i >= 0; i--) {
            if (pets.get(i).getOwner().getId() == owner.getId()) {
                pets.remove(i);
            }
        }
        owners.remove(owner);
        Owner.decreaseOwnerCount();
    }

    // Checks basic pet information.
    private void validatePet(String name, int age, Owner owner) {
        validateName(name, "Pet name");
        if (age < 0) {
            throw new IllegalArgumentException("Pet age cannot be negative");
        }
        if (owner == null) {
            throw new IllegalArgumentException("Select an owner");
        }
    }

    // Checks a person or pet name.
    private void validateName(String value, String fieldName) {
        validateText(value, fieldName);
        String name = value.trim();
        boolean hasLetter = false;

        for (int i = 0; i < name.length(); i++) {
            char letter = name.charAt(i);
            if (Character.isLetter(letter)) {
                hasLetter = true;
            } else if (letter != ' ' && letter != '.'
                    && letter != '-' && letter != '\'') {
                throw new IllegalArgumentException(
                    fieldName + " contains an invalid character"
                );
            }
        }
        if (!hasLetter) {
            throw new IllegalArgumentException(
                fieldName + " must contain letters"
            );
        }
    }

    // Checks a phone number.
    private void validatePhone(String phone) {
        validateText(phone, "Phone number");
        String value = phone.trim();

        int digitCount = 0;
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            if (Character.isDigit(character)) {
                digitCount++;
            } else if (character != ' ' && character != '+' && character != '-'
                    && character != '(' && character != ')') {
                throw new IllegalArgumentException(
                    "Phone number contains an invalid character"
                );
            }
        }
        if (digitCount < 7 || digitCount > 15) {
            throw new IllegalArgumentException(
                "Phone number must contain 7 to 15 digits"
            );
        }
    }

    // Checks a basic email address.
    private void validateEmail(String email) {
        validateText(email, "Email");
        String value = email.trim();
        int atPosition = value.indexOf('@');
        int dotPosition = value.lastIndexOf('.');

        if (atPosition <= 0 || dotPosition <= atPosition + 1
                || dotPosition == value.length() - 1
                || atPosition != value.lastIndexOf('@')
                || value.indexOf(' ') >= 0) {
            throw new IllegalArgumentException("Enter a valid email address");
        }
    }

    // Checks required text.
    private void validateText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
    }
}
