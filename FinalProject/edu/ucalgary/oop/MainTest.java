package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainTest {
    private Clinic clinic;
    private Veterinarian vet;
    private Pet pet;

    @Before
    public void setUp() throws Exception {
        clinic = new Clinic(new DatabaseManager());
        vet = clinic.getVeterinarians().get(0);
        pet = clinic.getPets().get(0);
    }

    @Test
    public void testScheduleAndCancelAppointment() throws Exception {
        LocalDateTime future = LocalDateTime.now().plusDays(30).withMinute(0).withSecond(0).withNano(0);
        Appointment appt = clinic.scheduleAppointment(pet, vet, future, "checkup");

        assertTrue(clinic.getAppointments().contains(appt));
        //remove appointment
        clinic.cancelAppointment(appt);
        assertFalse(clinic.getAppointments().contains(appt));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPastDate() throws Exception {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(30);
        clinic.scheduleAppointment(pet, vet, pastDate, "checkup");
    }

    @Test(expected = AppointmentConflictException.class)
    public void testDoubleBooking() throws Exception {
        LocalDateTime time = LocalDateTime.now().plusDays(30).withMinute(0).withSecond(0).withNano(0);
        Appointment appt1 = clinic.scheduleAppointment(pet, vet, time, "one");
        try {
            clinic.scheduleAppointment(pet, vet, time, "two");
        } finally {
            clinic.cancelAppointment(appt1);
        }

    }

    @Test(expected = DailyAppointmentLimitException.class)
    public void testBookingOver8Appointments() throws Exception {
        LocalDateTime day = LocalDateTime.now().plusDays(30).withHour(9).withMinute(0).withSecond(0).withNano(0);
        ArrayList<Appointment> created = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            created.add(clinic.scheduleAppointment(pet, vet, day.plusHours(i), "n" + i));
        }
        try {
            clinic.scheduleAppointment(pet, vet, day.plusHours(8), "one too many");
        } finally {
            for (Appointment a : created) clinic.cancelAppointment(a);
        }
    }

    @Test
    public void testEnteringNonexistantPet() {
        // menu choice, bad pet id, then exit
        String input = String.join("\n", "11", "1000", "0") + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        new ClinicCLI(clinic).run();
        System.setOut(System.out);
        assertTrue(out.toString().contains("Pet was not found"));
    }

    @Test
    public void testEnteringNonexistantVet() {
        // menu choice, valid pet id, bad vet id, then exit
        String input = String.join("\n", "11", "1", "1000", "0") + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        new ClinicCLI(clinic).run();
        System.setOut(System.out);
        assertTrue(out.toString().contains("Veterinarian was not found"));
    }

    @Test
    public void testEnteringGarbageDate() {
        // menu choice, valid pet id, valid vet id, garbage date, then exit
        String input = String.join("\n", "11", "1", "1", "wawawawawa", "0") + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        new ClinicCLI(clinic).run();
        System.setOut(System.out);
        assertTrue(out.toString().contains("Use date format yyyy-mm-dd hh:mm"));
    }
}
