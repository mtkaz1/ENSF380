/*
Copyright Ann Barcomb and Emily Marasco, 2022-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

public class Clothing {

    private String colour;
    private String material;

    // Getters and setters
    public String getColour() { return this.colour; }
    public String getMaterial() { return this.material; }
    public void setColour(String colour) { this.colour = colour; }
    public void setMaterial(String material) { this.material = material; }

    // Constructor
    public Clothing(String colour, String material) {
        this.colour = colour;
        this.material = material;
    }


    public static void main(String[] args) {
        var garment = new Clothing("black", "cotton");
        System.out.println("Colour: " + garment.getColour() +
          ", Material: " + garment.getMaterial());
    }
}


