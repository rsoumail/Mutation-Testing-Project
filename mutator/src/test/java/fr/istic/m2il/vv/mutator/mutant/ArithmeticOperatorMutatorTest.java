package fr.istic.m2il.vv.mutator.mutant;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import fr.istic.m2il.vv.mutator.TargetClassForTestMutator;
import fr.istic.m2il.vv.mutator.TargetClassForTestMutatorTest;
import fr.istic.m2il.vv.mutator.mutant.ArithmeticOperatorMutator;
import fr.istic.m2il.vv.mutator.mutant.Mutator;
import fr.istic.m2il.vv.mutator.testrunner.runner.MVNRunner;
import javassist.*;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.istic.m2il.vv.mutator.common.ClassLoaderParser;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.loader.CustomTranslator;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.util.Utils;

import static org.mockito.Mockito.*;

public class ArithmeticOperatorMutatorTest {

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

        mutator = new ArithmeticOperatorMutator(targetProject);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void mutateaddMethod() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("add")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertNotEquals(0, ((ArithmeticOperatorMutator)mutator).getTestResult().getExitCode());
    }
    @Test
    public void mutatesubMethod() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("sub")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertNotEquals(0, ((ArithmeticOperatorMutator)mutator).getTestResult().getExitCode());
    }
    @Test
    public void mutatemulMethod() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("mul")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertNotEquals(0, ((ArithmeticOperatorMutator)mutator).getTestResult().getExitCode());
    }

    @Test
    public void mutatedivMethod() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("div")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator.mutate(ctMethod);
        Assert.assertNotEquals(0, ((ArithmeticOperatorMutator)mutator).getTestResult().getExitCode());
    }
}