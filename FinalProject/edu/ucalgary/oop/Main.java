package edu.ucalgary.oop;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/** Starts the veterinary clinic program. */
public class Main {
    /** Loads the database and opens the GUI. */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DatabaseManager database = new DatabaseManager();
                Clinic clinic = new Clinic(database);
                ClinicGUI gui = new ClinicGUI(clinic);
                gui.showGUI();
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(
                    null,
                    "Could not connect to the database:\n" + exception.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
