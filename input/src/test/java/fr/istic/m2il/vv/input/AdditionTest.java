package fr.istic.m2il.vv.input;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdditionTest {

	@Test
	public void simple() throws Exception {
		Addition addition = new Addition(10.0, 5.0);
		// Assert.assertEquals(addition.operate(), (addition.getFirstMember() +
		// addition.getSecondMember()), 0);
		assert (addition.operate() == addition.getFirstMember() + addition.getSecondMember());
	}

	@Test
	public void AdditionByZero() throws Exception {

		Addition add = new Addition(5000, 0);
		assert (add.operate() == 5000);
	}

	@Test
	public void FirstMember() throws Exception {
		double a = 0;
		double b = 1;
		Addition addition = new Addition(a, b);
		assert (addition.getFirstMember() == a);
	}
	
	@Test
	public void SecondMember() throws Exception {
		double a = 0;
		double b = 1;
		Addition addition = new Addition(a, b);
		assert (addition.getSecondMember() == b);
	}

	@Test
	public void Test() {
		assert (true);
	}
}