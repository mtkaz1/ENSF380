package edu.ucalgary.oop;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/** Starts the Paws &amp; Care Veterinary Clinic program. */
public class Main {
    /**
     * Loads the database and starts the command-line interface.
     *
     * @param args command-line arguments; not used
     */
    public static void main(String[] args) {
        try {
            DatabaseManager database = new DatabaseManager();
            Clinic clinic = new Clinic(database);
            ClinicCLI cli = new ClinicCLI(clinic);
            cli.run();
        } catch (FileNotFoundException exception) {
            System.out.println(
                "Could not find db.properties. Run the program from "
                    + "the FinalProject folder."
            );
        } catch (IllegalArgumentException exception) {
            System.out.println(
                "Database settings error: " + exception.getMessage()
            );
        } catch (SQLException exception) {
            System.out.println(
                "Could not connect to the database: " + exception.getMessage()
            );
        }
    }
}
