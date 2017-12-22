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
	public void SecondMember() throws Exception {

		Multiplication multiplication  = new Multiplication();
		multiplication.setSecondMember(20.0);
		Assert.assertEquals(20.0, multiplication.getSecondMember(), 1);
	}

	@Test
	public void SetFirstMember() throws Exception {
		Multiplication multiplication  = new Multiplication();
		multiplication.setSecondMember(20.0);
		Assert.assertEquals(20.0, multiplication.getFirstMember(), 1);
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
	public void firstGreatherOrEqualThanSecond() throws Exception{
		Multiplication multiplication = new Multiplication(60.0, 30.0);
		Assert.assertTrue(multiplication.firstGreatherOrEqualThanSecond());
	}
	
	@Test 
	public void secondGreatherOrEqualThanFirst() throws Exception{
		Multiplication multiplication = new Multiplication(30.0, 60.0);
		Assert.assertTrue(multiplication.secondGreatherOrEqualThanFirst());
	}
	
	@Test
	public void firstLessOrEqualThanSecond() throws Exception{
		Multiplication multiplication = new Multiplication(10.0, 20.0);
		Assert.assertTrue(multiplication.firstLessOrEqualThanSecond());
	}
	
	@Test
	public void secondLessOrEqualThanFirst() throws Exception{
		Multiplication multiplication = new Multiplication(20.0, 10.0);
		Assert.assertTrue(multiplication.secondLessOrEqualThanFirst());
	}

}
