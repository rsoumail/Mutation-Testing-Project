package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.TargetClassForTestMutator;
import fr.istic.m2il.vv.mutator.TargetClassForTestMutatorTest;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ComparisonOperatorMutatorTest {


    TargetProject targetProject;
    CtClass ctClassForTest;
    CtMethod ctMethod;
    JavaAssistHelper javaAssistHelper;
    Mutator mutator;
    CtMethod modifiedCtMethod;
    Method original;
    Method[] methods;

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
        javaAssistHelper = JavaAssistHelper.getInstance();
       

        ApplicationProperties.getInstance(new File("src/main/resources/application.properties"));


        ctClassForTest = javaAssistHelper.getPool().get("fr.istic.m2il.vv.mutator.TargetClassForTestMutator");

        methods = TargetClassForTestMutator.class.getDeclaredMethods();
        mutator = new ComparisonOperatorMutator(targetProject);
    }
    
    

    
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void mutatefirstGreatherThanSecond() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("firstGreatherThanSecond")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertEquals(0, ((ComparisonOperatorMutator)mutator).getTestResult().getExitCode());
    }
  
    
    @Test
    public void mutatesecondGreatherThanfirst() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("secondGreatherThanfirst")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertEquals(0, ((ComparisonOperatorMutator)mutator).getTestResult().getExitCode());
    }
    
    @Test
    public void mutatefirstLessThanSecond() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("firstLessThanSecond")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertEquals(0, ((ComparisonOperatorMutator)mutator).getTestResult().getExitCode());
    }
    
    @Test
    public void mutatesecondLessThanFirst() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("secondLessThanFirst")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertEquals(0, ((ComparisonOperatorMutator)mutator).getTestResult().getExitCode());
    }
    
    @Test
    public void mutatefirstGreatherOrEqualThanSecond() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("firstGreatherOrEqualThanSecond")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertEquals(0, ((ComparisonOperatorMutator)mutator).getTestResult().getExitCode());
    }

    @Test
    public void mutatesecondGreatherOrEqualThanFirst() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("secondGreatherOrEqualThanFirst")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertEquals(0, ((ComparisonOperatorMutator)mutator).getTestResult().getExitCode());
    }
    
    @Test
    public void mutatefirstLessOrEqualThanSecond() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("firstLessOrEqualThanSecond")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertEquals(0, ((ComparisonOperatorMutator)mutator).getTestResult().getExitCode());
    }
    
    @Test
    public void mutatesecondLessOrEqualThanFirst() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("secondLessOrEqualThanFirst")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertEquals(0, ((ComparisonOperatorMutator)mutator).getTestResult().getExitCode());
    }
}