package edu.ucalgary.oop;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** Provides the clinic's Swing user interface. */
public class ClinicGUI implements ActionListener {
    private static final DateTimeFormatter INPUT_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Clinic clinic;
    private final JFrame frame = new JFrame("Veterinary Clinic");

    private final JTextArea staffArea = makeTextArea();
    private final JTextArea ownerArea = makeTextArea();
    private final JTextArea petArea = makeTextArea();
    private final JTextArea appointmentArea = makeTextArea();

    private final JTextField ownerNameField = new JTextField();
    private final JTextField ownerPhoneField = new JTextField();
    private final JTextField ownerEmailField = new JTextField();
    private final JButton addOwnerButton = new JButton("Register Owner");

    private final JComboBox<Owner> petOwnerBox = new JComboBox<>();
    private final JComboBox<String> speciesBox =
        new JComboBox<>(new String[] {"Dog", "Cat"});
    private final JTextField petNameField = new JTextField();
    private final JTextField petAgeField = new JTextField();
    private final JLabel petOptionLabel = new JLabel("Vaccinated:");
    private final JCheckBox petOptionBox = new JCheckBox();
    private final JButton addPetButton = new JButton("Register Pet");

    private final JComboBox<Pet> appointmentPetBox = new JComboBox<>();
    private final JComboBox<Veterinarian> veterinarianBox = new JComboBox<>();
    private final JTextField dateTimeField = new JTextField("yyyy-MM-dd HH:mm");
    private final JTextField notesField = new JTextField();
    private final JButton scheduleButton = new JButton("Schedule Appointment");
    private final JComboBox<Appointment> cancelBox = new JComboBox<>();
    private final JButton cancelButton = new JButton("Cancel Appointment");

    /** Builds the clinic window. */
    public ClinicGUI(Clinic clinic) {
        if (clinic == null) {
            throw new IllegalArgumentException("Clinic cannot be null");
        }
        this.clinic = clinic;
        buildWindow();
        addListeners();
        refreshDisplay();
    }

    /** Displays a staff selection and opens the window. */
    public void showGUI() {
        Staff selectedStaff = chooseStaffMember();
        if (selectedStaff != null) {
            frame.setTitle("Veterinary Clinic - " + selectedStaff.getName());
            frame.setVisible(true);
        }
    }

    /** Handles button and species events. */
    public void actionPerformed(ActionEvent event) {
        try {
            Object source = event.getSource();

            if (source == addOwnerButton) {
                addOwner();
            } else if (source == addPetButton) {
                addPet();
            } else if (source == scheduleButton) {
                scheduleAppointment();
            } else if (source == cancelButton) {
                cancelAppointment();
            } else if (source == speciesBox) {
                updatePetOption();
                return;
            }

            refreshDisplay();
        } catch (AppointmentConflictException exception) {
            showError(exception.getMessage());
        } catch (DailyAppointmentLimitException exception) {
            showError(exception.getMessage());
        } catch (NumberFormatException exception) {
            showError("Pet age must be a whole number");
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        } catch (SQLException exception) {
            showError("Database error: " + exception.getMessage());
        }
    }

    /** Builds the main frame and tabs. */
    private void buildWindow() {
        frame.setSize(850, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Staff", buildStaffPanel());
        tabs.addTab("Owners", buildOwnerPanel());
        tabs.addTab("Pets", buildPetPanel());
        tabs.addTab("Appointments", buildAppointmentPanel());
        frame.add(tabs, BorderLayout.CENTER);
    }

    /** Builds the staff tab. */
    private JPanel buildStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(staffArea), BorderLayout.CENTER);
        return panel;
    }

    /** Builds the owner tab. */
    private JPanel buildOwnerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.add(new JLabel("Name:"));
        form.add(ownerNameField);
        form.add(new JLabel("Phone:"));
        form.add(ownerPhoneField);
        form.add(new JLabel("Email:"));
        form.add(ownerEmailField);
        form.add(new JLabel());
        form.add(addOwnerButton);
        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(ownerArea), BorderLayout.CENTER);
        return panel;
    }

    /** Builds the pet tab. */
    private JPanel buildPetPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.add(new JLabel("Owner:"));
        form.add(petOwnerBox);
        form.add(new JLabel("Species:"));
        form.add(speciesBox);
        form.add(new JLabel("Name:"));
        form.add(petNameField);
        form.add(new JLabel("Age:"));
        form.add(petAgeField);
        form.add(petOptionLabel);
        form.add(petOptionBox);
        form.add(new JLabel());
        form.add(addPetButton);
        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(petArea), BorderLayout.CENTER);
        return panel;
    }

    /** Builds the appointment tab. */
    private JPanel buildAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.add(new JLabel("Pet:"));
        form.add(appointmentPetBox);
        form.add(new JLabel("Veterinarian:"));
        form.add(veterinarianBox);
        form.add(new JLabel("Date and time:"));
        form.add(dateTimeField);
        form.add(new JLabel("Notes:"));
        form.add(notesField);
        form.add(new JLabel());
        form.add(scheduleButton);
        form.add(cancelBox);
        form.add(cancelButton);
        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(appointmentArea), BorderLayout.CENTER);
        return panel;
    }

    /** Registers component listeners. */
    private void addListeners() {
        addOwnerButton.addActionListener(this);
        addPetButton.addActionListener(this);
        scheduleButton.addActionListener(this);
        cancelButton.addActionListener(this);
        speciesBox.addActionListener(this);
    }

    /** Registers an owner from the form. */
    private void addOwner() throws SQLException {
        clinic.registerOwner(
            ownerNameField.getText(), ownerPhoneField.getText(),
            ownerEmailField.getText()
        );
        ownerNameField.setText("");
        ownerPhoneField.setText("");
        ownerEmailField.setText("");
        showMessage("Owner registered");
    }

    /** Registers a pet from the form. */
    private void addPet() throws SQLException {
        Owner owner = (Owner) petOwnerBox.getSelectedItem();
        int age = Integer.parseInt(petAgeField.getText().trim());
        String species = (String) speciesBox.getSelectedItem();

        if (species.equals("Dog")) {
            clinic.registerDog(
                petNameField.getText(), age, owner, petOptionBox.isSelected()
            );
        } else {
            clinic.registerCat(
                petNameField.getText(), age, owner, petOptionBox.isSelected()
            );
        }

        petNameField.setText("");
        petAgeField.setText("");
        petOptionBox.setSelected(false);
        showMessage("Pet registered");
    }

    /** Schedules an appointment from the form. */
    private void scheduleAppointment() throws SQLException,
                                              AppointmentConflictException,
                                              DailyAppointmentLimitException {
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(
                dateTimeField.getText().trim(), INPUT_FORMAT
            );
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Use date format yyyy-MM-dd HH:mm");
        }

        clinic.scheduleAppointment(
            (Pet) appointmentPetBox.getSelectedItem(),
            (Veterinarian) veterinarianBox.getSelectedItem(),
            dateTime, notesField.getText()
        );
        dateTimeField.setText("yyyy-MM-dd HH:mm");
        notesField.setText("");
        showMessage("Appointment scheduled");
    }

    /** Cancels the selected appointment. */
    private void cancelAppointment() throws SQLException {
        Appointment appointment = (Appointment) cancelBox.getSelectedItem();
        if (appointment == null) {
            throw new IllegalArgumentException("There are no appointments to cancel");
        }
        int answer = JOptionPane.showConfirmDialog(
            frame, "Cancel the selected appointment?", "Confirm",
            JOptionPane.YES_NO_OPTION
        );
        if (answer == JOptionPane.YES_OPTION) {
            clinic.cancelAppointment(appointment);
            showMessage("Appointment cancelled");
        }
    }

    /** Refreshes lists and selection boxes. */
    private void refreshDisplay() {
        refreshStaff();
        refreshOwners();
        refreshPets();
        refreshAppointments();
    }

    /** Refreshes the staff display. */
    private void refreshStaff() {
        staffArea.setText("Total staff: " + Staff.getStaffCount() + "\n\n");
        for (Staff member : clinic.getStaff()) {
            staffArea.append(member + "\n");
        }
    }

    /** Refreshes owner controls. */
    private void refreshOwners() {
        Owner selected = (Owner) petOwnerBox.getSelectedItem();
        ownerArea.setText("Total owners: " + Owner.getOwnerCount() + "\n\n");
        petOwnerBox.removeAllItems();

        for (Owner owner : clinic.getOwners()) {
            ownerArea.append(owner + " - Pets: " + owner.getPets().size() + "\n");
            petOwnerBox.addItem(owner);
        }
        if (selected != null) {
            petOwnerBox.setSelectedItem(selected);
        }
    }

    /** Refreshes pet controls. */
    private void refreshPets() {
        Pet selected = (Pet) appointmentPetBox.getSelectedItem();
        petArea.setText("Total pets: " + clinic.getPets().size() + "\n\n");
        appointmentPetBox.removeAllItems();

        for (Pet pet : clinic.getPets()) {
            petArea.append(pet + "\n");
            appointmentPetBox.addItem(pet);
        }
        if (selected != null) {
            appointmentPetBox.setSelectedItem(selected);
        }

        veterinarianBox.removeAllItems();
        for (Veterinarian veterinarian : clinic.getVeterinarians()) {
            veterinarianBox.addItem(veterinarian);
        }
    }

    /** Refreshes appointment controls. */
    private void refreshAppointments() {
        appointmentArea.setText(
            "Daily limit per veterinarian: "
                + Clinic.MAX_DAILY_APPOINTMENTS + "\n\n"
        );
        cancelBox.removeAllItems();

        for (Appointment appointment : clinic.getAppointments()) {
            appointmentArea.append(
                appointment + " - Notes: " + appointment.getNotes() + "\n"
            );
            cancelBox.addItem(appointment);
        }
    }

    /** Changes the dog or cat option label. */
    private void updatePetOption() {
        String species = (String) speciesBox.getSelectedItem();
        if (species != null && species.equals("Cat")) {
            petOptionLabel.setText("Indoor:");
        } else {
            petOptionLabel.setText("Vaccinated:");
        }
        petOptionBox.setSelected(false);
    }

    /** Lets the user select the current staff member. */
    private Staff chooseStaffMember() {
        JList<Staff> staffList = new JList<>(
            clinic.getStaff().toArray(new Staff[0])
        );
        staffList.setSelectedIndex(0);
        int result = JOptionPane.showConfirmDialog(
            null, new JScrollPane(staffList), "Select Staff Member",
            JOptionPane.OK_CANCEL_OPTION
        );
        if (result == JOptionPane.OK_OPTION) {
            return staffList.getSelectedValue();
        }
        return null;
    }

    /** Creates a read-only text area. */
    private static JTextArea makeTextArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        return area;
    }

    /** Displays a normal message. */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    /** Displays an error message. */
    private void showError(String message) {
        JOptionPane.showMessageDialog(
            frame, message, "Error", JOptionPane.ERROR_MESSAGE
        );
    }
}
