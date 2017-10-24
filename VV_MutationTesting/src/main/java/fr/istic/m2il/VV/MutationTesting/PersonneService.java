package fr.istic.m2il.VV.MutationTesting;

public class PersonneService {
	/**
	 * Crée une nouvelle personne.
	 */
	public Personne newPersonne(String nom,
								String prenom,
								int age) {
		Personne p = new Personne();
		p.setNom(nom);
		p.setPrenom(prenom);
		p.setAge(age);
		return p;
	}
	
	/**
	 * Détermine si une personne est majeure.
	 */
	public boolean isMajeur(int age) {
		if (age >= 18) {
			return true;
		} else {
			return false;
		}
	}
}
