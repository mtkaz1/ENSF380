package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Loads and changes clinic data with JDBC. */
public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/vet_clinic";
    private static final String USERNAME = "oop";
    private static final String PASSWORD = "ucalgary";

    /** Opens a database connection. */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /** Loads all staff members. */
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

    /** Loads all owners. */
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

    /** Loads all pets and connects them to owners. */
    public ArrayList<Pet> loadPets(ArrayList<Owner> owners) throws SQLException {
        ArrayList<Pet> pets = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(
                "SELECT id, name, age, species, owner_id, is_vaccinated, is_indoor "
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

    /** Loads all appointments and connects their objects. */
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

    /** Inserts an owner and returns its new ID. */
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

    /** Inserts a dog and returns its new ID. */
    public int insertDog(String name, int age, int ownerId, boolean vaccinated)
                         throws SQLException {
        String sql = "INSERT INTO pets "
            + "(name, age, species, owner_id, is_vaccinated, is_indoor) "
            + "VALUES (?, ?, 'Dog', ?, ?, NULL) RETURNING id";
        return insertPet(sql, name, age, ownerId, vaccinated);
    }

    /** Inserts a cat and returns its new ID. */
    public int insertCat(String name, int age, int ownerId, boolean indoor)
                         throws SQLException {
        String sql = "INSERT INTO pets "
            + "(name, age, species, owner_id, is_vaccinated, is_indoor) "
            + "VALUES (?, ?, 'Cat', ?, NULL, ?) RETURNING id";
        return insertPet(sql, name, age, ownerId, indoor);
    }

    /** Inserts an appointment and returns its new ID. */
    public int insertAppointment(int petId, int vetId, LocalDateTime dateTime,
                                 String notes) throws SQLException {
        String sql = "INSERT INTO appointments (pet_id, vet_id, date_time, notes) "
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

    /** Deletes an appointment. */
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

    /** Inserts a pet shared by dog and cat methods. */
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

    /** Finds an owner by ID. */
    private Owner findOwner(ArrayList<Owner> owners, int id) throws SQLException {
        for (Owner owner : owners) {
            if (owner.getId() == id) {
                return owner;
            }
        }
        throw new SQLException("Owner " + id + " was not found");
    }

    /** Finds a pet by ID. */
    private Pet findPet(ArrayList<Pet> pets, int id) throws SQLException {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                return pet;
            }
        }
        throw new SQLException("Pet " + id + " was not found");
    }

    /** Finds a veterinarian by ID. */
    private Veterinarian findVeterinarian(ArrayList<Staff> staff, int id)
                                          throws SQLException {
        for (Staff member : staff) {
            if (member.getId() == id && member instanceof Veterinarian) {
                return (Veterinarian) member;
            }
        }
        throw new SQLException("Veterinarian " + id + " was not found");
    }

    /** Closes JDBC resources. */
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
