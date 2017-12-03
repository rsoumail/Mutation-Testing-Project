package fr.istic.m2il.vv.mutator.report;

import fr.istic.m2il.vv.mutator.mutant.MutantState;

/**
 * Report describes information from execution of test classes with a mutated
 * class
 */
public class Report {

    private MutantState mutantState;

    private String mutationDescription;

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

}