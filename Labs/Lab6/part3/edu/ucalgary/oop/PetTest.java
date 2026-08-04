package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;

public class PetTest {
    LocalDate today = LocalDate.now();
    int currentYear = today.getYear();

    private int[] defaultBirthday;

    @Before
    public void setUp() {
        defaultBirthday = new int[]{currentYear - 10, 6, 15};
    }

    private Pet defaultPet() {
        return new Pet("Kanellos", "Dog", "Golden Retriever", 4, "Golden", "Fur", defaultBirthday);
    }

    private Pet petBornOn(LocalDate date) {
        int[] birthDate = new int[]{date.getYear(), date.getMonthValue(), date.getDayOfMonth()};
        return new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
    }

    // Original tests

    @Test
    public void testCalculateAgeBirthdayPast() {
        int[] birthDate = new int[]{currentYear - 3, 1, 1};
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        assertEquals("Calculated age was incorrect: ", 3, petDog.calculateAge());
    }

    @Test
    public void testCalculateAgeBirthdayFuture() {
        int[] birthDate = new int[]{currentYear - 3, 12, 31};
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        int expResult = today.isBefore(LocalDate.of(currentYear, 12, 31)) ? 2 : 3;
        assertEquals("Calculated age was incorrect: ", expResult, petDog.calculateAge());
    }

    @Test
    public void testCalculateAgeBirthdayToday() {
        Pet petDog = petBornOn(today.minusYears(3));
        assertEquals("Calculated age was incorrect: ", 3, petDog.calculateAge());
    }

    // Boundary

    @Test
    public void testCalculateAgeBirthdayYesterday() {
        Pet petDog = petBornOn(today.minusYears(3).minusDays(1));
        assertEquals("Calculated age was incorrect: ", 3, petDog.calculateAge());
    }

    @Test
    public void testCalculateAgeBirthdayTomorrow() {
        Pet petDog = petBornOn(today.minusYears(3).plusDays(1));
        assertEquals("Calculated age was incorrect: ", 2, petDog.calculateAge());
    }

    @Test
    public void testCalculateAgeBirthdayLastMonth() {
        Pet petDog = petBornOn(today.minusYears(3).minusMonths(1));
        assertEquals("Calculated age was incorrect: ", 3, petDog.calculateAge());
    }

    @Test
    public void testCalculateAgeBirthdayNextMonth() {
        Pet petDog = petBornOn(today.minusYears(3).plusMonths(1));
        assertEquals("Calculated age was incorrect: ", 2, petDog.calculateAge());
    }

    // Edge cases

    @Test
    public void testCalculateAgeBornToday() {
        Pet petDog = petBornOn(today);
        assertEquals("Calculated age was incorrect: ", 0, petDog.calculateAge());
    }

    @Test
    public void testCalculateAgeLeapDayBirthday() {
        int leapYear = currentYear - 1;
        while (!Year.isLeap(leapYear)) {
            leapYear--;
        }
        LocalDate birth = LocalDate.of(leapYear, 2, 29);
        Pet petDog = petBornOn(birth);
        int expResult = Period.between(birth, today).getYears();
        assertEquals("Calculated age was incorrect: ", expResult, petDog.calculateAge());
    }

    // Accessors and mutators

    @Test
    public void testGetName() {
        assertEquals("Incorrect name returned", "Kanellos", defaultPet().getName());
    }

    @Test
    public void testSetName() {
        Pet pet = defaultPet();
        pet.setName("Loukanikos");
        assertEquals("Name not updated correctly", "Loukanikos", pet.getName());
    }

    @Test
    public void testGetSpecies() {
        assertEquals("Incorrect species returned", "Dog", defaultPet().getSpecies());
    }

    @Test
    public void testSetSpecies() {
        Pet pet = defaultPet();
        pet.setSpecies("Cat");
        assertEquals("Species not updated correctly", "Cat", pet.getSpecies());
    }

    @Test
    public void testGetBirthYear() {
        assertEquals("Incorrect birth year returned", currentYear - 10, defaultPet().getBirthYear());
    }

    @Test
    public void testGetBirthMonthAndDay() {
        Pet pet = defaultPet();
        assertEquals("Incorrect birth month returned", 6, pet.getBirthMonth());
        assertEquals("Incorrect birth day returned", 15, pet.getBirthDay());
    }
}
