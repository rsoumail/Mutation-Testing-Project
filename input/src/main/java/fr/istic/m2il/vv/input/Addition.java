package fr.istic.m2il.vv.input;

public class Addition implements Operation {
    private Double firstMember;
    private Double secondMember;
    @Override
    public Double getFisrtMember() {
        return null;
    }

    @Override
    public void setFirstMember(Double firstMember) {
        this.firstMember = firstMember;
    }

    @Override
    public Double getSecondMember() {
        return this.secondMember;
    }

    @Override
    public void setSecondMember(Double secondMember) {
        this.secondMember = secondMember;
    }

    public Double addition(){
        return (getFisrtMember() + getSecondMember()) ;
    }
}
