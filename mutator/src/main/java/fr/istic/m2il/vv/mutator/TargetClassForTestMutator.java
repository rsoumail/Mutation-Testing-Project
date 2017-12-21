package fr.istic.m2il.vv.mutator;

public class TargetClassForTestMutator {


    private int valueOfVoidMethod;


	public int addInt(int a, int b) {
		return a + b;
	}

	public double addDouble(double a, double b) {
		return a + b;
	}

	public float addFloat(float a, float b) {
		return a + b;
	}

	public long addLong(long a, long b) {
		return a + b;
	}

	public int subInt(int a, int b) {
		return a - b;
	}

	public double subDouble(double a, double b) {
		return a - b;
	}

	public float subFloat(float a, float b) {
		return a - b;
	}

	public long subLong(long a, long b) {
		return a - b;
	}

	public int divInt(int a, int b) {
		return a / b;
	}

	public double divDouble(double a, double b) {
		return a / b;
	}

	public float divFloat(float a, float b) {
		return a / b;
	}

	public long divLong(long a, long b) {
		return a / b;
	}

	public int mulInt(int a, int b) {
		return a * b;
	}

	public double mulDouble(double a, double b) {
		return a * b;
	}

	public float mulFloat(float a, float b) {
		return a * b;
	}

	public long mulLong(long a, long b) {
		return a * b;
	}

	public void voidMethod(int valueOfVoidMethod) {
		this.valueOfVoidMethod = valueOfVoidMethod;
	}

	public int getValueOfVoidMethod() {
		return valueOfVoidMethod;
	}

	public boolean boolMethod() {

		return true;
	}

	public boolean firstGreatherThanSecond(int a, int b) {
		return a > b;
	}

	public boolean secondGreatherThanfirst(int a, int b) {
		return a < b;
	}

	public boolean firstLessThanSecond(int a, int b) {
		return a < b;
	}

	public boolean secondLessThanFirst(int a, int b) {
		return b < a;
	}

	public boolean firstGreatherOrEqualThanSecond(int a, int b) {
		return a >= b;
	}

	public boolean secondGreatherOrEqualThanFirst(int a, int b) {
		return a <= b;
	}

	public boolean firstLessOrEqualThanSecond(int a, int b) {
		return a <= b;
	}

	public boolean secondLessOrEqualThanFirst(int a, int b) {
		return a >= b;
	}

}
