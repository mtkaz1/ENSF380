/*
Copyright Ann Barcomb and Emily Marasco, 2021-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

public class ExploreCharacter {
	public static void main(String[] args) {
		char justChar = ('\u00C3');
                Character characterTest = Character.valueOf(justChar);

 		// They can be used interchangeably
                System.out.println("Storing as char or Character does not affect" +
                  " the appearance of the letter.");
		System.out.println("Variable of type char: " + justChar);
		System.out.println("Variable of type Character: " + characterTest);
                System.out.println();

                System.out.println("Character provides useful functions/methods.");

		// Naïve approach to checking characters
                System.out.print("Using > and < to check char: ");
		if ((justChar >= 'a' && justChar <= 'z') ||
  		  (justChar >= 'A' && justChar <= 'Z')) {
			System.out.println(justChar + " is a letter");
		} else {
			System.out.println(justChar + " is not a letter");
		}

		// Using a method of the Character class
                System.out.print("Using Character.isLetter to check char: ");
		if (Character.isLetter(justChar)) {
			System.out.println(justChar + " is a letter");
		} else {
			System.out.println(justChar + " is not a letter");
		}
	}
}
