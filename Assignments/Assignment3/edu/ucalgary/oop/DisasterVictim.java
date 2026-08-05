package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class DisasterVictim {
    private static int counter = 1;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String comments;
    private final LocalDate ENTRY_DATE;
    private final int ASSIGNED_SOCIAL_ID;
    private FamilyRelation[] familyConnections = new FamilyRelation[0];
    private MedicalRecord[] medicalRecords = new MedicalRecord[0];
    private Supply[] personalBelongings = new Supply[0];

    public DisasterVictim(String firstName, LocalDate entryDate) {
        if (entryDate == null) {
            throw new IllegalArgumentException("Every victim needs an entry date, none was given");
        }
        if (entryDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Nobody can be logged as entering on " + entryDate + ", that day has not happened yet");
        }
        this.firstName = firstName;
        this.ENTRY_DATE = entryDate;
        this.ASSIGNED_SOCIAL_ID = generateSocialID();
    }

    public DisasterVictim(String firstName, LocalDate entryDate, LocalDate dateOfBirth) {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("A birthday cannot be left empty");
        }
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Nobody is born on " + dateOfBirth + " yet, that date has not arrived");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public int getAssignedSocialID() {
        return ASSIGNED_SOCIAL_ID;
    }

    public LocalDate getEntryDate() {
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
        if (gender == null || !Arrays.asList("man", "woman", "boy", "girl", "please specify")
                .contains(gender.toLowerCase())) {
            throw new IllegalArgumentException("\"" + gender + "\" is not on the gender list: man, woman, boy, girl, please specify");
        }
        this.gender = gender;
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
            throw new IllegalArgumentException("That relation was never linked to " + firstName + ", nothing to unlink");
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
            throw new IllegalArgumentException(firstName + " is not carrying that supply");
        }
        personalBelongings = remaining.toArray(new Supply[0]);
    }

    private static int generateSocialID() {
        return counter++;
    }
}
