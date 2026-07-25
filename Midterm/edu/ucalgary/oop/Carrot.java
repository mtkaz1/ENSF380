package edu.ucalgary.oop;

public interface Carrot extends Asparagus {
    String THE_STRING;

    default String defaultMethod() {}

    static String staticMethod() {}

    public abstract String abstractMethod();
}