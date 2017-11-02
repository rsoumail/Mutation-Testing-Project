package fr.istic.m2il.vv.input;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class MultiplicationTest {

	@Test
	public void simple() throws Exception {
		Multiplication multiplication = new Multiplication(10.0, 5.0);
		Assert.assertEquals(multiplication.operate(), (multiplication.getFirstMember() * multiplication.getSecondMember()), 0);
	}
	
	@Test
	public void MultiplicationByZero() throws Exception {
		double a = 10;
		double b = 20;
		Multiplication multiplication = new Multiplication(a, b);
		assert(multiplication.operate() == 0);
	}
	
	@Test
	public void MultiplicationPostiveResult() throws Exception {
		double a = 10;
		double b = 20;
		Multiplication multiplication = new Multiplication(a, b);
		assert(multiplication.operate() > 0);
	}

}
