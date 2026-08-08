//ENSF 380 Assignment 3 Group 5
// Muhammad Kazi, Ahmed Nasr, Rafee Chaudhry

package edu.ucalgary.oop;

import java.time.LocalDate;

public class MedicalRecord {
    private Location location;
    private String treatmentDetails;
    private String dateOfTreatment;

    public MedicalRecord(Location location, String treatmentDetails, String dateOfTreatment) {
        this.location = location;
        this.treatmentDetails = treatmentDetails;
        setDateOfTreatment(dateOfTreatment);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    public void setTreatmentDetails(String treatmentDetails) {
        this.treatmentDetails = treatmentDetails;
    }

    public String getDateOfTreatment() {
        return dateOfTreatment;
    }

    public void setDateOfTreatment(String dateOfTreatment) {
        if (!isValidDateFormat(dateOfTreatment)) {
            throw new IllegalArgumentException("Treatment was not given on \"" + dateOfTreatment + "\", that is not a real yyyy-mm-dd date");
        }
        this.dateOfTreatment = dateOfTreatment;
    }

    private boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
