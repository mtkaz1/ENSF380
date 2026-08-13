package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppointmentTest {
    private Clinic clinic;
    private Veterinarian vet;
    private Pet pet;
    private final ArrayList<Appointment> createdAppointments =
            new ArrayList<>();

    @Before
    public void setUp() {
        Owner owner = new Owner(
            1, "jim", "000-000-6767", "jimmy@gmail.com"
        );
        pet = new Dog(1, "Bud", 5, owner, true);
        vet = new Veterinarian(1, "Dr. jim", "General Practice");
    }

    private void setUpClinic() throws Exception {
        clinic = new Clinic(new DatabaseManager());
        vet = clinic.getVeterinarians().get(0);
        pet = clinic.getPets().get(0);
    }

    private Appointment scheduleTestAppointment(Pet pet, Veterinarian vet, 
        LocalDateTime dateTime, String notes) throws Exception {

        Appointment appointment = clinic.scheduleAppointment(
            pet, vet, dateTime, notes
        );

        createdAppointments.add(appointment);
        return appointment;
    }    

    private String runCli(String... inputLines) {
        String input = String.join("\n", inputLines) + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));
            new ClinicCLI(clinic).run();
        } finally {
            System.setOut(originalOut);
        }
        return out.toString();

    }

    @After
    public void cleanUpTestAppointments() throws Exception {
        if (clinic == null) {
            return;
        }
        Exception firstFailure = null;
        for (int i = createdAppointments.size() - 1; i >= 0; i--) {
            Appointment appt = createdAppointments.get(i);

            try {
                if (clinic.getAppointments().contains(appt)) {
                    clinic.cancelAppointment(appt);
                }
            } catch (Exception e) {
                if (firstFailure == null) {
                    firstFailure = e;
                }
            }
        }

        createdAppointments.clear();

        if (firstFailure != null) {
            throw firstFailure;
        }
    }

    @Test
    public void testConstructorStoresFieldsAndTrimsNotes() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(30).withHour(0)
                                    .withMinute(0);
        Appointment appointment = new Appointment(
            101, pet, vet, dateTime, "  Annual checkup  "
        );

        assertEquals(101, appointment.getId());
        assertSame(pet, appointment.getPet());
        assertSame(vet, appointment.getVeterinarian());
        assertEquals(dateTime, appointment.getDateTime());
        assertEquals("Annual checkup", appointment.getNotes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRejectsZeroId() {
        new Appointment(
            0, pet, vet, LocalDateTime.now().plusDays(30).withHour(0)
            .withMinute(0), "Checkup"
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRejectsNegativeId() {
        new Appointment(
            -1, pet, vet, LocalDateTime.now().plusDays(30).withHour(0)
            .withMinute(0), "Checkup"
        );
    }

    @Test
    public void testConstructorConvertsNullNotesToEmptyString() {
        Appointment appointment = new Appointment(
            102, pet, vet, LocalDateTime.now().plusDays(30).withHour(0)
            .withMinute(0), null
        );

        assertEquals("", appointment.getNotes());
    }

    @Test
    public void testSetNotesTrimsWhitespace() {
        Appointment appointment = new Appointment(
            103, pet, vet, LocalDateTime.now().plusDays(30).withHour(0)
            .withMinute(0), "Initial"
        );

        appointment.setNotes("  Follow-up visit  ");

        assertEquals("Follow-up visit", appointment.getNotes());
    }

    @Test
    public void testSetNotesConvertsNullToEmptyString() {
        Appointment appointment = new Appointment(
            104, pet, vet, LocalDateTime.now().plusDays(30).withHour(0)
            .withMinute(0), "Initial"
        );

        appointment.setNotes(null);

        assertEquals("", appointment.getNotes());
    }

    @Test
    public void testToStringReturnsExpectedFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 1, 1, 0, 0);
        Appointment appointment = new Appointment(
            105, pet, vet, dateTime, "Checkup"
        );

        String expected = "105 - 2026-01-01 00:00 - " + pet.getName()
                        + " with " + vet.getName();
        assertEquals(expected, appointment.toString());
    }

    @Test
    public void testScheduleAndCancelAppointment() throws Exception {
        setUpClinic();
        LocalDateTime future = LocalDateTime.now().plusDays(30)
                                                .withHour(0)
                                                .withMinute(0)
                                                .withSecond(0)
                                                .withNano(0);
        Appointment appt = scheduleTestAppointment(pet, vet, future, "checkup");

        assertTrue(clinic.getAppointments().contains(appt));
        //remove appointment
        clinic.cancelAppointment(appt);
        assertFalse(clinic.getAppointments().contains(appt));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPastDate() throws Exception {
        setUpClinic();
        LocalDateTime pastDate = LocalDateTime.now().minusDays(30);
        scheduleTestAppointment(pet, vet, pastDate, "checkup");
    }

    @Test(expected = AppointmentConflictException.class)
    public void testDoubleBooking() throws Exception {
        setUpClinic();
        LocalDateTime time = LocalDateTime.now().plusDays(30)
                                                .withHour(0)
                                                .withMinute(0)
                                                .withSecond(0)
                                                .withNano(0);
        Appointment appt1 = scheduleTestAppointment(pet, vet, time, "notes");
        try {
            scheduleTestAppointment(pet, vet, time, "notes");
        } finally {
            clinic.cancelAppointment(appt1);
        }

    }

    @Test
    public void testSequentialBookings() throws Exception {
        setUpClinic();
        LocalDateTime t1 = LocalDateTime.now().plusDays(30)
                                        .withHour(0)
                                        .withMinute(0)
                                        .withSecond(0)
                                        .withNano(0);
        LocalDateTime t2 = LocalDateTime.now().plusDays(30)
                                        .withHour(0)
                                        .withMinute(30)
                                        .withSecond(0)
                                        .withNano(0);

        Appointment appt1 = scheduleTestAppointment(pet, vet, t1, "note");
        Appointment appt2 = scheduleTestAppointment(pet, vet, t2, "note");

        assertTrue(clinic.getAppointments().contains(appt1) 
                    && clinic.getAppointments().contains(appt2));
    }

    @Test
    public void testBookingOver8Appointments() throws Exception {
        setUpClinic();

        LocalDateTime day = LocalDateTime.now().plusDays(31)
                                        .withHour(9)
                                        .withMinute(0)
                                        .withSecond(0)
                                        .withNano(0);

        for (int i = 0; i < 8; i++) {
            scheduleTestAppointment(
                pet, vet, day.plusHours(i), "TEST: limit " + i
            );
        }

        try {
            scheduleTestAppointment(
                pet, vet, day.plusHours(8), "TEST: one too many"
            );

            fail("Expected DailyAppointmentLimitException");
        } catch (DailyAppointmentLimitException e) {
            assertTrue(
                e.getMessage().contains("already has 8 appointments")
            );
        }
    }

    @Test
    public void testBookingExactly8Appointments() throws Exception {
        setUpClinic();

        LocalDateTime day = LocalDateTime.now().plusDays(30)
                                        .withHour(9)
                                        .withMinute(0)
                                        .withSecond(0)
                                        .withNano(0);

        int initialSize = clinic.getAppointments().size();

        for (int i = 0; i < 8; i++) {
            scheduleTestAppointment(
                pet, vet, day.plusHours(i), "TEST: exact eight " + i
            );
        }

        assertEquals(
            initialSize + 8,
            clinic.getAppointments().size()
        );
    }

    @Test
    public void testEnteringNonexistentPet() throws Exception {
        setUpClinic();
        // menu choice, bad pet id, then exit
        String output = runCli("11", "1000", "0");
        assertTrue(output.contains("Pet was not found"));
    }

    @Test
    public void testEnteringNonexistentVet() throws Exception {
        setUpClinic();
        // menu choice, valid pet id, bad vet id, then exit
        String output = runCli("11", "1", "1000", "0");
        assertTrue(output.contains("Veterinarian was not found"));
    }

    @Test
    public void testEnteringGarbageDate() throws Exception {
        setUpClinic();
        // menu choice, valid pet id, valid vet id, garbage date, then exit
        String output = runCli("11", "1", "1", "wawawawawa", "0");
        assertTrue(output.contains("Use date format yyyy-mm-dd hh:mm"));
    }
}
