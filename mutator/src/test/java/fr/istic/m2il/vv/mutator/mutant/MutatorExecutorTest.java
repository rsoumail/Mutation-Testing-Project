package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.TargetClassForTestMutator;
import fr.istic.m2il.vv.mutator.TargetClassForTestMutatorTest;
import fr.istic.m2il.vv.mutator.config.ApplicationProperties;
import fr.istic.m2il.vv.mutator.loader.JavaAssistHelper;
import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MutatorExecutorTest {

    JavaAssistHelper javaAssistHelper;
    TargetProject targetProject;
    Mutator mutator;
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
        targetProject.setReportDir(new File("."));
        ApplicationProperties.getInstance(new File("src/main/resources/application.properties"));

        javaAssistHelper = JavaAssistHelper.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        targetProject = null;
        mutator = null;
    }

    @Test
    public void execute() throws Exception {
        mutator = new VoidMethodMutator(targetProject);
        MutatorExecutor executor = new MutatorExecutor(javaAssistHelper);
        executor.execute(mutator, targetProject);
        Assert.assertNotEquals(0, ((VoidMethodMutator)mutator).getTestResult().getExitCode());
    }

}