package fr.istic.m2il.vv.input;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdditionTest {

	@Test
	public void simple() throws Exception {
		Addition addition = new Addition(10.0, 5.0);
		Assert.assertEquals(addition.operate(), (addition.getFirstMember() + addition.getSecondMember()), 0);
	}

}