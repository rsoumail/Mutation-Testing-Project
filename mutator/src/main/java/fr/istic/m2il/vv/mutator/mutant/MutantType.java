package fr.istic.m2il.vv.mutator.mutant;

public enum MutantType {
    ARITHMETIC_MUTATOR("ArithmeticOperatorMutator"),
    VOID_MUTATOR("VoidMethodMutator"),
    BOOLEAN_MUTATOR("BooleanMethodMutator"),
    COMPARISON_MUTATOR("ComparisonOperatorMutator");

    private final String mutanType;

    MutantType(final String mutantType){
        this.mutanType = mutantType;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return mutanType;
    }
}
