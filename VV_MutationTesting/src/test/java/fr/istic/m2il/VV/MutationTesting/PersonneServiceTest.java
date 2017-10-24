package fr.istic.m2il.VV.MutationTesting;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

public class PersonneServiceTest {

	private PersonneService service;
	
	@Before
	public void setUp() {
		service = new PersonneService();
	}
	
	@After
	public void tearDown() {
		service = null;
	}
	
	@Test
	public void testNewPersonne() {
		Personne p = service.newPersonne("Waberi", "Houssein", 54);
		Assert.assertNotNull(p);
		Assert.assertEquals("Houssein", p.getNom());
		Assert.assertEquals("Waberi", p.getPrenom());
	}
	
	@Test
	public void testIsMajeur() {
		Assert.assertTrue(service.isMajeur(19));
		Assert.assertTrue(service.isMajeur(17));
	}

}
