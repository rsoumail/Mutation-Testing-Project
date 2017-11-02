package fr.istic.m2il.vv.input;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class SubstractionTest {

	@Test
	public void simple() throws Exception {
		Substraction substraction = new Substraction(10.0, 5.0);
		Assert.assertEquals(substraction.operate(), (substraction.getFirstMember() - substraction.getSecondMember()), 0);
	}
	
	@Test
	public void SubstractionByZero() throws Exception {
		double a = 10;
		double b = 0;
		Substraction substraction = new Substraction(a, b);
		assert(substraction.operate() == a);
	}
	
	@Test 
	public void SubstractionPositive() throws Exception {
		double a = 50;
		double b = 80;
		Substraction substraction = new Substraction(a, b);
		if(a < b) {
			assert(substraction.operate() < 0);
		}
		else {
			assert(substraction.operate() >= 0);
		}
	}

}
