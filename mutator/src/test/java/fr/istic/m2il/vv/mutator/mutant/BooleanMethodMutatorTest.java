package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.TargetClassForTestMutator;
import fr.istic.m2il.vv.mutator.TargetClassForTestMutatorTest;
import fr.istic.m2il.vv.mutator.common.ClassLoaderParser;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.loader.CustomTranslator;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import fr.istic.m2il.vv.mutator.util.Utils;
import javassist.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BooleanMethodMutatorTest {

    TargetProject targetProject;
    CtClass ctClassForTest;
    CtMethod ctMethod;
    JavaAssistHelper javaAssistHelper;
    Mutator mutator;
    CtMethod modifiedCtMethod;
    Method original;

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

        Method[] methods = TargetClassForTestMutator.class.getDeclaredMethods();
        for(Method m: methods){
            if(m.getName().equals("boolMethod")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        mutator = new BooleanMethodMutator(targetProject);

    }

    @After
    public void tearDown() throws Exception {

        ctClassForTest = null;
        ctMethod = null;
        javaAssistHelper = null;
        mutator = null;
        modifiedCtMethod = null;
        original = null;
        targetProject = null;
    }

    @Test
    public void mutateRealMethod() throws Exception {
        mutator.mutate(ctMethod);
        Assert.assertNotEquals(0, ((BooleanMethodMutator)mutator).getTestResult().getExitCode());
    }


}