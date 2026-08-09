package edu.ucalgary.oop;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

/** Provides a simple command-line interface for the clinic. */
public class ClinicCLI {
    private final Clinic clinic;
    private final Scanner scanner;

    /** Creates the clinic command-line interface. */
    public ClinicCLI(Clinic clinic) {
        if (clinic == null) {
            throw new IllegalArgumentException("Clinic cannot be null");
        }
        this.clinic = clinic;
        scanner = new Scanner(System.in);
    }

    /** Runs the program until the user chooses to exit. */
    public void run() {
        boolean running = true;
        System.out.println("\nPaws & Care Veterinary Clinic");

        while (running) {
            printMenu();
            String choice = readLine("Choose an option: ");

            try {
                if (choice.equals("1")) {
                    showStaff();
                } else if (choice.equals("2")) {
                    addStaff();
                } else if (choice.equals("3")) {
                    deleteStaff();
                } else if (choice.equals("4")) {
                    showOwners();
                } else if (choice.equals("5")) {
                    addOwner();
                } else if (choice.equals("6")) {
                    deleteOwner();
                } else if (choice.equals("7")) {
                    showPets();
                } else if (choice.equals("8")) {
                    addPet();
                } else if (choice.equals("9")) {
                    deletePet();
                } else if (choice.equals("10")) {
                    showAppointments();
                } else if (choice.equals("11")) {
                    scheduleAppointment();
                } else if (choice.equals("12")) {
                    cancelAppointment();
                } else if (choice.equals("0")) {
                    running = false;
                } else {
                    System.out.println("Enter a number from the menu.");
                }
            } catch (AppointmentConflictException exception) {
                showError(exception.getMessage());
            } catch (DailyAppointmentLimitException exception) {
                showError(exception.getMessage());
            } catch (NumberFormatException exception) {
                showError("Enter a whole number.");
            } catch (IllegalArgumentException exception) {
                showError(exception.getMessage());
            } catch (SQLException exception) {
                showError("Database error: " + exception.getMessage());
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    // Displays the main menu.
    private void printMenu() {
        System.out.println("\n------------------------------");
        System.out.println("1. View staff");
        System.out.println("2. Add staff");
        System.out.println("3. Delete staff");
        System.out.println("4. View owners");
        System.out.println("5. Add owner");
        System.out.println("6. Delete owner");
        System.out.println("7. View pets");
        System.out.println("8. Add pet");
        System.out.println("9. Delete pet");
        System.out.println("10. View appointments");
        System.out.println("11. Schedule appointment");
        System.out.println("12. Cancel appointment");
        System.out.println("0. Exit");
    }

    // Displays all staff members.
    private void showStaff() {
        System.out.println("\nSTAFF (" + Staff.getStaffCount() + ")");
        for (Staff member : clinic.getStaff()) {
            System.out.println(member);
        }
    }

    // Adds a veterinarian or receptionist.
    private void addStaff() throws SQLException {
        String role = readLine(
            "Enter V for veterinarian or R for receptionist: "
        );
        if (!role.equalsIgnoreCase("V") && !role.equalsIgnoreCase("R")) {
            throw new IllegalArgumentException("Staff role must be V or R");
        }

        String name = readLine("Staff name: ");

        if (role.equalsIgnoreCase("V")) {
            String specialization = readLine("Specialization: ");
            clinic.registerVeterinarian(name, specialization);
        } else {
            clinic.registerReceptionist(name);
        }
        System.out.println("Staff member registered.");
    }

    // Deletes a staff member selected by ID.
    private void deleteStaff() throws SQLException {
        showStaff();
        Staff member = findStaff(readInt("Staff ID to delete: "));
        String warning = "Delete " + member.getName();
        if (member.getRole().equals("Vet")) {
            warning += " and their appointments";
        }

        if (readYesNo(warning + "?")) {
            clinic.deleteStaff(member);
            System.out.println("Staff member deleted.");
        }
    }

    // Displays all owners.
    private void showOwners() {
        System.out.println("\nOWNERS (" + Owner.getOwnerCount() + ")");
        for (Owner owner : clinic.getOwners()) {
            System.out.println(owner + " - Pets: " + owner.getPets().size());
        }
    }

    // Adds an owner.
    private void addOwner() throws SQLException {
        String name = readLine("Owner name: ");
        String phone = readLine("Phone number: ");
        String email = readLine("Email address: ");
        clinic.registerOwner(name, phone, email);
        System.out.println("Owner registered.");
    }

    // Deletes an owner selected by ID.
    private void deleteOwner() throws SQLException {
        showOwners();
        Owner owner = findOwner(readInt("Owner ID to delete: "));
        String warning = "Delete " + owner.getName()
            + " and all of their pets and appointments?";

        if (readYesNo(warning)) {
            clinic.deleteOwner(owner);
            System.out.println("Owner deleted.");
        }
    }

    // Displays all pets.
    private void showPets() {
        System.out.println("\nPETS (" + clinic.getPets().size() + ")");
        for (Pet pet : clinic.getPets()) {
            System.out.println(pet);
        }
    }

    // Adds a dog or cat.
    private void addPet() throws SQLException {
        showOwners();
        Owner owner = findOwner(readInt("Owner ID: "));
        String species = readLine("Enter D for dog or C for cat: ");
        if (!species.equalsIgnoreCase("D")
                && !species.equalsIgnoreCase("C")) {
            throw new IllegalArgumentException("Pet species must be D or C");
        }

        String name = readLine("Pet name: ");
        int age = readInt("Pet age: ");

        if (species.equalsIgnoreCase("D")) {
            boolean vaccinated = readYesNo("Is the dog vaccinated?");
            clinic.registerDog(name, age, owner, vaccinated);
        } else {
            boolean indoor = readYesNo("Is the cat an indoor cat?");
            clinic.registerCat(name, age, owner, indoor);
        }
        System.out.println("Pet registered.");
    }

    // Deletes a pet selected by ID.
    private void deletePet() throws SQLException {
        showPets();
        Pet pet = findPet(readInt("Pet ID to delete: "));
        String warning = "Delete " + pet.getName() + " and its appointments?";

        if (readYesNo(warning)) {
            clinic.deletePet(pet);
            System.out.println("Pet deleted.");
        }
    }

    // Displays all appointments.
    private void showAppointments() {
        System.out.println("\nAPPOINTMENTS");
        System.out.println("Daily limit per veterinarian: "
            + Clinic.MAX_DAILY_APPOINTMENTS);
        for (Appointment appointment : clinic.getAppointments()) {
            System.out.println(appointment + " - Notes: "
                + appointment.getNotes());
        }
    }

    // Schedules an appointment.
    private void scheduleAppointment() throws SQLException,
                                              AppointmentConflictException,
                                              DailyAppointmentLimitException {
        showPets();
        Pet pet = findPet(readInt("Pet ID: "));
        showVeterinarians();
        Veterinarian veterinarian = findVeterinarian(
            readInt("Veterinarian ID: ")
        );

        String dateText = readLine("Date and time (yyyy-mm-dd hh:mm): ");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateText.trim().replace(" ", "T"));
        } catch (Exception exception) {
            throw new IllegalArgumentException(
                "Use date format yyyy-mm-dd hh:mm"
            );
        }

        String notes = readLine("Appointment notes (optional): ");
        clinic.scheduleAppointment(pet, veterinarian, dateTime, notes);
        System.out.println("Appointment scheduled.");
    }

    // Cancels an appointment selected by ID.
    private void cancelAppointment() throws SQLException {
        showAppointments();
        Appointment appointment = findAppointment(
            readInt("Appointment ID to cancel: ")
        );

        if (readYesNo("Cancel this appointment?")) {
            clinic.cancelAppointment(appointment);
            System.out.println("Appointment cancelled.");
        }
    }

    // Displays all veterinarians.
    private void showVeterinarians() {
        System.out.println("\nVETERINARIANS");
        for (Veterinarian veterinarian : clinic.getVeterinarians()) {
            System.out.println(veterinarian);
        }
    }

    // Finds a staff member by ID.
    private Staff findStaff(int id) {
        for (Staff member : clinic.getStaff()) {
            if (member.getId() == id) {
                return member;
            }
        }
        throw new IllegalArgumentException("Staff member was not found");
    }

    // Finds an owner by ID.
    private Owner findOwner(int id) {
        for (Owner owner : clinic.getOwners()) {
            if (owner.getId() == id) {
                return owner;
            }
        }
        throw new IllegalArgumentException("Owner was not found");
    }

    // Finds a pet by ID.
    private Pet findPet(int id) {
        for (Pet pet : clinic.getPets()) {
            if (pet.getId() == id) {
                return pet;
            }
        }
        throw new IllegalArgumentException("Pet was not found");
    }

    // Finds a veterinarian by ID.
    private Veterinarian findVeterinarian(int id) {
        for (Veterinarian veterinarian : clinic.getVeterinarians()) {
            if (veterinarian.getId() == id) {
                return veterinarian;
            }
        }
        throw new IllegalArgumentException("Veterinarian was not found");
    }

    // Finds an appointment by ID.
    private Appointment findAppointment(int id) {
        for (Appointment appointment : clinic.getAppointments()) {
            if (appointment.getId() == id) {
                return appointment;
            }
        }
        throw new IllegalArgumentException("Appointment was not found");
    }

    // Reads one line of user input.
    private String readLine(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    // Reads a whole number.
    private int readInt(String message) {
        return Integer.parseInt(readLine(message));
    }

    // Reads a yes or no answer.
    private boolean readYesNo(String message) {
        String answer = readLine(message + " (y/n): ");
        if (answer.equalsIgnoreCase("y")) {
            return true;
        }
        if (answer.equalsIgnoreCase("n")) {
            return false;
        }
        throw new IllegalArgumentException("Enter y or n");
    }

    // Displays an error without closing the program.
    private void showError(String message) {
        System.out.println("Error: " + message);
    }
}
