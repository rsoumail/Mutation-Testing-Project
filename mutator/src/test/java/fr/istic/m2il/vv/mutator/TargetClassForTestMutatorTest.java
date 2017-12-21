package fr.istic.m2il.vv.mutator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TargetClassForTestMutatorTest {

    TargetClassForTestMutator targetClassForTestMutator;

    @Before
    public void setUp() throws Exception {
        targetClassForTestMutator = new TargetClassForTestMutator();
    }

    @Test
    public void add() throws Exception {

        Assert.assertEquals(3, targetClassForTestMutator.addInt(1, 2));
    }

    @Test
    public void sub() throws Exception {
        Assert.assertEquals(1, targetClassForTestMutator.subInt(2, 1), 0);
    }

    @Test
    public void div() throws Exception {
        Assert.assertEquals(1, targetClassForTestMutator.divInt(2, 2), 0);
    }

    @Test
    public void mul() throws Exception {
        Assert.assertEquals(4, targetClassForTestMutator.mulInt(2, 2));
    }

    @Test
    public void voidMethod() throws Exception {
        targetClassForTestMutator.voidMethod(2);
        Assert.assertEquals(2, targetClassForTestMutator.getValueOfVoidMethod());
    }

    @Test
    public void boolMethod() throws Exception {
        Assert.assertTrue(targetClassForTestMutator.boolMethod());
    }

    @Test
    public void firstGreatherThanSecondCOmparisonMethod(){
        Assert.assertTrue(targetClassForTestMutator.firstGreatherThanSecond(2,1));
    }

}