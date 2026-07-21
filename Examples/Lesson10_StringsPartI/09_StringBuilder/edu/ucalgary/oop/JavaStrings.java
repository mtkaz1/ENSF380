/*
Copyright Ann Barcomb and Emily Marasco, 2021-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

public class JavaStrings {

    public static void main(String[] args) {
        
        StringBuilder animalFact1 = new StringBuilder("Horses are mammals");
        animalFact1.append(" and so are elephants.");
        
        System.out.println(animalFact1);
        
        StringBuilder animalFact2 = new StringBuilder("Horses are mammals.");
        animalFact2.insert(7,"and elephants ");
        
        System.out.println(animalFact2);
    }        
}  
