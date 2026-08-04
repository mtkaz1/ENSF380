package edu.ucalgary.oop;

public class Inquirer {
    private final String FIRST_NAME;
    private final String LAST_NAME;
    private final String INFO;
    private final String SERVICES_PHONE;

    public Inquirer(String firstName, String lastName, String phone, String info) {
        if (firstName == null || lastName == null || phone == null || info == null) {
            throw new IllegalArgumentException("An inquirer needs a full name, a phone number, and their info");
        }
        this.FIRST_NAME = firstName;
        this.LAST_NAME = lastName;
        this.SERVICES_PHONE = phone;
        this.INFO = info;
    }

    public String getFirstName() {
        return FIRST_NAME;
    }

    public String getLastName() {
        return LAST_NAME;
    }

    public String getServicesPhoneNum() {
        return SERVICES_PHONE;
    }

    public String getInfo() {
        return INFO;
    }
}
