//ENSF 380 Assignment 3 Group 5
// Muhammad Kazi, Ahmed Nasr, Rafee Chaudhry



package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class DisasterVictim {
    private static int counter = 1;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String comments;
    private final String ENTRY_DATE;
    private final int ASSIGNED_SOCIAL_ID;
    private FamilyRelation[] familyConnections = new FamilyRelation[0];
    private MedicalRecord[] medicalRecords = new MedicalRecord[0];
    private Supply[] personalBelongings = new Supply[0];

    public DisasterVictim(String firstName, String entryDate) {
        if (!isValidDateFormat(entryDate)) {
            throw new IllegalArgumentException("Entry date \"" + entryDate + "\" is not a real date, use yyyy-mm-dd");
        }
        if (convertDateStringToInt(entryDate) > convertDateStringToInt(LocalDate.now().toString())) {
            throw new IllegalArgumentException("Invalid entry date " + entryDate + ", that's in the future");
        }
        this.firstName = firstName;
        this.ENTRY_DATE = entryDate;
        this.ASSIGNED_SOCIAL_ID = generateSocialID();
    }

    public DisasterVictim(String firstName, String entryDate, String dateOfBirth) {
        this(firstName, entryDate);
        setDateOfBirth(dateOfBirth);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        if (!isValidDateFormat(dateOfBirth)) {
            throw new IllegalArgumentException("Birthdays go in as yyyy-mm-dd, got \"" + dateOfBirth + "\"");
        }
        if (convertDateStringToInt(dateOfBirth) > convertDateStringToInt(ENTRY_DATE)) {
            throw new IllegalArgumentException(firstName + " cannot be born on " + dateOfBirth + ", that is after the entry date " + ENTRY_DATE);
        }
        this.dateOfBirth = dateOfBirth;
    }

    public int getAssignedSocialID() {
        return ASSIGNED_SOCIAL_ID;
    }

    public String getEntryDate() {
        return ENTRY_DATE;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be left blank");
        }
        this.gender = gender.trim().toLowerCase();
    }

    public FamilyRelation[] getFamilyConnections() {
        return familyConnections;
    }

    public void setFamilyConnections(FamilyRelation[] connections) {
        this.familyConnections = connections;
    }

    public void addFamilyConnection(FamilyRelation record) {
        familyConnections = Arrays.copyOf(familyConnections, familyConnections.length + 1);
        familyConnections[familyConnections.length - 1] = record;
    }

    public void removeFamilyConnection(FamilyRelation exRelation) {
        ArrayList<FamilyRelation> remaining = new ArrayList<>(Arrays.asList(familyConnections));
        if (!remaining.remove(exRelation)) {
            throw new IllegalArgumentException("That relation was never connected to " + firstName + "");
        }
        familyConnections = remaining.toArray(new FamilyRelation[0]);
    }

    public MedicalRecord[] getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(MedicalRecord[] records) {
        this.medicalRecords = records;
    }

    public void addMedicalRecord(MedicalRecord record) {
        medicalRecords = Arrays.copyOf(medicalRecords, medicalRecords.length + 1);
        medicalRecords[medicalRecords.length - 1] = record;
    }

    public Supply[] getPersonalBelongings() {
        return personalBelongings;
    }

    public void setPersonalBelongings(Supply[] belongings) {
        this.personalBelongings = belongings;
    }

    public void addPersonalBelonging(Supply supply) {
        personalBelongings = Arrays.copyOf(personalBelongings, personalBelongings.length + 1);
        personalBelongings[personalBelongings.length - 1] = supply;
    }

    public void removePersonalBelonging(Supply unwantedSupply) {
        ArrayList<Supply> remaining = new ArrayList<>(Arrays.asList(personalBelongings));
        if (!remaining.remove(unwantedSupply)) {
            throw new IllegalArgumentException(firstName + " doesn't have the supply");
        }
        personalBelongings = remaining.toArray(new Supply[0]);
    }

    private static int generateSocialID() {
        return counter++;
    }

    private boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int convertDateStringToInt(String dateStr) {
        return Integer.parseInt(dateStr.replace("-", ""));
    }
}
