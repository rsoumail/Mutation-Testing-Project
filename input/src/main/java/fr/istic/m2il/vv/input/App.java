package fr.istic.m2il.vv.input;

public class App {

    public static void main(String[] args) {

        Addition addition = new Addition(10.0,5.0);
        Divison division = new Divison(10.0, 5.0);
        Substraction substration = new Substraction(10.0, 5.0);
        addition.operate();      
        division.operate();
        substration.operate();
    }
}
