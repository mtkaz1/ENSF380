package edu.ucalgary.oop;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/** Loads and changes PostgreSQL clinic data using JDBC. */
public class DatabaseManager {
    private final String url;
    private final String username;
    private final String password;

    /**
     * Loads the database settings from {@code db.properties}.
     *
     * @throws FileNotFoundException if {@code db.properties} is missing
     * @throws IllegalArgumentException if a database setting is invalid
     */
    public DatabaseManager() throws FileNotFoundException {
        Scanner configFile = new Scanner(new File("db.properties"));
        url = readSetting(configFile);
        username = readSetting(configFile);
        password = readSetting(configFile);
        configFile.close();
    }

    // Opens a database connection.
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Reads the value after an equals sign.
    private String readSetting(Scanner configFile) {
        if (!configFile.hasNextLine()) {
            throw new IllegalArgumentException(
                "db.properties is missing a database setting"
            );
        }

        String line = configFile.nextLine();
        int equalsPosition = line.indexOf('=');
        if (equalsPosition < 0) {
            throw new IllegalArgumentException(
                "Each db.properties line needs an equals sign"
            );
        }
        return line.substring(equalsPosition + 1).trim();
    }

    /**
     * Loads all staff members from the database.
     *
     * @return the loaded staff members
     * @throws SQLException if the data cannot be loaded
     */
    public ArrayList<Staff> loadStaff() throws SQLException {
        ArrayList<Staff> staff = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT id, name, role, specialization FROM staff ORDER BY id"
            );
            results = statement.executeQuery();

            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                String role = results.getString("role");

                if (role.equals("Vet")) {
                    staff.add(new Veterinarian(
                        id, name, results.getString("specialization")
                    ));
                } else {
                    staff.add(new Receptionist(id, name));
                }
            }
        } finally {
            closeResources(results, statement, connection);
        }
        return staff;
    }

    /**
     * Loads all owners from the database.
     *
     * @return the loaded owners
     * @throws SQLException if the data cannot be loaded
     */
    public ArrayList<Owner> loadOwners() throws SQLException {
        ArrayList<Owner> owners = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT id, name, phone, email FROM owners ORDER BY id"
            );
            results = statement.executeQuery();

            while (results.next()) {
                owners.add(new Owner(
                    results.getInt("id"),
                    results.getString("name"),
                    results.getString("phone"),
                    results.getString("email")
                ));
            }
        } finally {
            closeResources(results, statement, connection);
        }
        return owners;
    }

    /**
     * Loads all pets and connects each pet to its owner.
     *
     * @param owners the owners already loaded from the database
     * @return the loaded pets
     * @throws SQLException if the data or an owner cannot be found
     */
    public ArrayList<Pet> loadPets(ArrayList<Owner> owners)
                                   throws SQLException {
        ArrayList<Pet> pets = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT id, name, age, species, owner_id, "
                    + "is_vaccinated, is_indoor "
                    + "FROM pets ORDER BY id"
            );
            results = statement.executeQuery();

            while (results.next()) {
                Owner owner = findOwner(owners, results.getInt("owner_id"));
                Pet pet;

                if (results.getString("species").equals("Dog")) {
                    pet = new Dog(
                        results.getInt("id"), results.getString("name"),
                        results.getInt("age"), owner,
                        results.getBoolean("is_vaccinated")
                    );
                } else {
                    pet = new Cat(
                        results.getInt("id"), results.getString("name"),
                        results.getInt("age"), owner,
                        results.getBoolean("is_indoor")
                    );
                }
                pets.add(pet);
                owner.addPet(pet);
            }
        } finally {
            closeResources(results, statement, connection);
        }
        return pets;
    }

    /**
     * Loads all appointments and connects their pets and veterinarians.
     *
     * @param pets the pets already loaded from the database
     * @param staff the staff already loaded from the database
     * @return the loaded appointments
     * @throws SQLException if the data or a related object cannot be found
     */
    public ArrayList<Appointment> loadAppointments(ArrayList<Pet> pets,
                                                    ArrayList<Staff> staff)
                                                    throws SQLException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT id, pet_id, vet_id, date_time, notes "
                    + "FROM appointments ORDER BY date_time"
            );
            results = statement.executeQuery();

            while (results.next()) {
                Pet pet = findPet(pets, results.getInt("pet_id"));
                Veterinarian vet = findVeterinarian(
                    staff, results.getInt("vet_id")
                );
                appointments.add(new Appointment(
                    results.getInt("id"), pet, vet,
                    results.getTimestamp("date_time").toLocalDateTime(),
                    results.getString("notes")
                ));
            }
        } finally {
            closeResources(results, statement, connection);
        }
        return appointments;
    }

    /**
     * Inserts a veterinarian into the database.
     *
     * @param name the veterinarian's name
     * @param specialization the veterinarian's specialization
     * @return the new database ID
     * @throws SQLException if the veterinarian cannot be added
     */
    public int insertVeterinarian(String name, String specialization)
                                  throws SQLException {
        String sql = "INSERT INTO staff (name, role, specialization) "
            + "VALUES (?, 'Vet', ?) RETURNING id";
        return insertStaff(sql, name, specialization);
    }

    /**
     * Inserts a receptionist into the database.
     *
     * @param name the receptionist's name
     * @return the new database ID
     * @throws SQLException if the receptionist cannot be added
     */
    public int insertReceptionist(String name) throws SQLException {
        String sql = "INSERT INTO staff (name, role, specialization) "
            + "VALUES (?, 'Receptionist', NULL) RETURNING id";
        return insertStaff(sql, name, null);
    }

    /**
     * Inserts an owner into the database.
     *
     * @param name the owner's name
     * @param phone the owner's phone number
     * @param email the owner's email address
     * @return the new database ID
     * @throws SQLException if the owner cannot be added
     */
    public int insertOwner(String name, String phone, String email)
                           throws SQLException {
        String sql = "INSERT INTO owners (name, phone, email) "
            + "VALUES (?, ?, ?) RETURNING id";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, email);
            results = statement.executeQuery();
            results.next();
            return results.getInt("id");
        } finally {
            closeResources(results, statement, connection);
        }
    }

    /**
     * Inserts a dog into the database.
     *
     * @param name the dog's name
     * @param age the dog's age
     * @param ownerId the owner's database ID
     * @param vaccinated whether the dog is vaccinated
     * @return the new database ID
     * @throws SQLException if the dog cannot be added
     */
    public int insertDog(String name, int age, int ownerId, boolean vaccinated)
                         throws SQLException {
        String sql = "INSERT INTO pets "
            + "(name, age, species, owner_id, is_vaccinated, is_indoor) "
            + "VALUES (?, ?, 'Dog', ?, ?, NULL) RETURNING id";
        return insertPet(sql, name, age, ownerId, vaccinated);
    }

    /**
     * Inserts a cat into the database.
     *
     * @param name the cat's name
     * @param age the cat's age
     * @param ownerId the owner's database ID
     * @param indoor whether the cat is an indoor cat
     * @return the new database ID
     * @throws SQLException if the cat cannot be added
     */
    public int insertCat(String name, int age, int ownerId, boolean indoor)
                         throws SQLException {
        String sql = "INSERT INTO pets "
            + "(name, age, species, owner_id, is_vaccinated, is_indoor) "
            + "VALUES (?, ?, 'Cat', ?, NULL, ?) RETURNING id";
        return insertPet(sql, name, age, ownerId, indoor);
    }

    /**
     * Inserts an appointment into the database.
     *
     * @param petId the pet's database ID
     * @param vetId the veterinarian's database ID
     * @param dateTime the appointment date and time
     * @param notes additional appointment notes
     * @return the new database ID
     * @throws SQLException if the appointment cannot be added
     */
    public int insertAppointment(int petId, int vetId, LocalDateTime dateTime,
                                 String notes) throws SQLException {
        String sql = "INSERT INTO appointments "
            + "(pet_id, vet_id, date_time, notes) "
            + "VALUES (?, ?, ?, ?) RETURNING id";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, petId);
            statement.setInt(2, vetId);
            statement.setTimestamp(3, Timestamp.valueOf(dateTime));
            statement.setString(4, notes);
            results = statement.executeQuery();
            results.next();
            return results.getInt("id");
        } finally {
            closeResources(results, statement, connection);
        }
    }

    /**
     * Deletes an appointment from the database.
     *
     * @param appointmentId the appointment ID
     * @return {@code true} if an appointment was deleted
     * @throws SQLException if the delete operation fails
     */
    public boolean deleteAppointment(int appointmentId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "DELETE FROM appointments WHERE id = ?"
            );
            statement.setInt(1, appointmentId);
            return statement.executeUpdate() == 1;
        } finally {
            closeResources(null, statement, connection);
        }
    }

    /**
     * Deletes a staff member from the database.
     *
     * @param staffId the staff member's ID
     * @return {@code true} if a staff member was deleted
     * @throws SQLException if the delete operation fails
     */
    public boolean deleteStaff(int staffId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "DELETE FROM staff WHERE id = ?"
            );
            statement.setInt(1, staffId);
            return statement.executeUpdate() == 1;
        } finally {
            closeResources(null, statement, connection);
        }
    }

    /**
     * Deletes a pet from the database.
     *
     * @param petId the pet's ID
     * @return {@code true} if a pet was deleted
     * @throws SQLException if the delete operation fails
     */
    public boolean deletePet(int petId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "DELETE FROM pets WHERE id = ?"
            );
            statement.setInt(1, petId);
            return statement.executeUpdate() == 1;
        } finally {
            closeResources(null, statement, connection);
        }
    }

    /**
     * Deletes an owner from the database.
     *
     * @param ownerId the owner's ID
     * @return {@code true} if an owner was deleted
     * @throws SQLException if the delete operation fails
     */
    public boolean deleteOwner(int ownerId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "DELETE FROM owners WHERE id = ?"
            );
            statement.setInt(1, ownerId);
            return statement.executeUpdate() == 1;
        } finally {
            closeResources(null, statement, connection);
        }
    }

    // Inserts a pet shared by dog and cat methods.
    private int insertPet(String sql, String name, int age, int ownerId,
                          boolean specialValue) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setInt(3, ownerId);
            statement.setBoolean(4, specialValue);
            results = statement.executeQuery();
            results.next();
            return results.getInt("id");
        } finally {
            closeResources(results, statement, connection);
        }
    }

    // Inserts a staff member shared by both staff types.
    private int insertStaff(String sql, String name, String specialization)
                            throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            if (specialization != null) {
                statement.setString(2, specialization);
            }
            results = statement.executeQuery();
            results.next();
            return results.getInt("id");
        } finally {
            closeResources(results, statement, connection);
        }
    }

    // Finds an owner by ID.
    private Owner findOwner(ArrayList<Owner> owners, int id)
                            throws SQLException {
        for (Owner owner : owners) {
            if (owner.getId() == id) {
                return owner;
            }
        }
        throw new SQLException("Owner " + id + " was not found");
    }

    // Finds a pet by ID.
    private Pet findPet(ArrayList<Pet> pets, int id) throws SQLException {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                return pet;
            }
        }
        throw new SQLException("Pet " + id + " was not found");
    }

    // Finds a veterinarian by ID.
    private Veterinarian findVeterinarian(ArrayList<Staff> staff, int id)
                                          throws SQLException {
        for (Staff member : staff) {
            if (member.getId() == id && member.getRole().equals("Vet")) {
                return (Veterinarian) member;
            }
        }
        throw new SQLException("Veterinarian " + id + " was not found");
    }

    // Closes JDBC resources.
    private void closeResources(ResultSet results, PreparedStatement statement,
                                Connection connection) throws SQLException {
        if (results != null) {
            results.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
