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
    
    public PetTest() {
    }
    
    @Test
    public void testCalculateAgeBirthdayPast() {
        int[] birthDate = new int[]{2020,1,1};
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        int expResult = currentYear - birthDate[0];
        int result = petDog.calculateAge();
        assertEquals("Calculated age was incorrect: ", expResult, result);
    }


    @Test
    public void testCalculateAgeBirthdayFuture() {
        int[] birthDate = new int[]{2020,12,30};
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        int expResult = currentYear - birthDate[0] - 1;
        int result = petDog.calculateAge();
        assertEquals("Calculated age was incorrect: ", expResult, result);
    }

    @Test
    public void testCalculateAgeBirthdayToday() {
        int[] birthDate = new int[]{2020,12,25};
        Pet petDog = new Pet("Pongo", "Dog", "Dalmatian", 4, "Spotted", "Fur", birthDate);
        int expResult = currentYear - birthDate[0];
        int result = petDog.calculateAge();
        assertEquals("Calculated age was incorrect: ", expResult, result);
    }
}
