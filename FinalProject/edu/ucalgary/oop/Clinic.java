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
            throw new IllegalArgumentException(
                "Database manager cannot be null"
            );
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
            if (member.getRole().equals("Vet")) {
                veterinarians.add((Veterinarian) member);
            }
        }
        return veterinarians;
    }

    /** Registers and stores a veterinarian. */
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

    /** Registers and stores a receptionist. */
    public Receptionist registerReceptionist(String name) throws SQLException {
        validateName(name, "Staff name");
        int id = database.insertReceptionist(name.trim());
        Receptionist receptionist = new Receptionist(id, name);
        staff.add(receptionist);
        return receptionist;
    }

    /** Registers and stores an owner. */
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

    /** Registers and stores a dog. */
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

    /** Cancels an appointment. */
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

    /** Deletes a staff member and their appointments. */
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

    /** Deletes a pet and its appointments. */
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

    /** Deletes an owner, their pets, and their appointments. */
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
