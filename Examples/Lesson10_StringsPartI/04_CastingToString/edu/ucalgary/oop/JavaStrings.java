/*
Copyright Ann Barcomb and Emily Marasco, 2021-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

public class JavaStrings {

    public static void main(String[] args) {
      
        Double speedRecord = 70.76;
        System.out.println("The fastest record for a horse is " + String.valueOf(speedRecord) + " km/h.");
        System.out.println("The fastest record for a horse is " + Double.toString(speedRecord) + " km/h.");
        System.out.println("The fastest record for a horse is " + speedRecord + " km/h.");
	}
}
