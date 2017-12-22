package fr.istic.m2il.vv.mutator.targetproject;

import fr.istic.m2il.vv.mutator.TargetClassForTestMutator;
import fr.istic.m2il.vv.mutator.TargetClassForTestMutatorTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TargetProjectTest {
    TargetProject targetProject;
    @Before
    public void setUp() throws Exception {

        targetProject = TargetProject.getInstance();
        targetProject.setLocation(new File(""));
        targetProject.setClassesLocation(new File("target/classes"));
        targetProject.setTestsLocation((new File("target/test-classes")));
        targetProject.setPom(new File("pom.xml"));
        List<Class<?>> listclass = new ArrayList<>();
        listclass.add(TargetClassForTestMutator.class);
        List<Class<?>> listtest = new ArrayList<>();
        listtest.add(TargetClassForTestMutatorTest.class);
        targetProject.setClasses(listclass);
        targetProject.setTests(listtest);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTestClassNameOfClassValid() throws Exception {
        Assert.assertEquals(TargetClassForTestMutatorTest.class.getName(), targetProject.getTestClassNameOfClass(TargetClassForTestMutator.class.getName()));
    }

    @Test
    public void testClassNotNullValid(){
        Assert.assertEquals(TargetClassForTestMutatorTest.class, targetProject.getTestClassOfClass(TargetClassForTestMutator.class.getName()));
    }

    @Test
    public void getTestClassNameOfClassBad() throws Exception {
        Assert.assertEquals(TargetClassForTestMutatorTest.class.getName(), targetProject.getTestClassNameOfClass(""));
    }

    @Test
    public void testClassNotNullBad(){
        Assert.assertEquals(TargetClassForTestMutatorTest.class, targetProject.getTestClassOfClass(""));
    }

}