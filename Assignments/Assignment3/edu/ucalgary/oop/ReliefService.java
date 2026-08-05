package edu.ucalgary.oop;

import java.time.LocalDate;

public class ReliefService {
    private Inquirer inquirer;
    private DisasterVictim missingPerson;
    private LocalDate dateOfInquiry;
    private String infoProvided;
    private Location lastKnownLocation;

    public ReliefService(Inquirer inquirer, DisasterVictim missingPerson, LocalDate dateOfInquiry,
                         String infoProvided, Location lastKnownLocation) {
        this.inquirer = inquirer;
        this.missingPerson = missingPerson;
        setDateOfInquiry(dateOfInquiry);
        this.infoProvided = infoProvided;
        this.lastKnownLocation = lastKnownLocation;
    }

    public Inquirer getInquirer() {
        return inquirer;
    }

    public void setInquirer(Inquirer inquirer) {
        this.inquirer = inquirer;
    }

    public DisasterVictim getMissingPerson() {
        return missingPerson;
    }

    public void setMissingPerson(DisasterVictim missingPerson) {
        this.missingPerson = missingPerson;
    }

    public LocalDate getDateOfInquiry() {
        return dateOfInquiry;
    }

    public void setDateOfInquiry(LocalDate dateOfInquiry) {
        if (dateOfInquiry == null) {
            throw new IllegalArgumentException("An inquiry needs a date, none was given");
        }
        if (dateOfInquiry.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("No inquiry could have been filed on " + dateOfInquiry + ", that day has not arrived");
        }
        this.dateOfInquiry = dateOfInquiry;
    }

    public String getInfoProvided() {
        return infoProvided;
    }

    public void setInfoProvided(String infoProvided) {
        this.infoProvided = infoProvided;
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public String getLogDetails() {
        return "Inquirer: " + inquirer.getFirstName() +
            ", Missing Person: " + missingPerson.getFirstName() +
            ", Date of Inquiry: " + dateOfInquiry +
            ", Info Provided: " + infoProvided +
            ", Last Known Location: " + lastKnownLocation.getName();
    }
}
