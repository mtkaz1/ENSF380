package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class PetTest {
    LocalDate today = LocalDate.now();
    int currentYear = today.getYear();

    @Test
    public void testCalculateAgeBirthdayPast() {
        int[] birthDate = new int[]{currentYear - 3, 1, 1};
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        int expResult = currentYear - birthDate[0];
        int result = petDog.calculateAge();
        assertEquals("Calculated age was incorrect: ", expResult, result);
    }

    @Test
    public void testCalculateAgeBirthdayFuture() {
        int[] birthDate = new int[]{currentYear - 3, 12, 31};
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        int expResult;
        if (today.isBefore(LocalDate.of(currentYear, 12, 31))) {
            expResult = currentYear - birthDate[0] - 1;
        } else {
            expResult = currentYear - birthDate[0];
        }
        int result = petDog.calculateAge();
        assertEquals("Calculated age was incorrect: ", expResult, result);
    }

    @Test
    public void testCalculateAgeBirthdayToday() {
        int[] birthDate = new int[]{currentYear - 3, today.getMonthValue(), today.getDayOfMonth()};
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        int expResult = currentYear - birthDate[0];
        int result = petDog.calculateAge();
        assertEquals("Calculated age was incorrect: ", expResult, result);
    }
}
