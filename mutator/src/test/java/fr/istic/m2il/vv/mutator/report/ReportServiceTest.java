package fr.istic.m2il.vv.mutator.report;

import fr.istic.m2il.vv.mutator.TargetClassForTestMutator;
import fr.istic.m2il.vv.mutator.TargetClassForTestMutatorTest;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.mutant.BooleanMethodMutator;
import fr.istic.m2il.vv.mutator.mutant.Mutator;
import fr.istic.m2il.vv.mutator.mutant.VoidMethodMutator;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import jdk.nashorn.internal.runtime.ECMAException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ReportServiceTest {

    TargetProject targetProject;
    CtMethod ctMethod;
    JavaAssistHelper javaAssistHelper;
    Mutator mutator;
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

        methods = TargetClassForTestMutator.class.getDeclaredMethods();
        for(Method m: methods){
            if(m.getName().equals("voidMethod")){
                original = m;
            }
        }

        ctMethod = javaAssistHelper.getPool().get("fr.istic.m2il.vv.mutator.TargetClassForTestMutator").getDeclaredMethod(original.getName());
        mutator = new VoidMethodMutator(targetProject);
        mutator.mutate(ctMethod);
        ReportService.getInstance().doReport();
    }

    @After
    public void tearDown() throws Exception {
        targetProject = null;
        mutator = null;
        ctMethod = null;
        javaAssistHelper = null;
        original = null;
        methods = null;
    }

    @Test
    public void validMutantNumber() throws Exception{
        Assert.assertEquals(new Integer(1), ReportService.getInstance().getTotalMutationsNumber());
    }

    @Test
    public void validTotalTestsCasesRan() throws Exception{
        Assert.assertEquals(new Integer(57), ReportService.getInstance().getTotalTestsCasesRan());
    }

    @Test
    public void validTotalKilledMutantsNumber(){
        Assert.assertEquals(new Integer(2), ReportService.getInstance().getTotalKilledMutantsNumber());
    }

    @Test
    public void validRate(){
        Assert.assertEquals(new Float(50),ReportService.getInstance().getRate(new Integer(2), new Integer(4)));
    }

}