package fr.istic.m2il.vv.input;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class DivisionTest {

	@Test
	public void simple() throws Exception {
		Division division = new Division(10.0, 5.0);
		Assert.assertEquals(division.operate(), (division.getFirstMember() / division.getSecondMember()), 1);
	}
	
	@Test
	public void DivisionByOne() throws Exception {
		double a = 1000;
		double b = 1;
		Division division = new Division(a, b);
		Assert.assertEquals(division.operate(), a, 1);
	}

	@Test
	public void OneDivisionByNumber() throws Exception {
		double a = 1;
		double b = 10;
		Division division = new Division(a, b);
		Assert.assertTrue(division.operate() < 1);
	}
	
	@Test
	public void DivisionByNumber() throws Exception {
		double a = 50;
		double b = 10;
		Division division = new Division(a, b);
		Assert.assertEquals(5, division.operate(), 1);
	}

	@Test
	public void firstGreatherThanSecond() throws Exception{
		Division division = new Division(30.0, 7.0);
		Assert.assertTrue(division.firstGreatherThanSecond());

	}

	@Test
	public void secondGreatherThanFirst() throws Exception{
		Division division = new Division(5.0,30.0);
		Assert.assertTrue(division.secondGreatherThanFirst() );
	}

	@Test
	public void firstLessThanSecond() throws Exception{
		Division division = new Division(5.0,30.0);
		Assert.assertTrue(division.firstLessThanSecond());
	}

	@Test
	public void secondLessThanFirst() throws Exception{
		Division division = new Division(30.0, 5.0);
		Assert.assertTrue(division.secondLessThanFirst());
	}
}
