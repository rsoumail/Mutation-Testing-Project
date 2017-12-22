package fr.istic.m2il.vv.mutator.report;

import fr.istic.m2il.vv.mutator.mutant.MutantState;

/**
 * Report describes information from execution of test classes with a mutated
 * class
 */
public class Report {

    private MutantState mutantState;

    private String mutationDescription;
    private String mutatedMethodName;
    private String mutatedClassName;
    private Integer mutatedLine;
    private Integer testsRan;
    private String testClassRun;

    public Report(MutantState mutantState, String mutationDescription) {
        super();
        this.mutantState = mutantState;
        this.mutationDescription = mutationDescription;
    }

    /**
     * @return the state of mutant
     */
    public MutantState getMutantState() {
        return mutantState;
    }

    /**
     * @param mutantState
     *            the state of mutant to set
     */
    public void setMutantState(MutantState mutantState) {
        this.mutantState = mutantState;
    }

    /**
     * @return the mutationDescription
     */
    public String getMutationDescription() {
        return mutationDescription;
    }

    /**
     * @param mutationDescription
     *            the mutationDescription to set
     */
    public void setMutationDescription(String mutationDescription) {
        this.mutationDescription = mutationDescription;
    }


    public String getMutatedMethodName() {
        return mutatedMethodName;
    }

    public void setMutatedMethodName(String mutatedMethodName) {
        this.mutatedMethodName = mutatedMethodName;
    }

    public String getMutatedClassName() {
        return mutatedClassName;
    }

    public void setMutatedClassName(String mutatedClassName) {
        this.mutatedClassName = mutatedClassName;
    }

    public Integer getMutatedLine() {
        return mutatedLine;
    }

    public void setMutatedLine(Integer mutatedLine) {
        this.mutatedLine = mutatedLine;
    }

    public Integer getTestsRan() {
        return testsRan;
    }

    public void setTestsRan(Integer testsRan) {
        this.testsRan = testsRan;
    }

    public String getTestClassRun() {
        return testClassRun;
    }

    public void setTestClassRun(String testClassRun) {
        this.testClassRun = testClassRun;
    }
}