//ENSF 380 Assignment 3 Group 5
// Muhammad Kazi, Ahmed Nasr, Rafee Chaudhry

package edu.ucalgary.oop;

public class Supply {
    private String type;
    private int quantity;

    public Supply(String type, int quantity) {
        if (type == null || quantity < 0) {
            throw new IllegalArgumentException("A supply needs a type and a count of zero or more");
        }
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("You can't have negative supplies");
        }
        this.quantity = quantity;
    }
}
