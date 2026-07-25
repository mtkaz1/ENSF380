package edu.ucalgary.oop;

public interface Beet extends Asparagus {
    String THE_STRING;

    @Override
    default String defaultMethod() {}

    public abstract String abstractMethod();
}