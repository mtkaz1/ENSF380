/*
Copyright Ann Barcomb and Emily Marasco, 2022-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

public class TeaPot {
    private final Teabag bag;
    private boolean water = false;
    private boolean teabagIn = true;
       
    public void printWaterStatus() {
        if (this.water == true) {
            System.out.println("Water has been added to the teapot.");
        } else {
            System.out.println("Water has not been added to the teapot.");
        }
    }

    public void printTeabagStatus() {
        if (this.teabagIn == false) {
            System.out.println("The teabag has been removed.");
        } else {
            System.out.println("The teabag is still in the teapot.");
        }
    }

    public void printTeabagDetails() {
        if (this.teabagIn == false) {
            System.out.println("The teabag has been removed and thrown away.");
        } else {
            System.out.printf("We are brewing " + this.bag.getTeaType() +
              ", from " + this.bag.getTeaOrigin() + ", by " + 
              this.bag.getTeaBrand() + ".");
        }
    }

    public void removeTeabag() {
        this.teabagIn = false;
    }

    public void addWater() {
        this.water = true;
    }

    public TeaPot() {
        this.bag = new Teabag();
    }

    public TeaPot(String teaType) {
        this.bag = new Teabag(teaType);
    }

    public TeaPot(String teaType, String origin) {
        this.bag = new Teabag(teaType, origin);
    }

    public TeaPot(String teaType, String origin, String brand) {
        this.bag = new Teabag(teaType, origin, brand);
    }
}
