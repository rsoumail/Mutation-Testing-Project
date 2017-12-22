package fr.istic.m2il.vv.mutator.report;

import fr.istic.m2il.vv.mutator.mutant.MutantState;
import fr.istic.m2il.vv.mutator.mutant.MutantType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReportTest {

    Report report;
    @Before
    public void setUp() throws Exception {
        report = new Report(MutantState.STARTED,"");
        report.setTestsRan(new Integer(2));
        report.setMutatedMethodName("Method");
        report.setMutatedClassName("Class");
        report.setTestClassRun("ClassTest");
        report.setMutatedLine(new Integer(10));
        report.setMutationDescription("Description");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAndSetters(){
        Assert.assertEquals("ClassTest", report.getTestClassRun());
        Assert.assertEquals("Method", report.getMutatedMethodName());
        Assert.assertEquals("Class", report.getMutatedClassName());
        Assert.assertEquals(new Integer(10), report.getMutatedLine());
        Assert.assertEquals(new Integer(2), report.getTestsRan());
        Assert.assertEquals(MutantState.STARTED, report.getMutantState());
        Assert.assertEquals("Description", report.getMutationDescription());
    }

}