/*
Copyright Ann Barcomb and Emily Marasco, 2024
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class PetTest {
    private int[] defaultBirthday;

    @Before
    public void setUp() {
        // Arrange: Set up a default birthday for reuse
        defaultBirthday = new int[]{2015, 6, 15};
    }

    @Test
    public void testCalculateAgeBirthdayPast() {
        // Arrange
        int[] birthDate = {2015, 1, 1};
        Pet pet = new Pet("Patricus", "Dog", "Mastiff", 4, "Brindle", 
          "Fur", birthDate);

        // Act
        int age = pet.calculateAge();

        // Assert
        int expectedAge = LocalDate.now().getYear() - 2015;
        assertEquals("Incorrect age for past birthday", expectedAge, age);
    }

    @Test
    public void testCalculateAgeBirthdayFuture() {
        // Arrange
        int[] birthDate = {2015, 12, 31};
        Pet pet = new Pet("Patricus", "Dog", "Mastiff", 4, "Brindle", "Fur", 
          birthDate);

        // Act
        int age = pet.calculateAge();

        // Assert
        LocalDate today = LocalDate.now();
        int expectedAge = 0;
        if (today.isBefore(LocalDate.of(today.getYear(), 12, 31))) {
            expectedAge = today.getYear() - 2015 - 1 ;
        } else {
            expectedAge = today.getYear() - 2015;
        }
        assertEquals("Incorrect age for future birthday", expectedAge, age);
    }

    @Test
    public void testCalculateAgeBirthdayToday() {
        // Arrange
        LocalDate today = LocalDate.now();
        int[] birthDate = {today.getYear() - 5, today.getMonthValue(), 
           today.getDayOfMonth()};
        Pet pet = new Pet("Mrs. Chippy", "Cat", "Tabby", 4, "Mackerel", "Fur", 
            birthDate);

        // Act
        int age = pet.calculateAge();

        // Assert
        assertEquals("Incorrect age for birthday today", 5, age);
    }

    @Test
    public void testCalculateAge_LeapYearBirthday() {
        // Arrange
        int[] birthDate = {2016, 2, 29};
        Pet pet = new Pet("Velveteen", "Rabbit", "Hare", 4, "Brown", "Fur", 
            birthDate);

        // Act
        int age = pet.calculateAge();

        // Assert
        LocalDate today = LocalDate.now();
        int expectedAge = 0;
        if (today.isBefore(LocalDate.of(today.getYear(), 2, 29))) {
            expectedAge = today.getYear() - 2016 - 1 ;
        } else {
            expectedAge = today.getYear() - 2016;
        }
        assertEquals("Incorrect age for leap year birthday", expectedAge, age);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_FutureBirthdayThrowsException() {
        // Arrange
        int[] futureBirthday = {LocalDate.now().getYear() + 1, 7, 1};

        // Act & Assert: Exception is expected
        new Pet("Greyfriars Bobby", "Dog", "Skye Terrier", 4, "Brown", "Fur", 
           futureBirthday);
    }

    @Test
    public void testGetName() {
        // Arrange
        Pet pet = new Pet("Kanellos", "Dog", "Golden Retriever", 4, "Golden", 
          "Fur", defaultBirthday);

        // Act
        String name = pet.getName();

        // Assert
        assertEquals("Incorrect name returned", "Kanellos", name);
    }

    @Test
    public void testSetName() {
        // Arrange
        Pet pet = new Pet("Kanellos", "Dog", "Golden Retriever", 4, "Golden", 
           "Fur", defaultBirthday);

        // Act
        pet.setName("Loukanikos");

        // Assert
        assertEquals("Name not updated correctly", "Loukanikos", pet.getName());
    }

    @Test
    public void testGetSpecies() {
        // Arrange
        Pet pet = new Pet("Kanellos", "Dog", "Golden Retriever", 4, "Golden", 
           "Fur", defaultBirthday);

        // Act
        String species = pet.getSpecies();

        // Assert
        assertEquals("Incorrect species returned", "Dog", species);
    }

    @Test
    public void testSetSpecies() {
        // Arrange
        Pet pet = new Pet("Kanellos", "Dog", "Golden Retriever", 4, 
           "Golden", "Fur", defaultBirthday);

        // Act
        pet.setSpecies("Cat");

        // Assert
        assertEquals("Species not updated correctly", "Cat", pet.getSpecies());
    }

    @Test
    public void testGetBirthYear() {
        // Arrange
        Pet pet = new Pet("Kanellos", "Dog", "Golden Retriever", 4, "Golden", 
           "Fur", defaultBirthday);

        // Act
        int year = pet.getBirthYear();

        // Assert
        assertEquals("Incorrect birth year returned", 2015, year);
    }
}

