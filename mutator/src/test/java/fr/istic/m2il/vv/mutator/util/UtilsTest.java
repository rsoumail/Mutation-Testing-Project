package fr.istic.m2il.vv.mutator.util;

import fr.istic.m2il.vv.mutator.TargetClassForTestMutator;
import fr.istic.m2il.vv.mutator.TargetClassForTestMutatorTest;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.mutant.ArithmeticOperatorMutator;
import fr.istic.m2il.vv.mutator.mutant.BooleanMethodMutator;
import fr.istic.m2il.vv.mutator.mutant.Mutator;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class UtilsTest {

    TargetProject targetProject;
    CtClass ctClassForTest;
    CtMethod ctMethod;
    JavaAssistHelper javaAssistHelper;
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

        ApplicationProperties.getInstance(new File("src/main/resources/application.properties"));
        javaAssistHelper = JavaAssistHelper.getInstance();




        ctClassForTest = javaAssistHelper.getPool().get("fr.istic.m2il.vv.mutator.TargetClassForTestMutator");

        methods = TargetClassForTestMutator.class.getDeclaredMethods();


    }

    @After
    public void tearDown() throws Exception {
        targetProject = null;
        ctClassForTest = null;
        ctMethod = null;
        modifiedCtMethod = null;
        original = null;
        methods = null;
        javaAssistHelper = null;
    }

    @Test
    public void write() throws Exception {
    }

    @Test
    public void loadPropertiesFile() throws Exception {
        Assert.assertNotNull(Utils.loadPropertiesFile(new File("src/main/resources/application.properties")));
    }

    @Test
    public void testsCasesInTestClass() throws Exception {
        AtomicInteger counter = new AtomicInteger(0);
        Arrays.stream(TargetClassForTestMutatorTest.class.getMethods()).forEach(m -> {
            Annotation[] annotations = m.getDeclaredAnnotations();
            Arrays.stream(annotations).forEach(a -> {
                if (a.annotationType().toString().equals("interface org.junit.Test")) {
                    counter.getAndIncrement();
                }
            });

        });

        Assert.assertEquals(counter.get(), Utils.testsCasesInTestClass(TargetClassForTestMutatorTest.class));
    }

    @Test
    public void revert() throws Exception {
        for(Method m: methods){
            if(m.getName().equals("voidMethod")){
                original = m;
            }
        }
        ctMethod = ctClassForTest.getDeclaredMethod(original.getName());
        modifiedCtMethod = CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null);
        modifiedCtMethod.setBody("{}");
        Utils.revert(modifiedCtMethod, ctMethod, new BooleanMethodMutator(targetProject), targetProject);
        if(ctMethod.getDeclaringClass().isFrozen())
            ctMethod.getDeclaringClass().defrost();
        if(modifiedCtMethod.getDeclaringClass().isFrozen())
            modifiedCtMethod.getDeclaringClass().defrost();
        Assert.assertEquals(modifiedCtMethod, CtNewMethod.copy(ctMethod, ctMethod.getDeclaringClass(), null));
    }

}