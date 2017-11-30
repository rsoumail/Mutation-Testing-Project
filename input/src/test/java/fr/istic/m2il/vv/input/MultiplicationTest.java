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
		double b = 0;
		Multiplication multiplication = new Multiplication(a, b);
		Assert.assertEquals(0.0, multiplication.operate(), 1);
	}
	
	@Test
	public void MultiplicationPostiveResult() throws Exception {
		double a = 10;
		double b = 20;
		Multiplication multiplication = new Multiplication(a, b);
		Assert.assertTrue(multiplication.operate() > 0);
	}

	@Test
	public void firstGreatherThanSecond() throws Exception{
		Multiplication multiplication = new Multiplication(30.0, 7.0);
		Assert.assertTrue(multiplication.firstGreatherThanSecond());

	}

	@Test
	public void secondGreatherThanFirst() throws Exception{
		Multiplication multiplication = new Multiplication(5.0,30.0);
		Assert.assertTrue(multiplication.secondGreatherThanFirst() );
	}

	@Test
	public void firstLessThanSecond() throws Exception{
		Multiplication multiplication = new Multiplication(5.0,30.0);
		Assert.assertTrue(multiplication.firstLessThanSecond());
	}

	@Test
	public void secondLessThanFirst() throws Exception{
		Multiplication multiplication = new Multiplication(30.0, 5.0);
		Assert.assertTrue(multiplication.secondLessThanFirst());
	}
	
	@Test
	public void firstGreatherOrEqualThenSecond() throws Exception{
		Multiplication multiplication = new Multiplication(60.0, 30.0);
		Assert.assertTrue(multiplication.firstGreatherOrEqualThenSecond());
	}
	
	@Test 
	public void secondGreatherOrEqualThenFirst() throws Exception{
		Multiplication multiplication = new Multiplication(30.0, 60.0);
		Assert.assertTrue(multiplication.secondGreatherOrEqualThenFirst());
	}
	
	@Test
	public void firstLessOrEqualThenSecond() throws Exception{
		Multiplication multiplication = new Multiplication(10.0, 20.0);
		Assert.assertTrue(multiplication.firstLessOrEqualThenSecond());
	}
	
	@Test
	public void secondLessOrEqualThenFirst() throws Exception{
		Multiplication multiplication = new Multiplication(20.0, 10.0);
		Assert.assertTrue(multiplication.secondLessOrEqualThenFirst());
	}

}
