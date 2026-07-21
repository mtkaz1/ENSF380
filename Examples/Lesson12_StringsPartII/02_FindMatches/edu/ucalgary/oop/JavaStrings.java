/*
Copyright Ann Barcomb and Emily Marasco, 2021-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

import java.util.regex.*;

public class JavaStrings {

    public static void main(String[] args) {
        
        Pattern myPattern = Pattern.compile("Horses", Pattern.CASE_INSENSITIVE);
        Matcher myMatcher = myPattern.matcher("Horses are mammals. Seahorses are not.");
        boolean isMatch = myMatcher.find();
        System.out.println(isMatch);
	}
}
		
