package fr.istic.m2il.vv.input;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdditionTest {

	@Test
	public void simple() throws Exception {
		Addition addition = new Addition(10.0, 5.0);
		Assert.assertEquals(15.0, addition.operate(), 1);
	}

	@Test
	public void AdditionByZero() throws Exception {

		Addition addition = new Addition(5000.0, 0.0);
		Assert.assertEquals(5000.0, addition.operate(), 1);
	}

	@Test
	public void FirstMember() throws Exception {
		double a = 0;
		double b = 1;
		Addition addition = new Addition(a, b);
		Assert.assertEquals(a, addition.getFirstMember(), 1);
	}

	@Test
	public void FirstMemberSet() throws Exception {
		Addition addition = new Addition(5.0, 30.0);
		addition.setFirstMember(10.0);
		Assert.assertEquals(10.0, addition.getFirstMember(), 1);
	}

	@Test
	public void SecondMember() throws Exception {
		double a = 0;
		double b = 1;
		Addition addition = new Addition(a, b);
		Assert.assertEquals(b, addition.getSecondMember(), 1);
	}

	@Test
	public void SetFirstMember() throws Exception {
		Addition addition = new Addition();
		addition.setFirstMember(20.0);
		Assert.assertEquals(20.0, addition.getFirstMember(), 1);
	}

	@Test
	public void firstGreatherThanSecond() throws Exception {
		Addition addition = new Addition(30.0, 7.0);
		Assert.assertTrue(addition.firstGreatherThanSecond());

	}

	@Test
	public void secondGreatherThanFirst() throws Exception {
		Addition addition = new Addition(5.0, 30.0);
		Assert.assertTrue(addition.secondGreatherThanFirst());
	}

	@Test
	public void firstLessThanSecond() throws Exception {
		Addition addition = new Addition(5.0, 30.0);
		Assert.assertTrue(addition.firstLessThanSecond());
	}

	@Test
	public void secondLessThanFirst() throws Exception {
		Addition addition = new Addition(30.0, 5.0);
		Assert.assertTrue(addition.secondLessThanFirst());
	}

	@Test
	public void firstGreatherOrEqualThenSecond() throws Exception {
		Addition addition = new Addition(60.0, 30.0);
		Assert.assertTrue(addition.firstGreatherOrEqualThenSecond());
	}

	@Test
	public void secondGreatherOrEqualThenFirst() throws Exception {
		Addition addition = new Addition(30.0, 60.0);
		Assert.assertTrue(addition.secondGreatherOrEqualThenFirst());
	}

	@Test
	public void firstLessOrEqualThenSecond() throws Exception {
		Addition addition = new Addition(10.0, 20.0);
		Assert.assertTrue(addition.firstLessOrEqualThenSecond());
	}

	@Test
	public void secondLessOrEqualThenFirst() throws Exception {
		Addition addition = new Addition(20.0, 10.0);
		Assert.assertTrue(addition.secondLessOrEqualThenFirst());
	}
}