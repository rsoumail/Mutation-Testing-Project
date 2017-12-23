package fr.istic.m2il.vv.input;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class SubstractionTest {

	@Test
	public void simple() throws Exception {
		Substraction substraction = new Substraction(10.0, 5.0);
		Assert.assertEquals(5.0,substraction.operate(), 1);
	}
	
	@Test
	public void SubstractionByZero() throws Exception {
		double a = 10;
		double b = 0;
		Substraction substraction = new Substraction(a, b);
		Assert.assertEquals(a, substraction.operate(),  1);
	}
	
	@Test 
	public void SubstractionPositive() throws Exception {
		double a = 50;
		double b = 80;
		Substraction substraction = new Substraction(a, b);
		if(a < b) {
			Assert.assertTrue(substraction.operate() < 0);
		}
		else {
			Assert.assertTrue(substraction.operate() >= 0);
		}
	}

	@Test
	public void SecondMember() throws Exception {

		Substraction substraction = new Substraction();
		substraction.setSecondMember(20.0);
		Assert.assertEquals(20.0, substraction.getSecondMember(), 1);
	}

	@Test
	public void SetFirstMember() throws Exception {
		Substraction substraction = new Substraction();
		substraction.setFirstMember(20.0);
		Assert.assertEquals(20.0, substraction.getFirstMember(), 1);
	}

	@Test
	public void firstGreatherThanSecond() throws Exception{
		Substraction substraction = new Substraction(30.0, 7.0);
		Assert.assertTrue(substraction.firstGreatherThanSecond());

	}

	@Test
	public void secondGreatherThanFirst() throws Exception{
		Substraction substraction = new Substraction(5.0,30.0);
		Assert.assertTrue(substraction.secondGreatherThanFirst() );
	}

	@Test
	public void firstLessThanSecond() throws Exception{
		Substraction substraction = new Substraction(5.0,30.0);
		Assert.assertTrue(substraction.firstLessThanSecond());
	}

	@Test
	public void secondLessThanFirst() throws Exception{
		Substraction substraction = new Substraction(30.0, 5.0);
		Assert.assertTrue(substraction.secondLessThanFirst());
	}
	
	@Test
	public void firstGreatherOrEqualThanSecond() throws Exception{
		Substraction substraction = new Substraction(60.0, 30.0);
		Assert.assertTrue(substraction.firstGreatherOrEqualThanSecond());
	}
	
	@Test 
	public void secondGreatherOrEqualThanFirst() throws Exception{
		Substraction substraction = new Substraction(30.0, 60.0);
		Assert.assertTrue(substraction.secondGreatherOrEqualThanFirst());
	}
	
	@Test
	public void firstLessOrEqualThanSecond() throws Exception{
		Substraction substraction = new Substraction(10.0, 20.0);
		Assert.assertTrue(substraction.firstLessOrEqualThanSecond());
	}
	
	@Test
	public void secondLessOrEqualThanFirst() throws Exception{
		Substraction substraction = new Substraction(20.0, 10.0);
		Assert.assertTrue(substraction.secondLessOrEqualThanFirst());
	}

}
