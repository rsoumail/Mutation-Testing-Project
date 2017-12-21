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
    public void addInt() throws Exception {

        Assert.assertEquals(3, targetClassForTestMutator.addInt(1, 2));

    }
    @Test
    public void addFloat() throws Exception {

        Assert.assertEquals(3, targetClassForTestMutator.addFloat(1.0f, 2.0f), 0);
    }

    @Test
    public void addLong() throws Exception {

        Assert.assertEquals(3, targetClassForTestMutator.addLong((long) 1L, 2L));
    }

    @Test
    public void addDouble() throws Exception {

        Assert.assertEquals(3, targetClassForTestMutator.addDouble(1.0, 2.0), 0);
    }

    @Test
    public void subInt() throws Exception {
        Assert.assertEquals(1, targetClassForTestMutator.subInt(2, 1));
    }

    @Test
    public void subFloat() throws Exception {
        Assert.assertEquals(1, targetClassForTestMutator.subFloat(2, 1),0);
    }

    @Test
    public void subLong() throws Exception {
        Assert.assertEquals(1, targetClassForTestMutator.subLong(2L, 1L));
    }

    @Test
    public void subDouble() throws Exception {
        Assert.assertEquals(1, targetClassForTestMutator.subDouble(2.0, 1.0), 0);
    }

    @Test
    public void divInt() throws Exception {
        Assert.assertEquals(1, targetClassForTestMutator.divInt(2, 2));
    }
    @Test
    public void divFloat() throws Exception {
        Assert.assertEquals(1, targetClassForTestMutator.divFloat(2, 2), 0);
    }

    @Test
    public void divLong() throws Exception {
        Assert.assertEquals(1, targetClassForTestMutator.divLong(2L, 2L));
    }
    @Test
    public void divDouble() throws Exception {
        Assert.assertEquals(1, targetClassForTestMutator.divDouble(2.0, 2.0), 0);
    }

    @Test
    public void mulInt() throws Exception {
        Assert.assertEquals(4, targetClassForTestMutator.mulInt(2, 2));
    }

    @Test
    public void mulFloat() throws Exception {
        Assert.assertEquals(4, targetClassForTestMutator.mulFloat(2.0f, 2.0f), 0);
    }

    @Test
    public void mulLong() throws Exception {
        Assert.assertEquals(4, targetClassForTestMutator.mulLong(2L, 2L));
    }

    @Test
    public void mulDouble() throws Exception {
        Assert.assertEquals(4, targetClassForTestMutator.mulDouble(2.0, 2.0), 0);
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