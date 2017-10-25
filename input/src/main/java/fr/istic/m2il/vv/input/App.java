package fr.istic.m2il.vv.input;

public class App {

	public static void main(String[] args) {

		Addition addition = new Addition(10.0, 5.0);
		Substraction substraction = new Substraction(10.0, 5.0);
		Multiplication multiplication = new Multiplication(10.0, 5.0);
		Division division = new Division(10.0, 5.0);
		System.out.println("Addition : " + addition.operate());
		System.out.println("Sustraction : " + substraction.operate());
		System.out.println("Multiplication : " + multiplication.operate());
		System.out.println("Division : " + division.operate());

	}
}
