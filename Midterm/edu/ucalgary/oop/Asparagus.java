package edu.ucalgary.oop;

public interface Asparagus {
    String THE_STRING;

    default String defaultMethod() {}

    static String staticMethod() {}

    public abstract String abstractMethod();
}  