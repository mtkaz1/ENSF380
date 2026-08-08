//ENSF 380 Assignment 3 Group 1
// Muhammad Kazi, Ahmed Nasr, Rafee Chaudhry



package edu.ucalgary.oop;

public class FamilyRelation {
    private DisasterVictim personOne;
    private String relationshipTo;
    private DisasterVictim personTwo;

    public FamilyRelation(DisasterVictim personOne, String relationshipTo, DisasterVictim personTwo) {
        if (personOne == null || relationshipTo == null || personTwo == null) {
            throw new IllegalArgumentException("A family relation needs two people and the relationship between them");
        }
        this.personOne = personOne;
        this.relationshipTo = relationshipTo;
        this.personTwo = personTwo;
    }

    public DisasterVictim getPersonOne() {
        return personOne;
    }

    public void setPersonOne(DisasterVictim personOne) {
        this.personOne = personOne;
    }

    public String getRelationshipTo() {
        return relationshipTo;
    }

    public void setRelationshipTo(String relationshipTo) {
        this.relationshipTo = relationshipTo;
    }

    public DisasterVictim getPersonTwo() {
        return personTwo;
    }

    public void setPersonTwo(DisasterVictim personTwo) {
        this.personTwo = personTwo;
    }
}
