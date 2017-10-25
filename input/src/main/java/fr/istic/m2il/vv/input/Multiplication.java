package fr.istic.m2il.vv.input;

public class Multiplication {
	private Double firstMember;
	private Double secondMember;

	public Multiplication(Double firstMember, Double secondMember) {
		this.firstMember = firstMember;
		this.secondMember = secondMember;
	}

	public Double getFisrtMember() {
		return null;
	}

	public void setFirstMember(Double firstMember) {
		this.firstMember = firstMember;
	}

	public Double getSecondMember() {
		return this.secondMember;
	}

	public void setSecondMember(Double secondMember) {
		this.secondMember = secondMember;
	}

	public Double operate() {
		return (getFisrtMember() * getSecondMember());
	}
}
