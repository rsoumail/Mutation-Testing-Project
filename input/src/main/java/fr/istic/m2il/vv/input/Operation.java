package fr.istic.m2il.vv.input;

public interface Operation {
	
	void setFirstMember(double firstMember);

	void setSecondMember(double secondMember);

	double getFirstMember();

	double getSecondMember();
	
	boolean firstGreatherThanSecond();

	boolean secondGreatherThanFirst();

	boolean firstLessThanSecond();

	boolean secondLessThanFirst();
	
	boolean firstGratherEqualSecond();
	
	boolean secondLessEqualSecond();
	
	double operate();
}
