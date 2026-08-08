//ENSF 380 Assignment 3 Group 5
// Muhammad Kazi, Ahmed Nasr, Rafee Chaudhry

package edu.ucalgary.oop;

import java.util.ArrayList;

public class Location {
    private String name;
    private String address;
    private ArrayList<DisasterVictim> occupants = new ArrayList<>();
    private ArrayList<Supply> supplies = new ArrayList<>();

    public Location(String name, String address) {
        if (name == null || address == null) {
            throw new IllegalArgumentException("A location needs both a name and an address");
        }
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<DisasterVictim> getOccupants() {
        return occupants;
    }

    public void setOccupants(ArrayList<DisasterVictim> occupants) {
        this.occupants = occupants;
    }

    public void addOccupant(DisasterVictim occupant) {
        occupants.add(occupant);
    }

    public void removeOccupant(DisasterVictim occupant) {
        if (!occupants.remove(occupant)) {
            throw new IllegalArgumentException("That person was never checked in at " + name);
        }
    }

    public ArrayList<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(ArrayList<Supply> supplies) {
        this.supplies = supplies;
    }

    public void addSupply(Supply supply) {
        supplies.add(supply);
    }

    public void removeSupply(Supply supply) {
        if (!supplies.remove(supply)) {
            throw new IllegalArgumentException(name + " has no such supply on the shelf");
        }
    }
}
