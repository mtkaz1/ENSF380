/*
Copyright Ann Barcomb and Emily Marasco, 2021-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

public class Foo {

    public static void main(String[] args) {

        System.out.println();
        // Demonstrate dynamic allocation
        int intNested[][];

        intNested[0][0] = new int[3];
        intNested[0][1] = new int[4];
        intNested[1][0] = new String[5];
        intNested[1][1] = new String[1];
        for(int i=0; i < intNested.length; i++) {
            for(int j=0; j < intNested[i].length; j++) {
                for(int k=0; k< intNested[i][j].length; k++) {
                    System.out.print("intNested[" + i + "][" + j + "][" + k + "] ");
                    System.out.println(intNested[i][j][k]);
                }
            }
        } 
        

    }

}
  
