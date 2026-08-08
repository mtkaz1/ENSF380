/*
Copyright Ann Barcomb and Emily Marasco, 2023-2024
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class PetTest {
    int currentYear = LocalDate.now().getYear();
    LocalDate today = LocalDate.now();

    // Constructor is not needed and was removed
    
    @Test
    public void testCalculateAgeBirthdayPast() {
        // The first day of the year should always be today or already past.
        int[] birthDate = new int[]{today.getYear() - 3, 1, 1}; 
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        int expResult = currentYear - birthDate[0];
        int result = petDog.calculateAge();
        assertEquals("Calculated age was incorrect: ", expResult, result);
    }


    @Test
    public void testCalculateAge_BirthdayFuture() {
        // The last day of the year could be today or in the future
        int[] birthDate = new int[]{today.getYear() - 3, 12, 31}; 
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        int expResult = 0;
        if (today.isBefore(LocalDate.of(today.getYear(), 12, 31))) {
            expResult = currentYear - birthDate[0] - 1 ;
        } else {
            expResult = currentYear - birthDate[0];
        }
        int result = petDog.calculateAge();
        assertEquals("Calculated age was incorrect: ", expResult, result);
    }

    @Test
    public void testCalculateAgeBirthdayToday() {
        int[] birthDate = new int[]{today.getYear() - 3, today.getMonthValue(), today.getDayOfMonth()}; 
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        int expResult = currentYear - birthDate[0];
        int result = petDog.calculateAge();
        assertEquals("Calculated age was incorrect: ", expResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateAge_BirthdayException() {
        // Future date two years in the future
        int[] birthDate = new int[]{today.getYear() + 2, 7, 1}; 
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
    }

}
