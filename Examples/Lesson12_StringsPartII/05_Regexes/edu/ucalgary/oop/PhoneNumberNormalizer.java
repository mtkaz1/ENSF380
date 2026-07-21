/*
Copyright Ann Barcomb 2025
Licensed under GPL v3
See LICENSE.txt for more innormalizeion.
*/

package edu.ucalgary.oop;

import java.util.regex.Pattern;

public class PhoneNumberNormalizer {

    // Alternate version which will work with example 4
    public static String normalizePhoneNumberExtraSpace(String inputPhone) {
        // The . means "match anything", and the * means "0 or more times"
        // Because we do not capture the .* it means we ignore any additional
        // text which comes before the phone number and don't include it in our
        // output. If we want a literal period in the regular expression, we
        // need to escape it - except within the character class, indicated
        // with []. Within [], the . is interpreted as a literal character.
        // You might also notice that the - is at the end of the character class.
        // By doing this, I ensure that it is interpreted as a literal dash,
        // rather than a range, e.g., [A-C] (which is the same as [ABC]).
        String phoneRegex = "^.*(\\d{3})[\\s.-]?(\\d{3})[\\s.-]?(\\d{4})$";
        String replacement = "$1-$2-$3";
        return inputPhone.replaceAll(phoneRegex, replacement);
    }

    // Original version of the method
    public static String normalizePhoneNumber(String inputPhone) {
        // Define the regex pattern
        String phoneRegex = "^(\\d{3})[\\s.-]?(\\d{3})[\\s.-]?(\\d{4})$";
        // Define the replacement string
        String replacement = "$1-$2-$3";

        // Use String.replaceAll to replace the text.
        return inputPhone.replaceAll(phoneRegex, replacement);
    }

    public static void main(String[] args) {

        String inputPhone1 = "123 4567890";
        String inputPhone2 = "123-456_7890"; // Will not match
        String inputPhone3 = "123.456-7890";
        String inputPhone4 = "# 123 456 7890"; // Will not match

        System.out.println("Original Phone Number 1: " + inputPhone1);
        System.out.println("Formatted Phone Number 1: " + normalizePhoneNumber(inputPhone1));
        System.out.println();
        System.out.println("Original Phone Number 2: " + inputPhone2);
        System.out.println("Formatted Phone Number 2: " + normalizePhoneNumber(inputPhone2));
        System.out.println();
        System.out.println("Original Phone Number 3: " + inputPhone3);
        System.out.println("Formatted Phone Number 3: " + normalizePhoneNumber(inputPhone3));
        System.out.println();
        System.out.println("Original Phone Number 4: " + inputPhone4);
        System.out.println("Formatted Phone Number 4: " + normalizePhoneNumber(inputPhone4));
    }
}

