package edu.ucalgary.oop;

import java.sql.SQLException;

/** Starts the veterinary clinic program. */
public class Main {
    /** Loads the database and starts the command-line interface. */
    public static void main(String[] args) {
        try {
            DatabaseManager database = new DatabaseManager();
            Clinic clinic = new Clinic(database);
            ClinicCLI cli = new ClinicCLI(clinic);
            cli.run();
        } catch (SQLException exception) {
            System.out.println(
                "Could not connect to the database: " + exception.getMessage()
            );
        }
    }
}
