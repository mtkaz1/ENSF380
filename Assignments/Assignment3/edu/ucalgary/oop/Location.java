package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.Arrays;

public class Location {
    private String name;
    private String address;
    private DisasterVictim[] occupants = new DisasterVictim[0];
    private Supply[] supplies = new Supply[0];

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

    public DisasterVictim[] getOccupants() {
        return occupants;
    }

    public void setOccupants(DisasterVictim[] occupants) {
        this.occupants = occupants;
    }

    public void addOccupant(DisasterVictim occupant) {
        occupants = Arrays.copyOf(occupants, occupants.length + 1);
        occupants[occupants.length - 1] = occupant;
    }

    public void removeOccupant(DisasterVictim occupant) {
        ArrayList<DisasterVictim> remaining = new ArrayList<>(Arrays.asList(occupants));
        if (!remaining.remove(occupant)) {
            throw new IllegalArgumentException("That person was never checked in at " + name);
        }
        occupants = remaining.toArray(new DisasterVictim[0]);
    }

    public Supply[] getSupplies() {
        return supplies;
    }

    public void setSupplies(Supply[] supplies) {
        this.supplies = supplies;
    }

    public void addSupply(Supply supply) {
        supplies = Arrays.copyOf(supplies, supplies.length + 1);
        supplies[supplies.length - 1] = supply;
    }

    public void removeSupply(Supply supply) {
        ArrayList<Supply> remaining = new ArrayList<>(Arrays.asList(supplies));
        if (!remaining.remove(supply)) {
            throw new IllegalArgumentException(name + " has no such supply on the shelf");
        }
        supplies = remaining.toArray(new Supply[0]);
    }
}
